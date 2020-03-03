package practice.apps.bloxorz.objects.states

import java.io.File

import practice.apps.bloxorz.objects.{EmptyMap, Map}

class State(val map: Map, val positionOfPlayer: (Int, Int)) {

  implicit def listToMap(list: List[List[Char]]): Map = new Map(list)

  def changeMap(newMap: List[List[Char]]): State = new State(newMap, positionOfPlayer)

  def writePossibilities(): Unit = {
    println("Current map is:")
    println(map.toString)

  }
}


object InitialState extends State(EmptyMap, (0, 0)) {

  override def writePossibilities(): Unit = {
    println("For importing map enter \"U (name of existing map) \"")
    printExistingMaps()
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
