import scalafx.scene.paint.Color
import scalafx.scene.shape.Shape

class Furniture(name: String, shape: Shape) extends Shape(shape):
  
  var x: Double = 0
  var y: Double = 0
  
  val shapeOut = shape
  
  def fits(s: Shape) = true     // onks tää täällä vai GUI:ssa

  def overLapMistake: Boolean = false    // Tää kans
  
  layoutX = y
  layoutY = x

 // def canPutOnto(furniture: Furniture): Boolean = 
  
//  def info(): String
  
  
