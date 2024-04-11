import scalafx.scene.shape.Shape

class Furniture(name: String, shape: Shape) extends Shape(shape):

  val nameOut = name
  
  var x: Double = 0
  var y: Double = 0
  
  val shapeOut = shape
  
  def fits(s: Shape) = true     // onks tää täällä vai GUI:ssa

  def overLapMistake: Boolean =
    if name == "Carpet" && Main.furnitures.exists(f => f.intersects(this.getLayoutBounds)) then
      true
    else if name != "Lamp" && name != "Carpet" && Main.furnitures.filter(f => f.nameOut != "Lamp" && f.nameOut != "Carpet" ).exists(f => f.intersects(this.getLayoutBounds)) then
      true
    else false


  layoutX = y
  layoutY = x

 // def canPutOnto(furniture: Furniture): Boolean = 
  
//  def info(): String
  
  
