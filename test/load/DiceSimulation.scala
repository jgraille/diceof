package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DiceSimulation extends Simulation {

  // `Open system`, no control over the number of concurrent users
  // users can keep on arriving, some of them will be answered with 429 errors.
  // (too many requests)
  
  // For local testing we use HTTP since the dev server typically doesn't have HTTPS configured
  // In a production environment, you would use:
  // .baseUrl("https://your-production-domain.com")
  val httpProtocol = http
    .baseUrl("http://localhost:9000")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  // Define different payload scenarios
  val defaultPayload = StringBody("""{"nbDicesA":5,"expectedValueA":"5+","occurrencesA":2,"nbDicesB":2,"expectedValueB":"4","occurrencesB":1}""")
  val complexPayload = StringBody("""{"nbDicesA":20,"expectedValueA":"6","occurrencesA":10,"nbDicesB":18,"expectedValueB":"5","occurrencesB":8}""")
  val simplePayload = StringBody("""{"nbDicesA":1,"expectedValueA":"1","occurrencesA":1,"nbDicesB":1,"expectedValueB":"1","occurrencesB":1}""")

  val defaultScenario = scenario("Default Dice Roll")
    .exec(
      http("default_roll_request")
        .post("/roll")
        .body(defaultPayload)
        .asJson
        .check(status.is(200))
        .check(jsonPath("$.winner").exists)
    )

  val complexScenario = scenario("Complex Dice Roll")
    .exec(
      http("complex_roll_request")
        .post("/roll")
        .body(complexPayload)
        .asJson
        .check(status.is(200))
        .check(jsonPath("$.winner").exists)
    )

  val simpleScenario = scenario("Simple Dice Roll")
    .exec(
      http("simple_roll_request")
        .post("/roll")
        .body(simplePayload)
        .asJson
        .check(status.is(200))
        .check(jsonPath("$.winner").exists)
    )

  // Stay within rate limit of 60 requests per minute
  setUp(
    defaultScenario.inject(
      // Montée en charge progressive (linéaire) jusqu'à 20 utilisateurs au bout de 30 secondes
      rampUsers(20).during(30.seconds),
      // Injection de 0.67 utilisateur par seconde (0.67*3≈2), soit 2 utilisateurs toutes les 3 secondes
      // Charge stable qui respecte un débit
      constantUsersPerSec(0.67).during(30.seconds)
    ),
    complexScenario.inject(
      nothingFor(30.seconds),
      rampUsers(30).during(60.seconds)
    ),
    simpleScenario.inject(
      nothingFor(1.minute),
      rampUsers(5).during(30.seconds)
    )
  ).protocols(httpProtocol)
   .assertions(
    // Max response time observed in ms, lt(1000) -> assertion should be under 1 second
    global.responseTime.max.lt(1000),
    // Mean response, lt(500) -> this time should be under 500ms
    global.responseTime.mean.lt(500),
    // gt(95) -> the success rate should be greater than 95%
    global.successfulRequests.percent.gt(95),
    // percentile3: perform the assertion on the 3rd percentile of the metric, 
    //as configured in gatling.conf (default is 95th).
    // lt(750) -> the 3rd percentile of the response time should be under 750ms
    details("complex_roll_request").responseTime.percentile3.lt(750)
   )
}