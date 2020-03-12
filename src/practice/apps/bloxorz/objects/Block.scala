package practice.apps.bloxorz.objects

object Block {
  val inAir: (Int, Int) = (-32, -32)
  val invalidPosition: ((Int, Int), (Int, Int)) = ((-32, -32), (-32, -32))

  def isVertical(positionOfPlayer: ((Int, Int), (Int, Int))): Boolean = {
    positionOfPlayer._1._2 == positionOfPlayer._2._2
  }
}
