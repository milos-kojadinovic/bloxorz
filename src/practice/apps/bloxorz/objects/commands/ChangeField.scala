package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class ChangeField(fieldToChange: Char, val coordinates: (Int, Int)) extends Command {
  override def apply(state: State): State = {
    if (!state.gameStarted && state.mapLoaded) {
      state.changeField(fieldToChange, coordinates)
    } else {
      println("Game already started, map can not be changed !")
      state
    }
  }

}
