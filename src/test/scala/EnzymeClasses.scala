package com.bio4j.data.enzyme.test

import org.scalatest.FunSuite

import com.bio4j.data.enzyme._

class ParseEnzymeClasses extends FunSuite {

  def lines =
    io.Source.fromFile("enzclass.txt").getLines

  def allEnzymeClasses = flat.enzymeClasses.fromLines(lines)

  test("parse all enzyme classes") {

    allEnzymeClasses.foreach { e =>

      val clazz = e
    }
  }

  /*
    This is unlikely to change
  */
  test("check first classes") {

    val firstFive = (allEnzymeClasses take 5).toList

    assert {

      firstFive === List[EnzymeClasses](
        EnzymeClass("1.-.-.-", "Oxidoreductases"),
        EnzymeSubClass("1.1.-.-", "Acting on the CH-OH group of donors"),
        EnzymeSubSubClass("1.1.1.-", "With NAD(+) or NADP(+) as acceptor"),
        EnzymeSubSubClass("1.1.2.-", "With a cytochrome as acceptor"),
        EnzymeSubSubClass("1.1.3.-", "With oxygen as acceptor")
      )
    }
  }
}
