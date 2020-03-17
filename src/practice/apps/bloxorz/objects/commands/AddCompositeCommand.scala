package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class AddCompositeCommand(compositeCommand: CompositeCommand) extends Command {
  override def apply(state: State): State = {
    state.addCompositeCommand(compositeCommand)
  }
}
