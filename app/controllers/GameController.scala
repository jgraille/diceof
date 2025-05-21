package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.GameService
import models.{DiceMode, Exact, AtLeast}

@Singleton
class GameController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:

  def roll: Action[JsValue] = Action(parse.json) { request =>
    val json = request.body

    def parseExpectedValue(value: String): (Int, DiceMode) =
      if value.endsWith("+") then
        val numValue = value.dropRight(1).toInt
        (numValue, AtLeast)
      else
        (value.toInt, Exact)

    try
      val nbDicesA = (json \ "nbDicesA").as[Int]
      val nbDicesB = (json \ "nbDicesB").as[Int]
      val expValueA = (json \ "expectedValueA").as[String]
      val expValueB = (json \ "expectedValueB").as[String]
      val occurrencesA = (json \ "occurrencesA").as[Int]
      val occurrencesB = (json \ "occurrencesB").as[Int]

      // Validate input parameters
      if nbDicesA < 0 || nbDicesB < 0 then
        BadRequest("Number of dice must be non-negative")
      else if occurrencesA < 0 || occurrencesB < 0 then
        BadRequest("Number of occurrences must be non-negative")
      else if occurrencesA > nbDicesA || occurrencesB > nbDicesB then
        BadRequest("Number of occurrences cannot exceed number of dice")
      else
        val (expValA, modeA) = parseExpectedValue(expValueA)
        val (expValB, modeB) = parseExpectedValue(expValueB)

        // Validate expected values
        if expValA < 1 || expValA > 6 || expValB < 1 || expValB > 6 then
          BadRequest("Expected values must be between 1 and 6")
        else
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
    catch
      case e: NumberFormatException =>
        BadRequest(s"Invalid number format: ${e.getMessage}")
      case e: IllegalArgumentException =>
        BadRequest(e.getMessage)
      case e: Exception =>
        InternalServerError(s"Unexpected error: ${e.getMessage}")
}
