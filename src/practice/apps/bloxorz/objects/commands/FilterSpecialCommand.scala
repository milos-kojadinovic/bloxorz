package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class FilterSpecialCommand extends Command {
  override def apply(state: State): State = {
    if (!state.gameStarted && state.mapLoaded) {
      state.removeSpecial()
    } else {
      if (state.gameStarted)
        println("Game already started, map can not be changed!")
      if (!state.mapLoaded)
        println("Please select map first!")
      state
    }
  }
}
