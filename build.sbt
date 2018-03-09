
lazy val vertxWebClient = (project in file(".")).
  settings(
    name := "VertxWebClient",
    version := "1.0",
    scalaVersion := "2.12.4",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-encoding", "UTF-8"
      //"-unchecked",
      //"-Xlint",
      //"-Ywarn-dead-code"
    )
  )

//testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
shellPrompt := { s => Project.extract(s).currentProject.id + "> " }

//ivyScala := ivyScala.value map {
//  _.copy(overrideScalaVersion = true)
//}

ensimeRepositoryUrls in ThisBuild += "http://maven.aliyun.com/nexus/content/groups/public"
ensimeIgnoreScalaMismatch in ThisBuild := true
ensimeJavaFlags in ThisBuild := Seq("-Xss512M", "-Xmx4G", "-XX:MaxMetaspaceSize=768M")

resolvers ++= Seq(
  Resolver.mavenLocal,
  MavenRepository("aliyun", "http://maven.aliyun.com/nexus/content/groups/public")
)

lazy val log4jVersion = "2.7"
lazy val vertxVersion = "3.4.2"
lazy val latest = "latest.integration"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-jcl" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
  "com.typesafe" % "config" % "1.3.3",
  "org.scalatest" %% "scalatest" % "3.0.5",
  "io.vertx" % "vertx-core" % vertxVersion,
  "io.vertx" %% "vertx-web-client-scala" % vertxVersion,
  "io.vertx" %% "vertx-auth-oauth2-scala" % vertxVersion,
  "io.vertx" % "vertx-unit" % vertxVersion % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)
