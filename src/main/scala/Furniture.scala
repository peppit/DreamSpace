import scalafx.scene.paint.Color
import scalafx.scene.shape.Shape

class Furniture(name: String, shape: Shape, color: Color) extends Shape(shape):


  val nameOut = name
  val colorOut = color
  
  var x: Double = this.shape.getLayoutX
  var y: Double = this.shape.getLayoutY
  
  val shapeOut = shape

  def overlapMistake: Boolean =
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

  
  
