
```scala
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

  test("check first entry") {

    val firstEntry = allEntries.head

    assert {

      ( firstEntry.ID === "1.1.1.1"                                 ) &&
      ( firstEntry.subSubClassID === "1.1.1.-"                      ) &&
      ( firstEntry.description === "Alcohol dehydrogenase"          ) &&
      ( firstEntry.alternativeNames === Seq("Aldehyde reductase")   ) &&
      (
        firstEntry.catalyticActivity === "(1) An alcohol + NAD(+) = an aldehyde or ketone + NADH. (2) A secondary alcohol + NAD(+) = a ketone + NADH."
                                                                    ) &&
      ( firstEntry.cofactors === Seq("Zn(2+) or Fe cation")         ) &&
      firstEntry.comments === Seq(
          "Acts on primary or secondary alcohols or hemi-acetals with very broad specificity; however the enzyme oxidizes methanol much more poorly than ethanol",
          "The animal, but not the yeast, enzyme acts also on cyclic secondary alcohols"
        )
    }
  }
}

```




[test/scala/EnzymeEntries.scala]: EnzymeEntries.scala.md
[test/scala/EnzymeClasses.scala]: EnzymeClasses.scala.md
[main/scala/entry.scala]: ../../main/scala/entry.scala.md
[main/scala/flat/entry.scala]: ../../main/scala/flat/entry.scala.md
[main/scala/flat/classes.scala]: ../../main/scala/flat/classes.scala.md