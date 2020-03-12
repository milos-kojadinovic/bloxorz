package practice.apps.bloxorz.objects

import practice.apps.bloxorz.objects.commands.{Command, MoveCommand, OpenMap, StartGame, UnknownCommand}


object Parser {
  implicit def from(str: String): Command = str match {
    case s"U($name)" => {
      new OpenMap(name)
    }
    case "S" => new StartGame()
    case string: String if string.matches("[LRGD]") => new MoveCommand(string.charAt(0))
    case _ => new UnknownCommand()
  }

}
