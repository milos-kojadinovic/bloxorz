package practice.apps.bloxorz.objects.states

import java.io.File

import practice.apps.bloxorz.objects.{Block, EmptyMap, Map}

class State(val map: Map, val positionOfPlayer: ((Int, Int), (Int, Int))) {

  implicit def listToMap(list: List[List[Char]]): Map = new Map(list)

  def changeMap(newMap: List[List[Char]]): State = new State(newMap, positionOfPlayer)

  def writePossibilities(): Unit = {
    if (gameStarted) {
      map.toString(positionOfPlayer)
      println("--------Meni-------")
      moveCommands()
    } else {
      println("Current map is:")
      map.toString(positionOfPlayer)
      println("--------Meni-------")
      if (map != EmptyMap) {
        println("For starting game pres \"S\"")
      }
      importMapPrint()
    }
    println("---------------------------------")

  }

  def gameStarted(): Boolean = {
    Block.invalidPosition != positionOfPlayer
  }

  def moveCommands(): Unit = {
    println("For moving left pres \"L\"")
    println("For moving right pres \"R\"")
    println("For moving up pres \"G\"")
    println("For moving down pres \"D\"")

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

}

object InitialState extends State(EmptyMap, Block.invalidPosition) {

  override def writePossibilities(): Unit = {
    importMapPrint()
    println("---------------------------------")

  }
}