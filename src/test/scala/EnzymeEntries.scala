package com.bio4j.data.enzyme.test

import org.scalatest.FunSuite

import com.bio4j.data.enzyme._

class ParseEnzymeEntries extends FunSuite {

  lazy val lines =
    io.Source.fromFile("enzyme.dat").getLines.toSeq

  lazy val allEntries = flat.entries.fromLines(lines)


  test("parse all entries and access all data") {

    allEntries foreach { e =>

      val id                = e.ID
      val subSubClassID     = e.subSubClassID
      val description       = e.description
      val alternativeNames  = e.alternativeNames
      val cofactors         = e.cofactors
      val catalyticActivity = e.catalyticActivity
      val comments          = e.comments
    }
  }
}
