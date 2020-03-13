package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class SwapFields(fieldToChange: Char, val coordinates: (Int, Int)) extends Command {
  override def apply(state: State): State = {
    if (!state.gameStarted && state.mapLoaded) {
      state.swapField(fieldToChange, coordinates)
    } else {
      println("Game already started, map can not be changed !")
      state
    }
  }
}
