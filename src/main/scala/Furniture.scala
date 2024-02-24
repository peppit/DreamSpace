trait Furniture(shape: Shape, color:Color):

  def fits(s: Shape) = ???

  def overLapMistake: Boolean = false

  def canPutOnto: Array[Furniture]

  def availableShapes: Array[Furniture]
  
  def info(): String
  
