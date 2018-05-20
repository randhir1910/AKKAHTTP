package com.knoldus.dao.components

import com.knoldus.dao.connections.{DBComponent, MySqlDbComponent}
import com.knoldus.models.Item
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future


trait ItemComponent extends ItemTable with DBComponent {

  def getAllItem(): Future[List[Item]] = {
    db.run(itemQuery.to[List].result)
  }

  def addItem(item: Item): Future[Int] = {

    db.run(itemQuery += item)
  }

  def updateItem(itemId: Int, item: Item): Future[Int] = {

    db.run(itemQuery.filter(_.itemId === itemId).update(item))
  }

  def deleteItem(itemId:Int): Future[Int] = {
    db.run(itemQuery.filter(_.itemId === itemId).delete)
  }
}

object ItemComponent extends ItemComponent with MySqlDbComponent

trait ItemTable {

  val itemQuery = TableQuery[ItemTable]

  class ItemTable(tag: Tag) extends Table[Item](tag, "item_detail") {
    def * = (itemId, itemName, price) <> (Item.tupled, Item.unapply)

    def itemId = column[Int]("item_id", O.PrimaryKey)

    def itemName = column[String]("item_name")

    def price = column[Double]("price")
  }

}