package mon4all.api


import com.twitter.finagle.http.{Method, HttpMuxer, Request, Response}
import com.twitter.finagle.{Service}
import com.twitter.finagle.http

import com.twitter.util.{Await, Future}

object Helpers {
  object service {
    def apply(func: http.Request => http.Response) = new Service[Request, Response] {
      def apply(req: http.Request): Future[http.Response] = {
        Future value(func(req))
      }
    }
  }
}
