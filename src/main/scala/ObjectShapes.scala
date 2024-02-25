case class EllipseObjects(diameterX: Double, diameterY: Double) extends Shape:

  def area: Double = scala.math.Pi * (diameterX/2) * (diameterY/2)




case class HalfRoundObjects(diameterX: Double, radiusY: Double) extends Shape:

  def area: Double = (scala.math.Pi * (diameterX/2) * radiusY)/2



case class RectangleObjects(sideL1: Double, sideL2: Double) extends Shape:

  def area = sideL1 * sideL2
  
  


case class RoundObjects(diameter: Double) extends Shape:

  def area = scala.math.Pi * diameter * diameter


