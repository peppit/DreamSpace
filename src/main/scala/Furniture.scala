import scalafx.scene.paint.Color
import scalafx.scene.shape.Shape

class Furniture(name: String, shape: Shape, color: Color) extends Shape(shape):


  val nameOut = name   //Here we get the name of the furniture for method overlapMistake and Data
  val colorOut = color  //Here we get the color of the furniture for Data
  
//Here we get the shape of the furniture for Data and other methods when moving the furniture
  val shapeOut = shape 
  
  //Here we get the position of the furniture for Data
  var x: Double = this.shape.getLayoutX
  var y: Double = this.shape.getLayoutY
  
// This method checks if the current funrniture overlaps another furniture and if it's possible to overlap
// returns true if there is an mistake: Furniture overlaps another furniture in a way that should not be possible in real life
// returns false if the overlap is possible
  def overlapMistake: Boolean =
    val thisFurnitureOut = Main.furnitures.filter( f => f != this)
  
    if (this.nameOut == "Lamp") then
      false
    else if (nameOut == "Carpet") && thisFurnitureOut.exists(f => f.getBoundsInParent.intersects(this.getBoundsInParent)) then
      println(" 1 ")
      true
    else if (nameOut == "TV") && thisFurnitureOut.filter(f => f.nameOut != "Carpet" && f.nameOut != "Table" ).exists(f => f.getBoundsInParent.intersects(this.getBoundsInParent)) then
      println(" 1 ")
      true
    else if nameOut != "Lamp" && nameOut != "Carpet" && nameOut != "TV" && thisFurnitureOut.filter(f => f.nameOut != "Lamp" && f.nameOut != "Carpet" ).exists(f => f.getBoundsInParent.intersects(this.getBoundsInParent)) then
      println(" 2 ")
      println(nameOut + " !!")
      true
    else false

  
  
