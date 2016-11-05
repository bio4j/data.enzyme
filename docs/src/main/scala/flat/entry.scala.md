
```scala
package com.bio4j.data.enzyme.flat

import com.bio4j.data.enzyme._

case class Entry(val lines: Seq[String]) extends AnyEntry {

  def ID: String =
    id.value

  def subSubClassID: String =
    id.subSubClassID

  def description: String =
    de.description

  def alternativeNames: Seq[String] =
    an.alternativeNames

  def cofactors: Seq[String] =
    cf.cofactors

  def catalyticActivity: String =
    ca.catalyticActivity

  def comments: Seq[String] =
    cc.comments

  private lazy val id: ID =
    new ID(linesWith(prefix = "ID").head)

  private lazy val de: DE =
    DE(linesWith(prefix = "DE"))

  private lazy val an: AN =
    AN(linesWith(prefix = "AN"))

  private lazy val cf: CF =
    CF(linesWith(prefix = "CF"))

  private lazy val ca: CA =
    CA(linesWith(prefix = "CA"))

  private lazy val cc: CC =
    CC(linesWith(prefix = "CC"))

  private def linesWith(prefix: String): Seq[String] =
    lines collect { case line if(line startsWith prefix) => line.stripPrefix(prefix).trim  }
}

private case class ID(val value: String) extends AnyVal {

  def subSubClassID: String =
    s"${value.reverse.dropWhile(_ != '.').reverse}-"
}

private case class DE(val lines: Seq[String]) extends AnyVal {

  def description: String =
    lines
      .map(_.trim.stripSuffix("."))
      .mkString(" ")
}

private case class AN(val lines: Seq[String]) extends AnyVal {

  def alternativeNames: Seq[String] =
    lines
      .mkString(" ")
      .split('.')
}

private case class CF(val lines: Seq[String]) extends AnyVal {

  def cofactors: Seq[String] =
    lines
      .mkString("")
      .split(';')
      .map(_.trim.stripSuffix("."))
}

private case class CA(val lines: Seq[String]) extends AnyVal {

  def catalyticActivity: String =
    lines.mkString(" ")
}

private case class CC(val lines: Seq[String]) extends AnyVal {

  def comments: Seq[String] =
    lines.mkString(" ")
      .split("-!-")
      .collect { case txt if(txt.nonEmpty) => txt.trim.stripSuffix(".") }
}

case object entries {
```


ENZYME entries file have a "header" consisting on CC lines and an end of entry // line.


```scala
  def fromLines(lines: Seq[String]): Seq[Entry] =
    entryLines(lines.dropWhile( l => l.startsWith("CC") || l.startsWith("//") )).map { Entry(_) }

  def validFromLines(lines: Seq[String]): Seq[Entry] =
    fromLines(lines) filter isValid
```


See ftp://ftp.expasy.org/databases/enzyme/enzuser.txt


```scala
  private def isValid(entry: Entry): Boolean =
    !( entry.description.startsWith("Deleted entry") || entry.description.startsWith("Transferred entry") )

  @annotation.tailrec
  private def entryLinesRec(
    currentLine: Option[String],
    linesLeft: Seq[String],
    entryAcc: Seq[String],
    acc: Seq[Seq[String]]
  )
  : Seq[Seq[String]] =
    currentLine match {
      case None       => acc
      case Some(line) => {

        if(isEndLine(line))
          entryLinesRec(
            currentLine = linesLeft.headOption,
            linesLeft   = if(linesLeft.isEmpty) Seq() else linesLeft.tail,
            entryAcc    = Seq(),
            acc         = acc :+ entryAcc
          )
        else
          entryLinesRec(
            currentLine = linesLeft.headOption,
            linesLeft   = if(linesLeft.isEmpty) Seq() else linesLeft.tail,
            entryAcc    = entryAcc :+ line,
            acc         = acc
          )
      }
    }

  private def entryLines(lines: Seq[String]): Seq[Seq[String]] =
    entryLinesRec(
      currentLine = lines.headOption,
      linesLeft   = lines.tail,
      entryAcc    = Seq(),
      acc         = Seq()
    )

  private def isEndLine(line: String) =
    line.startsWith("//")
}

```




[test/scala/EnzymeEntries.scala]: ../../../test/scala/EnzymeEntries.scala.md
[test/scala/EnzymeClasses.scala]: ../../../test/scala/EnzymeClasses.scala.md
[main/scala/entry.scala]: ../entry.scala.md
[main/scala/flat/entry.scala]: entry.scala.md
[main/scala/flat/classes.scala]: classes.scala.md