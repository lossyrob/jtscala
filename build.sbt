name := "jtscala"

scalaVersion := "2.10.3"

scalacOptions ++= Seq(
              "-deprecation", 
              "-unchecked", 
              "-language:implicitConversions", 
              "-language:postfixOps", 
              "-language:existentials", 
              "-feature")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.1" % "test",
  "com.vividsolutions" % "jts" % "1.12"
)

resolvers ++= Seq(
  "Scala Test" at "http://www.scala-tools.org/repo-reloases/",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots"
)

