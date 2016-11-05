package com.bio4j.data.enzyme.flat

import com.bio4j.data.enzyme._

case class ClassLine(val line: String) {

  final def asEnzymeClass: EnzymeClasses = {

    ID match {
      // the order is important here
      case id if (id == classID)        => EnzymeClass(id, description)
      case id if (id == subClassID)     => EnzymeSubClass(id, description)
      case id if (id == subSubClassID)  => EnzymeSubSubClass(id, description)
    }
  }

  /*
    In the `enzclass.txt` source file the id always takes 9 characters, but it has funny empty spaces around.
  */
  private lazy val ID: String =
    line
      .take(9)
      .filter(_ != ' ')

  private lazy val IDFragments: (String,String,String,String) = {

    val fragments = ID.split('.').take(4)

    (fragments(0), fragments(1), fragments(2), fragments(3))
  }

  private def classID: String =
    s"${IDFragments._1}.-.-.-"

  private def subClassID: String =
    s"${IDFragments._1}.${IDFragments._2}.-.-"

  private def subSubClassID: String =
    s"${IDFragments._1}.${IDFragments._2}.${IDFragments._3}.-"

  /*
    We don't want to store the description with a dot at the end!
  */
  private lazy val description: String =
    line
      .drop(9)
      .trim
      .stripSuffix(".")
}

case object enzymeClasses {

  /*
    The Enzyme source file `enzclass.txt` starts with:

    ```
    ---------------------------------------------------------------------------
            ENZYME nomenclature database
            SIB Swiss Institute of Bioinformatics; Geneva, Switzerland
    ----------------------------------------------------------------------------

    Description: Definition of enzyme classes, subclasses and sub-subclasses
    Name:        enzclass.txt
    Release:     07-Sep-2016

    ----------------------------------------------------------------------------

    1. -. -.-  Oxidoreductases.
    1. 1. -.-   Acting on the CH-OH group of donors.
    ```

    it also ends with:

    ```
    ----------------------------------------------------------------------------
    Copyrighted by the SIB Swiss Institute of Bioinformatics.
    There are no restrictions on its use by any institutions as long as
    its content is in no way modified.
    ----------------------------------------------------------------------------
    ```

    so we are only picking lines with a dot in the second char.

    Note that there empty lines now and then, which need to be filtered out too.
  */
  def fromLines(lines: Iterator[String]): Iterator[EnzymeClasses] =
    lines
      .filter(_.nonEmpty)
      .collect { case line if(line(1) == '.') => ClassLine(line).asEnzymeClass }
}
