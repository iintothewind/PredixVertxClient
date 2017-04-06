name := "VertxWebClient"

version := "1.0"

scalaVersion := "2.12.0"

lazy val vertx = (project in file(".")).
  settings(
    name := "VertxWebClient",
    version := "1.0",
    scalaVersion := "2.12.0",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-encoding", "UTF-8"
      //"-unchecked",
      //"-Xlint",
      //"-Ywarn-dead-code"
    )
  )

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
shellPrompt := { s => Project.extract(s).currentProject.id + "> " }

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

resolvers ++= {
  Seq(Resolver.sonatypeRepo("releases"))
}

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % "2.7",
  "org.apache.logging.log4j" % "log4j-jcl" % "2.7",
  "org.apache.logging.log4j" % "log4j-core" % "2.7",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "com.typesafe" % "config" % "1.3.1",
  "com.google.guava" % "guava" % "21.0",
  "org.scalatest" %% "scalatest" % "3.0.1",
  "io.vertx" % "vertx-core" % "3.4.0",
  "io.vertx" %% "vertx-web-client-scala" % "3.4.0",
  "io.vertx" %% "vertx-auth-oauth2-scala" % "3.4.0",
  "io.vertx" % "vertx-unit" % "3.4.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)
