
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

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-api" % "2.7",
  "org.apache.logging.log4j" % "log4j-jcl" % "2.7",
  "org.apache.logging.log4j" % "log4j-core" % "2.7",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
  "com.typesafe" % "config" % "1.3.3",
  "com.google.guava" % "guava" % "21.0",
  "org.scalatest" %% "scalatest" % "3.0.5",
  "io.vertx" % "vertx-core" % "3.5.1",
  "io.vertx" %% "vertx-web-client-scala" % "3.5.1",
  "io.vertx" %% "vertx-auth-oauth2-scala" % "3.5.1",
  "io.vertx" % "vertx-unit" % "3.5.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
)
