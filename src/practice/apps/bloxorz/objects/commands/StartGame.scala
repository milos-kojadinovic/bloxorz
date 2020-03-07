package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class StartGame() extends Command {
  override def apply(state: State): State = {
    new State(state.map, state.map.findStartPosition())
  }
}
