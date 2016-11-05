name          := "data.enzyme"
organization  := "bio4j"
description   := "ENZYME ADT and flat file parsers"

bucketSuffix  := "era7.com"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test

testOptions       in Test += Tests.Argument("-oD")
parallelExecution in Test := false
