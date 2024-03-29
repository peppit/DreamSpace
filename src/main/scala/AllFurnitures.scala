/*
import scalafx.scene.paint.Color

case class Bed(shape: Shape, color: Color) extends Furniture(shape, color), Shape:

  private val overlaps = Array(Carpet)
  private val shapes = Array(RectangleObjects, EllipseObjects)

  def canPutOnto: Array[Furniture] = overlaps
  def availableShapes: Array[Shape] = shapes

  def available: Boolean = availableShapes.contains(shape)


case class Carpet(shape: Shape, color: Color) extends Furniture(shape, color):

  private val overlaps = Array[Furniture]()
  private val shapes = Array(RectangleObjects, EllipseObjects, RoundObjects, HalfRoundObjects)

  def canPutOnto: Array[Furniture] = overlaps
  def availableShapes: Array[Shape] = ???

  def available: Boolean = availableShapes.contains(shape)


case class Chair(shape: Shape, color: Color) extends Furniture(shape, color):

  private val overlaps = Array(Carpet)

  def canPutOnto: Array[Furniture] = overlaps

  def availableShapes: Array[Shape] = ???


case class Closet(shape: Shape, color: Color) extends Furniture(shape, color):

  private val overlaps = Array(Carpet)

  def canPutOnto: Array[Furniture] = ???

  def availableShapes: Array[Shape] = ???


case class Lamp(shape: Shape, color: Color) extends Furniture(shape, color):

  private val overlaps = Array(Carpet, Table)

  def canPutOnto: Array[Furniture] = overlaps

  def availableShapes: Array[Shape] = ???


case class Table(shape: Shape, color: Color) extends Furniture(shape, color):

  private val overlaps = Array(Carpet)

  def canPutOnto: Array[Furniture] = overlaps

  def availableShapes: Array[Shape] = ???


case class Sofa(shape: Shape, color: Color) extends Furniture(shape, color):

  private val overlaps = Array(Carpet)

  def canPutOnto: Array[Furniture] = overlaps

  def availableShapes: Array[Shape] = ???

*/