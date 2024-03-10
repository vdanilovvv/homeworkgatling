package webtours

import scala.concurrent.duration._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import scala.util.Random

object Actions {

  private val outboundFlights = List(1)


  private val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

  private val headers_2 = Map(
    "Origin" -> "http://webtours.load-test.ru:1080",
    "Upgrade-Insecure-Requests" -> "1"
  )

  private val headers_5 = Map("Accept" -> "image/avif,image/webp,*/*")


  val mainPage = http("request_0")
    .get("/cgi-bin/welcome.pl?signOff=true")
    .headers(headers_0)
    .resources(
      http("request_1")
        .get("/cgi-bin/nav.pl?in=home")
        .headers(headers_0)
        .check(regex("""<input type="hidden" name="userSession" value="([^"]+)"""").saveAs("userSession"))
    )

  val login = http("request_2")
    .post("/cgi-bin/login.pl")
    .headers(headers_2)
    .formParam("userSession", "#{userSession}")
    .formParam("username", "danilov")
    .formParam("password", "danilov")
    .formParam("login.x", "59")
    .formParam("login.y", "7")
    .formParam("JSFormSubmit", "off")
    .resources(
      http("request_3")
        .get("/cgi-bin/login.pl?intro=true")
        .headers(headers_0),
      http("request_4")
        .get("/cgi-bin/nav.pl?page=menu&in=home")
        .headers(headers_0),
      http("request_5")
        .get("/WebTours/images/signoff.gif")
        .headers(headers_5),
      http("request_6")
        .get("/WebTours/images/in_home.gif")
        .headers(headers_5),
      http("request_7")
        .get("/WebTours/images/flights.gif")
        .headers(headers_5),
      http("request_8")
        .get("/WebTours/images/itinerary.gif")
        .headers(headers_5)
    )


  val flights = http("request_9")
    .get("/cgi-bin/welcome.pl?page=search")
    .headers(headers_0)
    .resources(
      http("request_10")
        .get("/cgi-bin/nav.pl?page=menu&in=flights")
        .headers(headers_0),
      http("request_11")
        .get("/cgi-bin/reservations.pl?page=welcome")
        .headers(headers_0)
        .check(regex("""<option(?:\sselected="selected")?\svalue="([^"]+)">""").findAll.saveAs("cities")),
      http("request_12")
        .get("/WebTours/images/home.gif")
        .headers(headers_5),
      http("request_13")
        .get("/WebTours/images/in_flights.gif")
        .headers(headers_5),
      http("request_14")
        .get("/WebTours/images/button_next.gif")
        .headers(headers_5))


  val getCities = exec(session => {
    val cities = session("cities").as[Seq[String]]
    val depart = cities(Random.nextInt(cities.size))
    val arrive = cities.filterNot(_ == depart)(Random.nextInt(cities.filterNot(_ == depart).size))
    session.set("depart", depart)
      .set("arrive", arrive)
  })

  val findFlight = http("request_15")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_2)
    .formParam("advanceDiscount", "0")
    .formParam("depart", "#{depart}")
    .formParam("departDate", "03/06/2024")
    .formParam("arrive", "#{arrive}")
    .formParam("returnDate", "03/07/2024")
    .formParam("numPassengers", "1")
    .formParam("seatPref", "None")
    .formParam("seatType", "Coach")
    .formParam("findFlights.x", "49")
    .formParam("findFlights.y", "10")
    .formParam(".cgifields", "roundtrip")
    .formParam(".cgifields", "seatType")
    .formParam(".cgifields", "seatPref")
    .check(regex("""name="outboundFlight" value="(.*?;.*?;.*?)"""").findAll.saveAs("outboundFlights")
 )

  var getOutboundFlight = exec(session => {
    val outboundFlights = session("outboundFlights").as[Seq[String]]
    val outboundFlight = outboundFlights(Random.nextInt(outboundFlights.size))
    session.set("outboundFlight", outboundFlight)
  })

  val pickAir = http("request_16")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_2)
    .formParam("outboundFlight", "#{outboundFlight}")
    .formParam("numPassengers", "1")
    .formParam("advanceDiscount", "0")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("reserveFlights.x", "57")
    .formParam("reserveFlights.y", "8")

  val pickFlight = http("request_17")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_2)
    .formParam("firstName", "Vsevolod")
    .formParam("lastName", "Danilov")
    .formParam("address1", "")
    .formParam("address2", "")
    .formParam("pass1", "Vsevolod Danilov")
    .formParam("creditCard", "")
    .formParam("expDate", "")
    .formParam("oldCCOption", "")
    .formParam("numPassengers", "1")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("outboundFlight", "#{outboundFlight}")
    .formParam("advanceDiscount", "0")
    .formParam("returnFlight", "")
    .formParam("JSFormSubmit", "off")
    .formParam("buyFlights.x", "45")
    .formParam("buyFlights.y", "5")
    .formParam(".cgifields", "saveCC")


  val homePage = http("request_18")
    .get("/cgi-bin/welcome.pl?page=menus")
    .headers(headers_0)
    .resources(
      http("request_19")
        .get("/cgi-bin/login.pl?intro=true")
        .headers(headers_0),
      http("request_20")
        .get("/cgi-bin/nav.pl?page=menu&in=home")
        .headers(headers_0)
    )
}
