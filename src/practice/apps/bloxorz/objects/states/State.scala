package practice.apps.bloxorz.objects.states

import java.io.File

import practice.apps.bloxorz.objects.{Block, EmptyMap, Map}

class State(val map: Map, val positionOfPlayer: ((Int, Int), (Int, Int))) {

  implicit def listToMap(list: List[List[Char]]): Map = new Map(list)

  def changeMap(newMap: List[List[Char]]): State = new State(newMap, positionOfPlayer)

  def changeField(fieldToChange: Char, coordinates: (Int, Int)): State = {
    if ((coordinates equals map.findStartPosition) || (coordinates equals map.findTerminalPosition)) {
      println("Deleting start or terminal position is impossible!")
      this
    } else
      new State(map.changeField(fieldToChange, coordinates), positionOfPlayer)
  }

  def swapField(fieldToChange: Char, coordinates: (Int, Int)): State = {
    val oldCoordinates = map.findPositionOfField(fieldToChange)
    new State(map.changeField(fieldToChange, coordinates).changeField(Map.defaultField, oldCoordinates),
      positionOfPlayer)
  }

  def writePossibilities(): Unit = {
    if (gameStarted) {
      map.toString(positionOfPlayer)
      println("--------Meni-------")
      printMoveCommands()
    } else {
      println("Current map is:")
      map.toString(positionOfPlayer)
      println("--------Meni-------")
      if (map != EmptyMap) {
        println("For starting game pres \"S\"")
        printChangeCommands()
      }
      importMapPrint()
    }
    println("---------------------------------")

  }

  def gameStarted: Boolean = {
    Block.invalidPosition != positionOfPlayer
  }

  def mapLoaded: Boolean = {
    this.map != EmptyMap
  }

  def printMoveCommands(): Unit = {
    println("For moving left pres \"L\"")
    println("For moving right pres \"R\"")
    println("For moving up pres \"G\"")
    println("For moving down pres \"D\"")

  }


  def printChangeCommands(): Unit = {

    println("For removing field enter \"Remove(coordinate1,coordinate2)\"")
    println("For adding default field enter \"Add(coordinate1,coordinate2)\"")
    println("For putting special field enter \"Special(coordinate1,coordinate2)\"")
    println("For changing coordinates of start field enter \"Start(coordinate1,coordinate2)\"")
    println("For changing coordinates of terminal field enter \"Terminal(coordinate1,coordinate2)\"")
  }

  def importMapPrint(): Unit = {
    println("For importing map enter \"U (name of available map) \"")
    printExistingMaps()
  }

  def printExistingMaps(): Unit = {
    println("Available maps:")
    val directory = new File("src/practice/apps/bloxorz/maps/")
    if (directory.exists && directory.isDirectory) {
      directory.listFiles.filter(_.isFile).toList.foreach(file => {
        val nameWithExtension = file.getName
        println(nameWithExtension.substring(0, nameWithExtension.length - 4))
      })
    }
  }

  def isInitialState: Boolean = {
    InitialState == this
  }
}

object InitialState extends State(EmptyMap, Block.invalidPosition) {

  override def writePossibilities(): Unit = {
    importMapPrint()
    println("---------------------------------")

  }
}