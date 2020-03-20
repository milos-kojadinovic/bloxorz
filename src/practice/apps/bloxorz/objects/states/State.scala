package practice.apps.bloxorz.objects.states

import practice.apps.bloxorz.objects.commands.{CommandsHolder, CompositeCommand, DefaultCommandsHolder}
import practice.apps.bloxorz.objects.{Block, EmptyMap, Map}

case class State(map: Map, positionOfPlayer: ((Int, Int), (Int, Int)), val commandsHolder: CommandsHolder) {

  def reset(): State = {
    State(EmptyMap, Block.invalidPosition, commandsHolder)
  }

  implicit def listToMap(list: List[List[Char]]): Map = new Map(list)

  def addCompositeCommand(compositeCommand: CompositeCommand): State = {
    State(map, positionOfPlayer, commandsHolder.addCommand(compositeCommand))
  }

  def changeMap(newMap: List[List[Char]]): State = State(newMap, positionOfPlayer, commandsHolder)

  def changeField(fieldToChange: Char, coordinates: (Int, Int)): State = {
    if ((coordinates equals map.findStartPosition) || (coordinates equals map.findTerminalPosition)) {
      println("Deleting start or terminal position is impossible!")
      this
    } else
      State(map.changeField(fieldToChange, coordinates), positionOfPlayer, commandsHolder)
  }

  def swapField(fieldToChange: Char, coordinates: (Int, Int)): State = {
    val oldCoordinates = map.findPositionOfField(fieldToChange)
    State(map.changeField(fieldToChange, coordinates).changeField(Map.defaultField, oldCoordinates),
      positionOfPlayer, commandsHolder)
  }

  def swapStartTerminal(): State = {
    val oldStart = map.findStartPosition()
    val oldTerminal = map.findTerminalPosition()
    val newMap = map.changeField('S', oldTerminal).changeField('T', oldStart)
    State(newMap, positionOfPlayer, commandsHolder)
  }

  def removeSpecial(): State = {
    State(map.removeSpecial(), positionOfPlayer, commandsHolder)
  }

  def filterSpecial(coordinate: (Int, Int), distance: Int): State = {
    State(map.filterSpecial(coordinate, distance), positionOfPlayer, commandsHolder)
  }

  def moveToPosition(newPosition: ((Int, Int), (Int, Int))): State = State(map, newPosition, commandsHolder)

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