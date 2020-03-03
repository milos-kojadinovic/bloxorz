package practice.apps.bloxorz.objects.commands

import practice.apps.bloxorz.objects.states.State

trait Command {
  def apply(state: State): State
}
