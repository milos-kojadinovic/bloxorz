package practice.apps.bloxorz.objects.commands

import java.io.{File, PrintWriter}

import practice.apps.bloxorz.objects.Block
import practice.apps.bloxorz.objects.states.State

class SolveCommand extends Command {

  override def apply(state: State): State = {
    if (!state.gameStarted && state.mapLoaded) {
      val startState = new StartGame().apply(state)
      val initialState = (startState, List())
      writeSolution(solve(List(initialState), List()))
    } else {
      if (state.gameStarted)
        println("Game already started, map can not be changed!")
      if (!state.mapLoaded)
        println("Please select map first!")
    }
    state
  }

  def notGameOverPositions(state: State): List[(Char, ((Int, Int), (Int, Int)))] = {
    val moves = List(
      ('L', Block.move(state.positionOfPlayer, 'L')),
      ('R', Block.move(state.positionOfPlayer, 'R')),
      ('D', Block.move(state.positionOfPlayer, 'D')),
      ('G', Block.move(state.positionOfPlayer, 'G')),
    )

    moves.filter(move => {
      println("MOVE:" + move)
      !state.map.checkGameOver(move._2)
    })
  }

  @scala.annotation.tailrec
  private def solve(toVisit: List[(State, List[Char])], visited: List[State]): List[Char] = {
    if (toVisit.isEmpty)
      return List()

    val state = toVisit.head._1
    val previousMoves = toVisit.head._2

    val nonGameOverPositions = notGameOverPositions(state)
    val gameWonMove = nonGameOverPositions.filter(move => state.map.checkWin(move._2))

    if (gameWonMove.nonEmpty)
      previousMoves :+ gameWonMove.head._1
    else {
      val newStates = nonGameOverPositions.map(move => {
        (state.moveToPosition(move._2), previousMoves :+ move._1)
      }).filter(state => !visited.contains(state._1))
      solve(toVisit.tail.concat(newStates), visited :+ state)
    }
  }

  def writeSolution(moves: List[Char]): Unit = {
    val file = new File("src/practice/apps/bloxorz/moves/solution.txt")
    val pw = new PrintWriter(file)

    val string = moves.mkString("\n")

    pw.write(string)
    pw.close()
  }

}
