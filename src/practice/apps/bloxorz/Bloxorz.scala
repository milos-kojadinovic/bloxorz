package practice.apps.bloxorz

import java.util.Scanner

import practice.apps.bloxorz.objects.states.{InitialState, State};

object Bloxorz extends App {

  //Def of methods

  def executeCommand(state: State, command: String): State = {
    state.commandsHolder.from(command).apply(state)
  }

  //Main body

  println("Hello to Bloxorz game")
  val scanner = new Scanner(System.in)
  val initialState: State = InitialState

  initialState.writePossibilities()

  io.Source.stdin.getLines().foldLeft(initialState)((state, commandString) => {
    val newState = executeCommand(state, commandString)
    newState.writePossibilities()
    newState
  })

}
