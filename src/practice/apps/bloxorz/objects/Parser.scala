package practice.apps.bloxorz.objects

import practice.apps.bloxorz.objects.commands.{Command, OpenMap}

object Parser {
  implicit def from(str: String): Command = str match {
    case s"U($name)" => {
      new OpenMap(name)
    }
  }

}
