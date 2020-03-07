package practice.apps.bloxorz.objects.states

import java.io.File

import practice.apps.bloxorz.objects.states.InitialState.printExistingMaps
import practice.apps.bloxorz.objects.{EmptyMap, Map}

class State(val map: Map, val positionOfPlayer: ((Int, Int), (Int, Int))) {

  implicit def listToMap(list: List[List[Char]]): Map = new Map(list)

  def changeMap(newMap: List[List[Char]]): State = new State(newMap, positionOfPlayer)

  def writePossibilities(): Unit = {
    println("Current map is:")
    map.toString(positionOfPlayer)
    println("For starting game enter \"S\"")
    importMapPrint()
  }

  def importMapPrint(): Unit = {
    println("For importing map enter \"U (name of existing map) \"")
    printExistingMaps()
  }

}

object InitialState extends State(EmptyMap, ((-1, -1), (-1, -1))) {

  override def writePossibilities(): Unit = {
    importMapPrint()
  }

  def printExistingMaps(): Unit = {
    println("Existing maps:")
    val directory = new File("src/practice/apps/bloxorz/maps/")
    if (directory.exists && directory.isDirectory) {
      directory.listFiles.filter(_.isFile).toList.foreach(file => {
        val nameWithExtension = file.getName
        println(nameWithExtension.substring(0, nameWithExtension.length - 4))
      })
    }
    println("---------------------------------")
  }

}

object GameOverState extends State(EmptyMap, ((-1, -1), (-1, -1))) {

  override def writePossibilities(): Unit = {
    println("GAME OVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    println("---------------------------------")
  }

}

object WinState extends State(EmptyMap, ((-1, -1), (-1, -1))) {

  override def writePossibilities(): Unit = {
    println("GAME WON!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
    println("---------------------------------")
  }
}