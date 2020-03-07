package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.{GameOverState, State, WinState}
import practice.apps.bloxorz.objects.Block.inAir

class MoveCommand(direction: Char) extends Command {

  def move(positionOfPlayer: ((Int, Int), (Int, Int))): ((Int, Int), (Int, Int)) = {

    if (positionOfPlayer._2 == inAir) {
      direction match {
        case 'L' =>
          ((positionOfPlayer._1._1 - 2, positionOfPlayer._1._2), (positionOfPlayer._1._1 - 1, positionOfPlayer._1._2))
        case 'R' =>
          ((positionOfPlayer._1._1 + 1, positionOfPlayer._1._2), (positionOfPlayer._1._1 + 2, positionOfPlayer._1._2))
        case 'D' =>
          ((positionOfPlayer._1._1, positionOfPlayer._1._2 + 1), (positionOfPlayer._1._1, positionOfPlayer._1._2 + 2))
        case 'G' =>
          ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 2), (positionOfPlayer._1._1, positionOfPlayer._1._2 - 1))
      }
    } else {
      direction match {
        case 'L' => {
          if (positionOfPlayer._1._1 == positionOfPlayer._2._1) { // isVertical
            ((positionOfPlayer._1._1 - 1, positionOfPlayer._1._2), (positionOfPlayer._2._1 - 1, positionOfPlayer._2._2))
          } else ((positionOfPlayer._1._1 - 1, positionOfPlayer._1._2), inAir)
        }
        case 'R' => {
          if (positionOfPlayer._1._1 == positionOfPlayer._2._1) { // isVertical
            ((positionOfPlayer._1._1 + 1, positionOfPlayer._1._2), (positionOfPlayer._2._1 + 1, positionOfPlayer._2._2))
          } else ((positionOfPlayer._2._1 + 1, positionOfPlayer._2._2), inAir)
        }
        case 'D' => {
          if (positionOfPlayer._1._1 == positionOfPlayer._2._1) { // isVertical
            ((positionOfPlayer._2._1, positionOfPlayer._2._2 + 1), inAir)
          } else
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 + 1), (positionOfPlayer._2._1, positionOfPlayer._2._2 + 1))
        }
        case 'G' => {
          if (positionOfPlayer._1._1 == positionOfPlayer._2._1) { // isVertical
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 1), inAir)
          } else
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 1), (positionOfPlayer._2._1, positionOfPlayer._2._2 - 1))
        }
      }
    }

  }

  override def apply(state: State): State = {
    val newPosition = move(state.positionOfPlayer)
    if (state.map.checkGameOver(newPosition))
      GameOverState
    else if (state.map.checkWin(newPosition)) {
      WinState
    } else
      new State(state.map, newPosition)
  }
}
