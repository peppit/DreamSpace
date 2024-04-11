import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import scalafx.beans.property.DoubleProperty
import scalafx.Includes.jfxNode2sfx

import scala.language.postfixOps
import scalafx.scene.shape.{Circle, Shape}


class DragController:

  private var anchorX: Double = 0
  private var anchorY: Double = 0
  private var yOffset: Double = 0
  private var xOffset: Double = 0
  private var previousY: Double = 0
  private var previousX: Double = 0
  private var cycleStatus: Double = 0    //when cycleStatus is 0 it's INACTIVE, when 1 it's ACTIVE


  def createHandlers(target: Furniture) =
    val shape = target.shapeOut
    shape.onMousePressed = (event) =>
      if (event.isPrimaryButtonDown) then
        cycleStatus = 1
        anchorY = event.getSceneY
        anchorX = event.getSceneX
       // previousX = shape.getLayoutX
     //   previousY = shape.getLayoutY
        yOffset = target.y - anchorY
        xOffset = target.x -anchorX
      else if  event.isSecondaryButtonDown then
        cycleStatus = 1
        shape.setTranslateX(0)
        shape.setTranslateY(0)
      else ()

    shape.setOnMouseDragged((event: MouseEvent) =>
      if (cycleStatus != 0) then
        shape.setTranslateX(event.getSceneX - anchorX)
        shape.setTranslateY(event.getSceneY - anchorY))

    shape.setOnMouseReleased((event: MouseEvent) =>
        println(shape.getLayoutX.toString + ", " + shape.getLayoutY.toString)
        shape.setLayoutY(event.getSceneY + yOffset)
        shape.setLayoutX(event.getSceneX + xOffset)
        target.x = event.getSceneX + xOffset
        target.y = event.getSceneY + yOffset
        shape.setTranslateX(0)
        shape.setTranslateY(0)
        println(shape.getLayoutX.toString + ", " + shape.getLayoutY.toString))


/*  def createDraggableProperty(target: Furniture) =
    val shape = target.shapeOut
    var isDraggable = new SimpleBooleanProperty()
    isDraggable.addListener(observalbe, oldValue, newValue)
*/


  def makeDraggable(target: Furniture): Unit =
    val shape = target.shapeOut

    shape.onMousePressed = (event) =>
      anchorY = event.getSceneY
      anchorX = event.getSceneX
      previousX = shape.getLayoutX
      previousY = shape.getLayoutY
      yOffset = target.x - anchorY
      xOffset = target.x -anchorX

    shape.setOnMouseDragged((event: MouseEvent) =>
      shape.setTranslateX(event.getSceneX - anchorX)
      shape.setTranslateY(event.getSceneY - anchorY))

    shape.setOnMouseReleased((event: MouseEvent) =>
      println(shape.getLayoutX.toString + ", " + shape.getLayoutY.toString)
      shape.setLayoutY(event.getSceneY + yOffset)
      shape.setLayoutX(event.getSceneX + xOffset)
      target.x = event.getSceneX + xOffset
      target.x = event.getSceneY + yOffset)


