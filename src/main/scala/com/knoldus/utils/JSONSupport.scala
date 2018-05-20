package com.knoldus.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.knoldus.models.{Item, ItemsList}
import com.knoldus.utils.ResponseUtil.Response

class JSONSupport extends SprayJsonSupport {

  import spray.json.DefaultJsonProtocol._

  implicit val itemJsonFormat = jsonFormat3(Item)
  implicit val repsonseJson = jsonFormat2(Response)
  implicit val itemsList = jsonFormat1(ItemsList)

}
