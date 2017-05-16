import com.typesafe.sbt.SbtAspectj.{ Aspectj, aspectjSettings, compiledClasses }
import com.typesafe.sbt.SbtAspectj.AspectjKeys.{ binaries, inputs, lintProperties }

name := "play-java-ebean-example"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean, PlayEnhancer)

libraryDependencies += jdbc
libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.0-P25-B3"
libraryDependencies += evolutions
libraryDependencies += cache 
libraryDependencies += filters

libraryDependencies += "org.aspectj" % "aspectjrt" % "1.8.9"
libraryDependencies += "org.aspectj" % "aspectjweaver" % "1.8.9"


aspectjSettings

inputs in Aspectj <+= compiledClasses

products in Compile <<= products in Aspectj

products in Runtime <<= products in Compile

javaOptions += "-javaagent:~/.ivy2/cache/org.aspectj/aspectjweaver/jars/aspectjweaver-1.8.9.jar -noverify"
fork in run := true