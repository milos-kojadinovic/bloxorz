package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

class UnknownCommand extends Command {
  override def apply(state: State): State = {
    println("Unknown command")
    state
  }
}
