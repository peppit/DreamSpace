/*import scalafx.scene.paint.Color

case class Bed(shape: Shape, color: Color) extends Furniture:

  def shapeOut = shape

  def canPutOnto(furniture: Furniture) =
    if furniture == Carpet then
      true
    else false

  def info() = "This is Bed"


case class Carpet(shape: Shape, color: Color) extends Furniture:

  def shapeOut = shape

  def canPutOnto(furniture: Furniture) = false   //you can't put carpet over any furniture.

  def info() = "This is Carpet"


case class Chair(shape: Shape, color: Color) extends Furniture:

  def shapeOut = shape

  private val overlaps = Array(Carpet)

  def canPutOnto(furniture: Furniture) =
    if furniture == Carpet then
      true
    else false

  def info() = "This is Chair"


case class Closet(shape: Shape, color: Color) extends Furniture:
  def shapeOut = shape

  def canPutOnto(furniture: Furniture) =
    if furniture == Carpet then
      true
    else false

  def info() = "This is Closet"


case class Lamp(shape: Shape, color: Color) extends Furniture:

  def shapeOut = shape

  def canPutOnto(furniture: Furniture) = true

  def info() = "This is Lamp"


case class Table(shape: Shape, color: Color) extends Furniture:

  def shapeOut = shape

  def canPutOnto(furniture: Furniture) =
    if furniture == Carpet then
      true
    else false

  def info() = "This is Table"


class Sofa(shape: Shape, color: Color) extends Furniture:

  def shapeOut = shape

  def canPutOnto(furniture: Furniture) =
    if furniture == Carpet then
      true
    else false

  def info() = "Sofa"

*/
