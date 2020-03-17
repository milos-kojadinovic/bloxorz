package practice.apps.bloxorz.objects.commands

import java.io.File

import practice.apps.bloxorz.objects.Map
import practice.apps.bloxorz.objects.commands.error.CompositeCommandError
import practice.apps.bloxorz.objects.states.{InitialState, State}

import scala.collection.immutable.HashMap
import scala.util.Try

class CommandsHolder(val additionalCommands: HashMap[String, Command]) {

  def from(str: String): Command = str match {
    case s"U($name)" => new OpenMap(name)
    case "S" => new StartGame()
    case s"PlayFromFile($name)" => new PlayFromFile(name)
    case s"Remove($x,$y)" if checkCoordinates(x, y) => new ChangeField(Map.noField, (x.toInt, y.toInt))
    case s"Add($x,$y)" if checkCoordinates(x, y) => new ChangeField(Map.defaultField, (x.toInt, y.toInt))
    case s"Special($x,$y)" if checkCoordinates(x, y) => new ChangeField(Map.dot, (x.toInt, y.toInt))
    case s"Start($x,$y)" if checkCoordinates(x, y) => new SwapFields(Map.start, (x.toInt, y.toInt))
    case s"Terminal($x,$y)" if checkCoordinates(x, y) => new SwapFields(Map.terminal, (x.toInt, y.toInt))
    case string: String if string.matches("[LRGD]") => new MoveCommand(string.charAt(0))
    case s"Create($name=$commands)" => {

      val commandsList = Try {
        commands.split('+').map { str =>
          val commandToAdd = from(str.trim)
          if (commandToAdd equals UnknownCommand)
            throw new CompositeCommandError
          else commandToAdd
        }.toList
      }

      if (commandsList.isSuccess)
        new AddCompositeCommand(new CompositeCommand(name.trim, commandsList.get))
      else {
        println("Error creating composite command! Please try again.")
        UnknownCommand
      }
    }
    case compositeCommandName if additionalCommands.get(compositeCommandName).nonEmpty =>
      additionalCommands(compositeCommandName)
    case _ => UnknownCommand
  }

  def addCommand(compositeCommand: CompositeCommand): CommandsHolder = {
    new CommandsHolder(additionalCommands + (compositeCommand.name -> compositeCommand))
  }

  def checkCoordinates(x: String, y: String): Boolean = {
    Try(x.toInt).isSuccess && Try(y.toInt).isSuccess
  }

  //PRINT POSSIBILITIES
  def printPosibilities(state: State): Unit = {
    println("--------Meni-------")
    if (state equals InitialState) {
      importMapPrint()
    }
    else if (state.gameStarted) {
      state.printMap()
      printMoveCommands()
    } else {
      if (state.mapLoaded) {
        println("Current map is:")
        state.printMap()
      }
      println("--------Meni-------")
      if (state.mapLoaded) {
        println("For starting game pres \"S\"")
        println("For playing moves from file enter \"PlayFromFile(name of file in moves folder)\"")
        printChangeCommands()
      }
      println("For creating new composite function enter Create(name of new function=functions separated with \"+\")")
      if (additionalCommands.nonEmpty) {
        additionalCommands.keySet.foreach(name => println("For composite command " + name + " enter \"" + name + "\""))
      }
      importMapPrint()
    }
    println("---------------------------------")
  }

  def printMoveCommands(): Unit = {
    println("For moving left pres \"L\"")
    println("For moving right pres \"R\"")
    println("For moving up pres \"G\"")
    println("For moving down pres \"D\"")

  }

  def printChangeCommands(): Unit = {

    println("For removing field enter \"Remove(coordinate1,coordinate2)\"")
    println("For adding default field enter \"Add(coordinate1,coordinate2)\"")
    println("For putting special field enter \"Special(coordinate1,coordinate2)\"")
    println("For changing coordinates of start field enter \"Start(coordinate1,coordinate2)\"")
    println("For changing coordinates of terminal field enter \"Terminal(coordinate1,coordinate2)\"")
  }

  def importMapPrint(): Unit = {
    println("For importing map enter \"U (name of available map) \"")
    printExistingMaps()
  }

  def printExistingMaps(): Unit = {
    println("Available maps:")
    val directory = new File("src/practice/apps/bloxorz/maps/")
    if (directory.exists && directory.isDirectory) {
      directory.listFiles.filter(_.isFile).toList.foreach(file => {
        val nameWithExtension = file.getName
        println(nameWithExtension.substring(0, nameWithExtension.length - 4))
      })
    }
  }

}

object DefaultCommandsHolder extends CommandsHolder(HashMap()) {
}
