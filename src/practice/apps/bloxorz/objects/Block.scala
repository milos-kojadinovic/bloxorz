package practice.apps.bloxorz.objects

object Block {
  val inAir: (Int, Int) = (-32, -32)
  val invalidPosition: ((Int, Int), (Int, Int)) = ((-32, -32), (-32, -32))

  def isVertical(positionOfPlayer: ((Int, Int), (Int, Int))): Boolean = {
    positionOfPlayer._1._2 == positionOfPlayer._2._2
  }

  def move(positionOfPlayer: ((Int, Int), (Int, Int)), direction: Char): ((Int, Int), (Int, Int)) = {
    if (positionOfPlayer._2 == inAir) {
      direction match {
        case 'L' =>
          ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 2), (positionOfPlayer._1._1, positionOfPlayer._1._2 - 1))
        case 'R' =>
          ((positionOfPlayer._1._1, positionOfPlayer._1._2 + 1), (positionOfPlayer._1._1, positionOfPlayer._1._2 + 2))
        case 'D' =>
          ((positionOfPlayer._1._1 + 1, positionOfPlayer._1._2), (positionOfPlayer._1._1 + 2, positionOfPlayer._1._2))
        case 'G' =>
          ((positionOfPlayer._1._1 - 2, positionOfPlayer._1._2), (positionOfPlayer._1._1 - 1, positionOfPlayer._1._2))
      }
    } else {
      direction match {
        case 'L' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 1), (positionOfPlayer._2._1, positionOfPlayer._2._2 - 1))
          } else ((positionOfPlayer._1._1, positionOfPlayer._1._2 - 1), inAir)
        }
        case 'R' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._1._1, positionOfPlayer._1._2 + 1), (positionOfPlayer._2._1, positionOfPlayer._2._2 + 1))
          } else ((positionOfPlayer._2._1, positionOfPlayer._2._2 + 1), inAir)
        }
        case 'D' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._2._1 + 1, positionOfPlayer._2._2), inAir)
          } else
            ((positionOfPlayer._1._1 + 1, positionOfPlayer._1._2), (positionOfPlayer._2._1 + 1, positionOfPlayer._2._2))
        }
        case 'G' => {
          if (isVertical(positionOfPlayer)) { // isVertical
            ((positionOfPlayer._1._1 - 1, positionOfPlayer._1._2), inAir)
          } else
            ((positionOfPlayer._1._1 - 1, positionOfPlayer._1._2), (positionOfPlayer._2._1 - 1, positionOfPlayer._2._2))
        }
      }
    }

  }

}
