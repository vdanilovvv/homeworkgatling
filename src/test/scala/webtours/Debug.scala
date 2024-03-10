package webtours

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.core.scenario.Simulation
import webtours.WebTours.httpProtocol

class Debug extends Simulation {
  setUp(CommonScenario()

    .inject(
      constantUsersPerSec(0.065).during(2.minute),
      constantUsersPerSec(0.13).during(2.minute),
      constantUsersPerSec(0.195).during(2.minute),
      constantUsersPerSec(0.26).during(2.minute),
      constantUsersPerSec(0.325).during(2.minute),
      constantUsersPerSec(0.39).during(2.minute),
      constantUsersPerSec(0.455).during(2.minute),
      constantUsersPerSec(0.52).during(2.minute),
      constantUsersPerSec(0.585).during(2.minute),
      constantUsersPerSec(0.65).during(2.minute))
    .protocols(httpProtocol)).assertions(global.responseTime.max.lt(3000))
}
