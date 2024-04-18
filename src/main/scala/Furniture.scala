import scalafx.scene.shape.Shape

class Furniture(name: String, shape: Shape) extends Shape(shape):

  val nameOut = name
  
  var x: Double = 400
  var y: Double = 50
  
  val shapeOut = shape
  
  def fits(s: Shape) = true     // onks tää täällä vai GUI:ssa

  def overLapMistake: Boolean =
    val thisFurnitureOut = Main.furnitures.filter( f => f != this)
    if this.nameOut == "Lamp" then
      println("3")
      false
    else if (nameOut == "Carpet") && thisFurnitureOut.exists(f => f.getBoundsInParent.intersects(this.getBoundsInParent)) then
      println(" 1 ")
      true
    else if nameOut != "Lamp" && nameOut != "Carpet" && thisFurnitureOut.filter(f => f.nameOut != "Lamp" && f.nameOut != "Carpet" ).exists(f => f.getBoundsInParent.intersects(this.getBoundsInParent)) then
      println(" 2 ")
      println(nameOut + " !!")
      true
    else false


  layoutX = y
  layoutY = x

 // def canPutOnto(furniture: Furniture): Boolean = 
  
//  def info(): String
  
  
