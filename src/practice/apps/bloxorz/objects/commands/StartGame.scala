package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.Block
import practice.apps.bloxorz.objects.states.{InitialState, State}

class StartGame() extends Command {
  override def apply(state: State): State = {
    if (state eq InitialState) {
      println("Chose map first.")
      state
    } else {
      new State(state.map, (state.map.findStartPosition(), Block.inAir))

    }
  }
}
