package practice.apps.bloxorz.objects

class Map(val fields: List[List[Char]]) {

  def addRow(position: Int): Map = ???

  def addColumn(position: Int): Map = ???

  def deleteRow(position: Int): Map = ???

  def deleteColumn(position: Int): Map = ???

  def changeField(x: Int, y: Int): Map = ???

  def startPosition(): (Int, Int) = ???

  override def toString: String = {
    fields.map(line => {
      line.map(char => char)
    }.mkString(" ")).mkString("\n")
  }

}

object EmptyMap extends Map(List.empty) {

}
