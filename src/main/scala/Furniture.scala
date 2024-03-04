import scalafx.scene.paint.Color

trait Furniture(name: String, shape: Shape, color: Color) extends App:

  def fits(s: Shape) = ???

  def overLapMistake: Boolean = false

  def canPutOnto: Array[Furniture]

  def availableShapes: Array[Furniture]
  
  def info(): String
  
