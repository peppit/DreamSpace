trait Furniture:

  def fits(s: Shape) = true

  def overLapMistake: Boolean = false

  def canPutOnto(furniture: Furniture): Boolean
  
  def info(): String
  
