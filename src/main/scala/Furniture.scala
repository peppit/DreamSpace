import scalafx.scene.paint.Color
import scalafx.scene.shape.Shape

class Furniture(name: String, shape: Shape) extends Shape(shape):
  
  var x = layoutX.toDouble
  var y = layoutY.toDouble
  
  val shapeOut = shape
  
  def fits(s: Shape) = true     // onks tää täällä vai GUI:ssa

  def overLapMistake: Boolean = false    // Tää kans
  
  this.translateY = y
  this.translateX = x

 // def canPutOnto(furniture: Furniture): Boolean = 
  
//  def info(): String
  
  
