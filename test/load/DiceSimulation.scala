package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DiceSimulation extends Simulation {
  
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

  setUp(
    defaultScenario.inject(
      rampUsers(50).during(30.seconds),
      constantUsersPerSec(50).during(1.minute)
    ),
    complexScenario.inject(
      nothingFor(30.seconds), // Start after default scenario ramps up
      rampUsers(30).during(30.seconds)
    ),
    simpleScenario.inject(
      nothingFor(1.minute), // Start after complex scenario
      rampUsers(20).during(30.seconds)
    )
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.max.lt(1000),    // All responses should be under 1 second
     global.responseTime.mean.lt(500),    // Mean response time under 500ms
     global.successfulRequests.percent.gt(95), // 95% of requests should be successful
     details("complex_roll_request").responseTime.percentile3.lt(750) // 95th percentile for complex requests
   )
}