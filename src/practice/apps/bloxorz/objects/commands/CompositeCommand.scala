package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class CompositeCommand(val name: String, commands: List[Command]) extends Command {

  override def apply(state: State): State = {
    @scala.annotation.tailrec
    def applyCommand(state: State, commands: List[Command]): State = {
      if (commands.isEmpty) state
      else applyCommand(commands.head(state), commands.tail)
    }

    applyCommand(state, commands)
  }

}
