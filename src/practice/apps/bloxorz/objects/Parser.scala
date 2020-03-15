package practice.apps.bloxorz.objects

import practice.apps.bloxorz.objects.commands._

import scala.util.Try


object Parser {
  implicit def from(str: String): Command = str match {
    case s"U($name)" => new OpenMap(name)
    case "S" => new StartGame()
    case s"PlayFromFile($name)" => new PlayFromFile(name)
    case s"Remove($x,$y)" if checkCoordinates(x, y) => new ChangeField(Map.noField, (x.toInt, y.toInt))
    case s"Add($x,$y)" if checkCoordinates(x, y) => new ChangeField(Map.defaultField, (x.toInt, y.toInt))
    case s"Special($x,$y)" if checkCoordinates(x, y) => new ChangeField(Map.dot, (x.toInt, y.toInt))
    case s"Start($x,$y)" if checkCoordinates(x, y) => new SwapFields(Map.start, (x.toInt, y.toInt))
    case s"Terminal($x,$y)" if checkCoordinates(x, y) => new SwapFields(Map.terminal, (x.toInt, y.toInt))
    case string: String if string.matches("[LRGD]") => new MoveCommand(string.charAt(0))
    case _ => new UnknownCommand()
  }

  def checkCoordinates(x: String, y: String): Boolean = {
    Try(x.toInt).isSuccess && Try(y.toInt).isSuccess
  }

}
