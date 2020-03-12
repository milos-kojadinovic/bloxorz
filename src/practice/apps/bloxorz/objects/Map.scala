package practice.apps.bloxorz.objects

class Map(val fields: List[List[Char]]) {

  def addRow(position: Int): Map = ???

  def addColumn(position: Int): Map = ???

  def deleteRow(position: Int): Map = ???

  def deleteColumn(position: Int): Map = ???

  def changeField(x: Int, y: Int): Map = ???

  def findStartPosition(): ((Int, Int), (Int, Int)) = {
    (findPositionOfField('S'), Block.inAir)
  }

  def findPositionOfField(field: Char): (Int, Int) = {
    @scala.annotation.tailrec
    def findPositionHelper(mapMatrix: List[List[Char]], row: Int): (Int, Int) = {

      @scala.annotation.tailrec
      def findStartRowHelper(row: List[Char], column: Int): Int = {
        if (row.tail.isEmpty) -1
        else if (row.head == field) column
        else findStartRowHelper(row.tail, column + 1)
      }

      val foundColumn = findStartRowHelper(mapMatrix.head, 0)
      if (foundColumn != -1) (row, foundColumn)
      else findPositionHelper(mapMatrix.tail, row + 1)
    }

    findPositionHelper(fields, 0)
  }


  def isTerminal(field: Char): Boolean = Map.terminalCharactersDefeat.contains(field)

  def isDot(field: Char): Boolean = Map.terminalCharactersDot.contains(field)

  def isFieldWin(field: Char): Boolean = Map.terminalCharactersWin.contains(field)

  def notInAir(position: (Int, Int)): Boolean = {
    !isInAir(position)
  }

  def isInAir(position: (Int, Int)): Boolean = {
    position == Block.inAir
  }

  def isOutOfBounds(block: ((Int, Int), (Int, Int))): Boolean = {
    val column = fields.head.length
    val rows = fields.length
    (block._1._1 >= rows || block._2._1 >= rows) || (block._1._1 < 0 || (notInAir(block._2) && block._2._1 < 0)) ||
      ((block._1._2 >= column || block._2._2 >= column) || (block._1._2 < 0 || (notInAir(block._2) && block._2._2 < 0)))
  }

  def checkGameOver(block: ((Int, Int), (Int, Int))): Boolean = {
    isTerminal(getField(block._1)) || (notInAir(block._2) && isTerminal(getField(block._2))) ||
      (isInAir(block._2) && isDot(getField(block._1))) || isOutOfBounds(block)
  }

  def checkWin(position: ((Int, Int), (Int, Int))): Boolean = {
    isInAir(position._2) && isFieldWin(getField(position._1))
  }

  def getField(position: (Int, Int)): Char = {

    @scala.annotation.tailrec
    def getFieldHelper(mapMatrix: List[List[Char]], row: Int): Char = {

      @scala.annotation.tailrec
      def findStartRowHelper(row: List[Char], column: Int): Char = {
        if (column == position._2) row.head
        else findStartRowHelper(row.tail, column + 1)
      }

      if (row == position._1) findStartRowHelper(mapMatrix.head, 0)
      else getFieldHelper(mapMatrix.tail, row + 1)
    }

    getFieldHelper(fields, 0)
  }

  override def toString: String = {
    fields.map(line => {
      line.map(char => char)
    }.mkString(" ")).mkString("\n")
  }

  def toString(positionOfPlayer: ((Int, Int), (Int, Int))): Unit = {

    @scala.annotation.tailrec
    def printMapHelper(mapMatrix: List[List[Char]], row: Int): Unit = {

      @scala.annotation.tailrec
      def printRowHelper(rowFields: List[Char], column: Int): Unit = {
        if ((row, column).equals(positionOfPlayer._1) || (row, column).equals(positionOfPlayer._2)) print(" B")
        else print(" " + rowFields.head)
        if (rowFields.tail.nonEmpty)
          printRowHelper(rowFields.tail, column + 1)
      }

      printRowHelper(mapMatrix.head, 0)
      println()
      if (mapMatrix.tail.nonEmpty) {
        printMapHelper(mapMatrix.tail, row + 1)
      }

    }

    printMapHelper(fields, 0)
  }

}

object Map {
  val terminalCharactersDefeat: String = "–-"
  val terminalCharactersDot: String = "."
  val terminalCharactersWin: String = "T"
}


object EmptyMap extends Map(List.empty) {

}
