import sbt._
import sbt.Keys._

object JTScalaBuild extends Build {

  // Default settings
  override lazy val settings = super.settings ++
    Seq(
      version := "0.1.0-SNAPSHOT",
      scalaVersion := "2.10.3",
      organization := "com.azavea.geotrellis",

      // disable annoying warnings about 2.10.x
      conflictWarning in ThisBuild := ConflictWarning.disable,
      scalacOptions ++= 
        Seq("-deprecation",
          "-unchecked",
          "-Yclosure-elim",
          "-Yinline-warnings",
          "-optimize",
          "-language:implicitConversions",
          "-language:postfixOps",
          "-language:existentials",
          "-feature"),

      publishMavenStyle := true,
      
      publishTo <<= version { (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases"  at nexus + "service/local/staging/deploy/maven2")
      },

      publishArtifact in Test := false,

      pomIncludeRepository := { _ => false },
      licenses := Seq("Apache 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
      homepage := Some(url("http://geotrellis.github.io/")),

      pomExtra := (

    <scm>
      <url>git@github.com:geotrellis/geotrellis.git</url>
      <connection>scm:git:git@github.com:geotrellis/geotrellis.git</connection>
    </scm>
    <developers>
      <developer>
        <id>lossyrob</id>
        <name>Rob Emanuele</name>
        <url>http://github.com/lossyrob/</url>
      </developer>
    </developers>
      )
    )

  // Project: jtscala

  lazy val jtscala = 
    Project("jtscala", file("core"))
      .settings(jtscalaSettings:_*)

  lazy val jtscalaSettings = 
    Seq(
      name := "jtscala",
      libraryDependencies ++= Seq(
        "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
        "org.scalacheck" %% "scalacheck" % "1.11.1" % "test",
        "com.vividsolutions" % "jts" % "1.13"
      )
    )

  // Project: benchmark

  lazy val jtscala_benchmark = 
    Project("benchmark", file("benchmark"))
      .settings(jtscalaBenchmarkSettings:_*)
      .dependsOn(jtscala % "compile->test")

  lazy val jtscalaBenchmarkSettings =
    Seq(
      name := "jtscala-benchmark",
      libraryDependencies ++= Seq(
        "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
        "org.scalacheck" %% "scalacheck" % "1.11.1" % "test",
        "com.vividsolutions" % "jts" % "1.13"
      )
    )
}
