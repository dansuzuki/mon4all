package mon4all

/** relative imports */
import api._


import com.twitter.finagle.http.{Method, HttpMuxer, Request, Response}
import com.twitter.finagle.{Http, Service, ListeningServer}
import com.twitter.finagle.http
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http.path._
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}

import java.sql.{Connection, DriverManager, ResultSet, PreparedStatement}
import java.util.concurrent.CountDownLatch

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import scala.collection.JavaConversions._
import scala.sys.process._
import scala.util.Try


object Application extends TwitterServer {

  override def failfastOnFlagsNotParsed: Boolean = true

  /** application options */
  val port = flag("port", ":8080", "The port where the server will listen to")


  /** URI prefix */
  val prefix = Root / "mon4all" / "api"
  val ticks = prefix / "ticks"
  val services =
    HttpRouter.byRequest { request =>
      (request.method, Path(request.path)) match {
        case Method.Post -> ticks / jobName => TickServices.newOrResetJob(jobName)
        case Method.Get -> ticks / jobName => TickServices.getJob(jobName)
        case Method.Delete -> ticks / jobName => TickServices.deleteJob(jobName)
        case Method.Post -> ticks / jobName / itemName => TickServices.updateJobItem(jobName, itemName)
        case Method.Get -> ticks / jobName / itemName => TickServices.getJobItem(jobName, itemName)
        case Method.Delete -> ticks / jobName / itemName => TickServices.deleteJobItem(jobName, itemName)
      }
    }


  /** cors filter */
  def corsFilter = new Cors.HttpFilter(Cors.UnsafePermissivePolicy)

  def developmentInit {
    println("==== dropping db objects ====")
    db.TickData.dropObjects
    println("==== complete ===============")
    println()
    println("==== creating db objects ====")
    db.TickData.createObjects
    println("==== complete ===============")
    println()
  }

  /**
   * Main Entry point
   */
  def main() {
    // for development stage only
    developmentInit

    HttpMuxer.addRichHandler("/", corsFilter.andThen(services))
    val server = Http
      .server
      .withAdmissionControl
      .concurrencyLimit(32,8)
      .serve(port.getWithDefault.get, HttpMuxer)

    onExit {
      server.close()
    }
    Await.ready(server)

  }


}


/**
 * Router Object Helper
 */
object HttpRouter {
  def byRequest[REQUEST](routes: PartialFunction[Request, Service[REQUEST, Response]]) =
    new RoutingService(
      new PartialFunction[Request, Service[REQUEST, Response]] {
        def apply(request: Request)       = routes(request)
        def isDefinedAt(request: Request) = routes.isDefinedAt(request)
      })
}
