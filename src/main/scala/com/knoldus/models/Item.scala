package com.knoldus.models

case class Item(itemId:Int,itemName:String,price:Double)
case class ItemsList(items: List[Item])
