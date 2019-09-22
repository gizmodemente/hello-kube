import com.lightbend.lagom.core.LagomVersion

organization in ThisBuild := "org.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

val akkaServiceLocator = "com.lightbend.lagom" %% "lagom-scaladsl-akka-discovery-service-locator" % LagomVersion.current
val kubernetesDiscovery =  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % "1.0.1"
val akkaManagement = "com.lightbend.akka.management" %% "akka-management" % "1.0.1"
val akkaManagementClusterBootstrap = "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % "1.0.1"
val akkaManagementClusterHttp = "com.lightbend.akka.management" %% "akka-management-cluster-http" % "1.0.1"

lazy val `hello` = (project in file("."))
  .aggregate(`hello-api`, `hello-impl`)

lazy val `hello-api` = (project in file("hello-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-impl` = (project in file("hello-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      akkaServiceLocator,
      kubernetesDiscovery,
      akkaManagement,
      akkaManagementClusterBootstrap,
      akkaManagementClusterHttp,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-api`)
