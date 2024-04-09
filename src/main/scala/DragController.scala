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
      shape.setLayoutY(event.getSceneY + yOffset)
      shape.setLayoutX(event.getSceneX + xOffset)
      target.x = event.getSceneX + xOffset
      target.x = event.getSceneY + yOffset)

