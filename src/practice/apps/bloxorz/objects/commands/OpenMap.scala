package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

import scala.io.Source
import scala.util.Try

class OpenMap(name: String) extends Command {


  def readMap(): List[List[Char]] = {

    val file = Source.fromFile("src/practice/apps/bloxorz/maps/" + name + ".map")
    val fieldsList: List[List[Char]] =
      for {line <- file.getLines.toList} yield line.toList
    file.close()
    fieldsList

  }

  override def apply(state: State): State = {

    val newMapTry = Try {
      readMap()
    }
    if (newMapTry.isSuccess) state.changeMap(newMapTry.get)
    else {
      println("Invalid name of map. Try again.")
      state
    }
  }

}
