package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.Parser
import practice.apps.bloxorz.objects.states.{InitialState, State}

import scala.annotation.tailrec
import scala.io.Source

class PlayFromFile(name: String) extends Command {

  override def apply(state: State): State = {
    val commands = loadCommands()
    doAllCommands(new StartGame().apply(state), commands)
  }

  def loadCommands(): List[Command] = {
    val file = Source.fromFile(s"src/practice/apps/bloxorz/maps/${name}.txt")
    val commandsList: List[Command] =
      for {line <- file.getLines.toList} yield Parser.from(line)
    file.close()
    commandsList
  }

  @tailrec
  private def doAllCommands(state: State, commands: List[Command]): State = {
    if (state equals InitialState) return state
    println(s"---------- After command: ${commands.head.toString} ----------")
    state.map.toString(state.positionOfPlayer)
    if (commands.isEmpty) state
    else doAllCommands(commands.head.apply(state), commands.tail)
  }

}