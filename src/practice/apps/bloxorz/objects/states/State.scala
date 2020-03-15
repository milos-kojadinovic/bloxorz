package practice.apps.bloxorz.objects.states

import practice.apps.bloxorz.objects.commands.{CommandsHolder, DefaultCommandsHolder}
import practice.apps.bloxorz.objects.{Block, EmptyMap, Map}

class State(val map: Map, val positionOfPlayer: ((Int, Int), (Int, Int)), val commandsHolder: CommandsHolder) {

  implicit def listToMap(list: List[List[Char]]): Map = new Map(list)

  def changeMap(newMap: List[List[Char]]): State = new State(newMap, positionOfPlayer, commandsHolder)

  def changeField(fieldToChange: Char, coordinates: (Int, Int)): State = {
    if ((coordinates equals map.findStartPosition) || (coordinates equals map.findTerminalPosition)) {
      println("Deleting start or terminal position is impossible!")
      this
    } else
      new State(map.changeField(fieldToChange, coordinates), positionOfPlayer, commandsHolder)
  }

  def swapField(fieldToChange: Char, coordinates: (Int, Int)): State = {
    val oldCoordinates = map.findPositionOfField(fieldToChange)
    new State(map.changeField(fieldToChange, coordinates).changeField(Map.defaultField, oldCoordinates),
      positionOfPlayer, commandsHolder)
  }

  def writePossibilities(): Unit = {
    commandsHolder.printPosibilities(this)
  }

  def gameStarted: Boolean = {
    Block.invalidPosition != positionOfPlayer
  }

  def mapLoaded: Boolean = {
    this.map != EmptyMap
  }

  def isInitialState: Boolean = {
    InitialState == this
  }

  def printMap(): Unit = {
    map.toString(positionOfPlayer)
  }

}

object InitialState extends State(EmptyMap, Block.invalidPosition, DefaultCommandsHolder) {

  override def writePossibilities(): Unit = {
    commandsHolder.printPosibilities(this)
  }
}