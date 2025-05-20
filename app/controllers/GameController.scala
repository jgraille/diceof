package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.GameService

@Singleton
class GameController @Inject()(val controllerComponents: ControllerComponents) extends BaseController:

  def roll: Action[JsValue] = Action(parse.json) { request =>
    val json = request.body

    def parseExpectedValue(value: String): Map[String, Any] =
      if value.endsWith("+") then
        Map("expectedValue" -> value.dropRight(1).toInt, "mode" -> "atLeast")
      else
        Map("expectedValue" -> value.toInt, "mode" -> "exact")

    try {
      val nbDicesA = (json \ "nbDicesA").as[Int]
      val nbDicesB = (json \ "nbDicesB").as[Int]
      val expValueA = (json \ "expectedValueA").as[String]
      val expValueB = (json \ "expectedValueB").as[String]
      val occurrencesA = (json \ "occurrencesA").as[Int]
      val occurrencesB = (json \ "occurrencesB").as[Int]

      val parseExpValueA = parseExpectedValue(expValueA)
      val parseExpValueB = parseExpectedValue(expValueB)

      val expValA = parseExpValueA("expectedValue").asInstanceOf[Int]
      val expValB = parseExpValueB("expectedValue").asInstanceOf[Int]
      val modeA = parseExpValueA("mode").asInstanceOf[String]
      val modeB = parseExpValueB("mode").asInstanceOf[String]

      // Validate expected values
      if expValA < 1 || expValA > 6 || expValB < 1 || expValB > 6 then
        BadRequest("Expected values must be between 1 and 6.")

      // Validate mode values
      val validModes = Set("exact", "atLeast")
      if !validModes.contains(modeA) || !validModes.contains(modeB) then 
        BadRequest("Mode must be either 'exact' or 'atLeast'.")

      val (probA, probB, winner) = GameService.compareProbabilities(
        nbDicesA, expValA, occurrencesA,
        nbDicesB, expValB, occurrencesB,
        modeA, modeB
      )

      val result = Json.obj(
        "probA" -> probA,
        "probB" -> probB,
        "winner" -> winner
      )

      Ok(result)

    } catch {
      case e: IllegalArgumentException =>
          BadRequest(s"An error occurred: ${e.getMessage}")
      case e: Exception =>
        InternalServerError(s"An error occurred: ${e.getMessage}")
    }  
}
