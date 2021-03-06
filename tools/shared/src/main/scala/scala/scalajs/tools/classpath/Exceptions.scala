/*                     __                                               *\
**     ________ ___   / /  ___      __ ____  Scala.js tools             **
**    / __/ __// _ | / /  / _ | __ / // __/  (c) 2013-2014, LAMP/EPFL   **
**  __\ \/ /__/ __ |/ /__/ __ |/_// /_\ \    http://scala-js.org/       **
** /____/\___/_/ |_/____/_/ | |__/ /____/                               **
**                          |/____/                                     **
\*                                                                      */


package scala.scalajs.tools.classpath

import scala.scalajs.tools.jsdep.ResolutionInfo

class MissingJSLibException(val dependencies: List[ResolutionInfo])
  extends Exception(MissingJSLibException.mkMsg(dependencies))

object MissingJSLibException {
  private def mkMsg(deps: List[ResolutionInfo]): String = {
    val msg = new StringBuilder()
    msg.append("Missing dependencies: \n")
    for (d <- deps) {
      msg.append(s"- ${d.resourceName}")
      msg.append(s" originating from: ${d.origins.mkString(", ")}\n")
    }
    msg.toString()
  }
}

