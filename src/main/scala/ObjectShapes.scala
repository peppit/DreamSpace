import scalafx.scene.paint.Color
import scalafx.scene.Node 


case class EllipseObject(diameterX: Double, diameterY: Double) extends Shape:

  def area: Double = scala.math.Pi * (diameterX/2) * (diameterY/2)



case class HalfRoundObject(diameterX: Double, radiusY: Double) extends Shape:

  def area: Double = (scala.math.Pi * (diameterX/2) * radiusY)/2



case class RectangleObject(sideL1: Double, sideL2: Double, color: Color) extends Shape:

  def area = sideL1 * sideL2
  
  

case class RoundObject(diameter: Double, color: Color) extends Shape:

  def area = scala.math.Pi * diameter * diameter


