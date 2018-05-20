package com.knoldus

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.google.inject.Inject
import com.knoldus.dao.components.ItemComponent
import com.knoldus.models.{Item, ItemsList}
import com.knoldus.utils.JSONSupport
import com.knoldus.utils.ResponseUtil._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.control.NonFatal

class ItemHTTPService @Inject()(itemComponent: ItemComponent) extends JSONSupport {

  lazy val route: Route =
    cors() {
      pathPrefix("item") {
        addItem ~ deleteItem ~ getAllItem ~ updateItem

      }
    }

  def addItem: Route =
    post {
      entity(as[Item]) { itemData =>
        complete {
          itemComponent.addItem(itemData)
            .map { result =>
              Response(
                "Record Added successfully !!",
                StatusCodes.InternalServerError.intValue)
            }.recover {
            case NonFatal(exception) =>
              exception.printStackTrace()
              Response(
                "Could not get record from database",
                StatusCodes.InternalServerError.intValue)
          }
        }
      }
    }

  def deleteItem: Route = {
    path("deleteItem" / IntNumber) { id =>
      delete {
        entity(as[Item]) { item =>
          complete {
            itemComponent.deleteItem(id).map {
              case _ => Response("item deleted successfully", StatusCodes.InternalServerError.intValue)
            }
          }
        }
      }
    }
  }

  def getAllItem: Route = {
    path("getItem") {
      get {
        complete {
          itemComponent.getAllItem().map {
            itemList => ItemsList(itemList)
          }
        }
      }
    }
  }

  def updateItem: Route = {
    path("updateItem" / IntNumber) { id =>
      put {
        entity(as[Item]) { item =>
          complete {
            itemComponent.updateItem(id, item).map {
              case _ => Response("item updated successfully", StatusCodes.InternalServerError.intValue)
            }
          }
        }
      }
    }
  }
}