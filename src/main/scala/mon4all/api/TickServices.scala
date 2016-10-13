package mon4all.api

import mon4all.db._

import com.twitter.finagle.http.{Method, HttpMuxer, Request, Response}
import com.twitter.finagle.{Http, Service, ListeningServer}
import com.twitter.finagle.http
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.service.RoutingService
import com.twitter.finagle.http.path._
import com.twitter.server.TwitterServer
import com.twitter.util.{Await, Future}

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

object TickServices {
  import Helpers._
  /**
   * POST /mon4all/api/ticks/<job name>
   * Content: the list of items to monitor with status as the initial status
   *
   * If job doesn't exist yet, create it as a new, else reset its initial status.
   */
  def newOrResetJob(jobName: String) = service {
    req => {
      val resp = http.Response(req.version, http.Status.Created)
      resp.setContentString(req.contentString)
      import TickData._
      val job = Job(req.contentString)
      println(job)
      println("newJob(job) returns " + newJob(job))
      resp
    }
  }

  /**
   * POST /mon4all/api/ticks/<job name>/<item>
   * Content: status
   */
  def updateJobItem(jobName: String, itemName: String) = service {
    req => {
      val resp = http.Response(req.version, http.Status.Created)
      resp.setContentString(req.contentString)
      resp
    }
  }

  /**
   * DELETE /mon4all/api/ticks/<job name>/
   */
  def deleteJob(jobName: String) = service {
    req => http.Response(req.version, http.Status.Gone)
  }

  /**
   * DELETE /mon4all/api/ticks/<job name>/<item name>
   */
  def deleteJobItem(jobName: String, itemName: String) = service {
    req => http.Response(req.version, http.Status.Gone)
  }

  /**
   * GET /mon4all/api/ticks/<job name>
   * RETURN
   *   List all with current status
   */
  def getJob(jobName: String) = service {
    req => http.Response(req.version, http.Status.Ok)
  }

  /**
   * GET /mon4all/api/ticks/<job name>/<item name>
   * RETURN
   *   current status
   */
  def getJobItem(jobName: String, itemName: String) = service {
    req => http.Response(req.version, http.Status.Ok)
  }



  case class TickJob(id: Long, name: String) {
    def toJValue = (("id" -> id) ~ ("name" -> name))
  }

  val tickJobs = compact(
    List(TickJob(1, "FA Purchase Score BT"),
      TickJob(2, "Sim Birth BT"),
      TickJob(3, "FA Daily Recompute"),
      TickJob(10, "Cash Credit Daily"),
      TickJob(20, "Usage Profiling"),
      TickJob(30, "Some Spark Shell"),
      TickJob(40, "PySpark Notebook"))
      .map(_.toJValue))

  def getTickJobs = service {
    req => {
      val resp = http.Response(req.version, http.Status.Ok)
      resp.setContentString(tickJobs)
      resp
    }
  }


}
