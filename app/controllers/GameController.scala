package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.GameService
import models.{DiceMode, Exact, AtLeast}
import scala.concurrent.duration._
import scala.concurrent.{Future, ExecutionContext}
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.pattern.CircuitBreaker

@Singleton
class GameController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext, system: ActorSystem) extends BaseController {

  // Future -> Do this in the background and get back to me
  // CircuitBreaker -> If things keep failing, stop trying for a while.

  // Rate limiting: Maximum requests per IP per minute
  private val requestsPerMinute = 60
  private val requestsByIp = scala.collection.concurrent.TrieMap[String, (Int, Long)]()

  // Circuit breaker to prevent system overload
  // If 5 futures fail or take too long, it opens the breaker.
  // Open state: the breaker is open, no requests are allowed
  // callTimeout = 2.seconds as we expect less than 10 ms for the roll request
  private val breaker = CircuitBreaker(
    system.scheduler, // The clock that tracks time
    maxFailures = 5,  // Trip (trébuche) after 5 failures
    callTimeout = 2.seconds, // Max time the circuit breaker will wait for the code to run and finish 
    resetTimeout = 1.minute // Open state, the breaker automatically tries again - it goes in the half-open state
  )

  private def isRateLimited(ip: String): Boolean = {
    val now = System.currentTimeMillis()
    val (count, timestamp) = requestsByIp.getOrElse(ip, (0, now))
    
    if (now - timestamp > 60000) { // More than a minute has passed
      requestsByIp.put(ip, (1, now))
      false
    } else if (count >= requestsPerMinute) {
      true
    } else {
      requestsByIp.put(ip, (count + 1, timestamp))
      false
    }
  }

  def roll: Action[JsValue] = Action(parse.json).async { request =>
    val ip = request.remoteAddress
    
    if (isRateLimited(ip)) {
      Future.successful(TooManyRequests("Rate limit exceeded. Please try again later."))
    } else {
      breaker.withCircuitBreaker(Future {
        // This block of code runs in the background
        // so that the dice game logic doesn’t freeze the whole server
        // It runs asynchronously and gives the result when it’s done

        // Note: this code doesn't run if 5 failures happen.
        val json = request.body

        def parseExpectedValue(value: String): (Int, DiceMode) = {
          if (value.endsWith("+")) {
            val numValue = value.dropRight(1).toInt
            (numValue, AtLeast)
          } else {
            (value.toInt, Exact)
          }
        }

        try {
          val nbDicesA = (json \ "nbDicesA").as[Int]
          val nbDicesB = (json \ "nbDicesB").as[Int]
          val expValueA = (json \ "expectedValueA").as[String]
          val expValueB = (json \ "expectedValueB").as[String]
          val occurrencesA = (json \ "occurrencesA").as[Int]
          val occurrencesB = (json \ "occurrencesB").as[Int]

          // Validate input parameters
          if (nbDicesA < 0 || nbDicesB < 0) {
            BadRequest("Number of dice must be non-negative")
          } else if (occurrencesA < 0 || occurrencesB < 0) {
            BadRequest("Number of occurrences must be non-negative")
          } else if (occurrencesA > nbDicesA || occurrencesB > nbDicesB) {
            BadRequest("Number of occurrences cannot exceed number of dice")
          } else {
            val (expValA, modeA) = parseExpectedValue(expValueA)
            val (expValB, modeB) = parseExpectedValue(expValueB)

            // Validate expected values
            if (expValA < 1 || expValA > 6 || expValB < 1 || expValB > 6) {
              BadRequest("Expected values must be between 1 and 6")
            } else {
              val (probA, probB, winner) = GameService.compareProbabilities(
                nbDicesA, expValA, occurrencesA,
                nbDicesB, expValB, occurrencesB,
                modeA, modeB
              )

              Ok(Json.obj(
                "probA" -> probA,
                "probB" -> probB,
                "winner" -> winner
              ))
            }
          }
        } catch {
          case e: NumberFormatException =>
            BadRequest(s"Invalid number format: ${e.getMessage}")
          case e: IllegalArgumentException =>
            BadRequest(e.getMessage)
          case e: Exception =>
            InternalServerError(s"Unexpected error: ${e.getMessage}")
        }
      }).recover {
        case _: java.util.concurrent.TimeoutException =>
          RequestTimeout("Request timed out. Please try again.")
        case _ =>
          ServiceUnavailable("Service temporarily unavailable. Please try again later.")
      }
    }
  }
}