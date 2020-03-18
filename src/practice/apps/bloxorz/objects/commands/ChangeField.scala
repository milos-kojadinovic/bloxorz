package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class ChangeField(fieldToChange: Char, val coordinates: (Int, Int)) extends Command {
  override def apply(state: State): State = {
    if (!state.gameStarted && state.mapLoaded) {
      state.changeField(fieldToChange, coordinates)
    } else {
      if (state.gameStarted)
        println("Game already started, map can not be changed!")
      if (!state.mapLoaded)
        println("Please select map first!")
      state
    }
  }

}
