package com.knoldus.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.knoldus.ItemHTTPService
import com.knoldus.dao.components.ItemComponent

import scala.concurrent.Await
import scala.concurrent.duration.Duration


//#main-class
object ItemHTTPServer extends App {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("KnolStoreHTTPServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //#main-class
  // from the UserRoutes trait
  lazy val routes: Route = itemHTTPService.route
  val itemHTTPService = new ItemHTTPService(ItemComponent)
  //#main-class

  //#http-server
  Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)
  //#http-server
  //#main-class
}
