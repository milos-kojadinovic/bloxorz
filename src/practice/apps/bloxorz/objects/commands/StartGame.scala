package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.Block
import practice.apps.bloxorz.objects.states.State

class StartGame() extends Command {
  override def apply(state: State): State = {
    if (!state.mapLoaded) {
      println("Chose map first.")
      state
    } else {
      State(state.map, (state.map.findStartPosition(), Block.inAir), state.commandsHolder)
    }
  }
}
