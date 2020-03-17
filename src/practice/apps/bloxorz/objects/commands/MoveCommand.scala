package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.Block.{inAir, isVertical}
import practice.apps.bloxorz.objects.states.{InitialState, State}

class MoveCommand(direction: Char) extends Command {

  def move(positionOfPlayer: ((Int, Int), (Int, Int))): ((Int, Int), (Int, Int)) = {

    if (positionOfPlayer._2 == inAir) {
      direction match {
        case 'L' =>
          ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 2), (positionOfPlayer._1._1, positionOfPlayer._1._2 - 1))
        case 'R' =>
          ((positionOfPlayer._1._1, positionOfPlayer._1._2 + 1), (positionOfPlayer._1._1, positionOfPlayer._1._2 + 2))
        case 'D' =>
          ((positionOfPlayer._1._1 + 1, positionOfPlayer._1._2), (positionOfPlayer._1._1 + 2, positionOfPlayer._1._2))
        case 'G' =>
          ((positionOfPlayer._1._1 - 2, positionOfPlayer._1._2), (positionOfPlayer._1._1 - 1, positionOfPlayer._1._2))
      }
    } else {
      direction match {
        case 'L' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 1), (positionOfPlayer._2._1, positionOfPlayer._2._2 - 1))
          } else ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 1), inAir)
        }
        case 'R' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 + 1), (positionOfPlayer._2._1, positionOfPlayer._2._2 + 1))
          } else ((positionOfPlayer._2._1, positionOfPlayer._2._2 + 1), inAir)
        }
        case 'D' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._2._1 + 1, positionOfPlayer._2._2), inAir)
          } else
            ((positionOfPlayer._1._1 + 1, positionOfPlayer._1._2), (positionOfPlayer._2._1 + 1, positionOfPlayer._2._2))
        }
        case 'G' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._1._1 - 1, positionOfPlayer._1._2), inAir)
          } else
            ((positionOfPlayer._1._1 - 1, positionOfPlayer._1._2), (positionOfPlayer._2._1 - 1, positionOfPlayer._2._2))
        }
      }
    }

  }

  override def apply(state: State): State = {
    if (!state.gameStarted) {
      println("Start the game first!")
      println("---------------------------------")
      state
    } else {
      val newPosition = move(state.positionOfPlayer)
      if (state.map.checkGameOver(newPosition)) {
        println("GAME OVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("---------------------------------")
        state.reset()
      } else if (state.map.checkWin(newPosition)) {
        println("GAME WON!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("---------------------------------")
        state.reset()
      } else
        new State(state.map, newPosition,state.commandsHolder)
    }
  }

  override def toString: String = "Move command: " + direction
}
