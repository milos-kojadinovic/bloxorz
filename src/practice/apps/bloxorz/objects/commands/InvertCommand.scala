package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class InvertCommand extends Command {
  override def apply(state: State): State = {
    if (state.mapLoaded)
      state.swapStartTerminal()
    else {
      println("Select map first")
      state
    }
  }
}
