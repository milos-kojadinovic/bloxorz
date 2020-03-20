package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.Block
import practice.apps.bloxorz.objects.states.State

class MoveCommand(direction: Char) extends Command {

  override def apply(state: State): State = {
    if (!state.gameStarted) {
      println("Start the game first!")
      println("---------------------------------")
      state
    } else {
      val newPosition = Block.move(state.positionOfPlayer, direction)
      if (state.map.checkGameOver(newPosition)) {
        println("GAME OVER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("---------------------------------")
        state.reset()
      } else if (state.map.checkWin(newPosition)) {
        println("GAME WON!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("---------------------------------")
        state.reset()
      } else
        state.moveToPosition(newPosition)
    }
  }

  override def toString: String = "Move command: " + direction
}
