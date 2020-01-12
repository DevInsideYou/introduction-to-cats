ThisBuild / organization := "com.devinsideyou"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-language:_",
  "-unchecked",
  "-Wunused:_",
  "-Xfatal-warnings",
  "-Ymacro-annotations"
)

import Dependencies._

lazy val `intoduction-to-cats` =
  project
    .in(file("."))
    .settings(
      name := "Intoduction to Cats",
      addCompilerPlugin(org.typelevel.`kind-projector`),
      libraryDependencies ++= Seq(
        org.typelevel.`cats-core`
      ),
      libraryDependencies ++= Seq(
        com.github.alexarchambault.`scalacheck-shapeless_1.14`,
        org.scalacheck.scalacheck,
        org.scalatest.scalatest
      ).map(_ % Test),
      dependencyOverrides ++= Seq(
        org.scalatest.scalatest
      ),
      Compile / console / scalacOptions --= Seq(
        "-Wunused:_",
        "-Xfatal-warnings"
      ),
      Test / console / scalacOptions :=
        (Compile / console / scalacOptions).value
    )
