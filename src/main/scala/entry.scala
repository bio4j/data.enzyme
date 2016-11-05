package com.bio4j.data.enzyme

trait AnyEntry extends Any {

  def ID: String
  def subSubClassID: String

  def description: String
  def alternativeNames: Seq[String]
  def cofactors: Seq[String]
  def catalyticActivity: String
  def comments: Seq[String]
}

sealed trait EnzymeClasses extends Any {

  def ID: String
  def description: String
}
  case class EnzymeClass(val ID: String, val description: String) extends EnzymeClasses
  case class EnzymeSubClass(val ID: String, val description: String) extends EnzymeClasses
  case class EnzymeSubSubClass(val ID: String, val description: String) extends EnzymeClasses
