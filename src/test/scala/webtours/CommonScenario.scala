package webtours
import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


object CommonScenario{
  def apply(): ScenarioBuilder = new CommonScenario().scn
}
class CommonScenario {
  val scn: ScenarioBuilder = scenario("WebTours OTUS")
    .exec(Actions.mainPage)
    .exec(Actions.login)
    .exec(Actions.flights)
    .exec(Actions.getCities)
    .exec(Actions.findFlight)
    .exec(Actions.getOutboundFlight)
    .exec(Actions.pickAir)
    .exec(Actions.pickFlight)
    .exec(Actions.homePage)
}
