trait Furniture:

  def fits(s: Shape) = true     // onks tää täällä vai GUI:ssa

  def overLapMistake: Boolean = false    // Tää kans

  def canPutOnto(furniture: Furniture): Boolean
  
  def info(): String
  
