import Main.stage
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import scalafx.beans.property.DoubleProperty
import scalafx.Includes.jfxNode2sfx
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import scala.language.postfixOps
import scalafx.scene.shape.{Circle, Shape}
import scalafx.Includes.jfxDialogPane2sfx


class DragController:

  private var anchorX: Double = 0
  private var anchorY: Double = 0
  private var yOffset: Double = 0
  private var xOffset: Double = 0
  private var previousY: Double = 0
  private var previousX: Double = 0
  private var cycleStatus: Double = 0    //when cycleStatus is 0 it's INACTIVE, when 1 it's ACTIVE

  private var forTheFirstMove = 0


  def createHandlers(target: Furniture) =

    //Alert that pops up if the furniture is in a place where it's not possible to be.
    val overLapMistakeAlert = new Alert(AlertType.Error):
      initOwner(stage)
      title = "Exception Dialog"
      headerText = "Overlap mistake."
      contentText = "You can not put this furniture here!"
      // Set expandable Exception into the dialog pane.
      //dialogPane().expandableContent = expContent
  //  .showAndWait()

    val shape = target.shapeOut
    shape.onMousePressed = (event) =>
      previousX = event.getSceneX
      previousY = event.getSceneY
      if (event.isPrimaryButtonDown) then
        cycleStatus = 1
        anchorY = event.getSceneY
        anchorX = event.getSceneX
        println(anchorX.toString + "this is Ax" + anchorY.toString + "this is Ay")

        if forTheFirstMove != 0 then
          yOffset = target.y - anchorY
          xOffset = target.x - anchorX
          println(xOffset.toString + "this is Ox" + xOffset.toString + "this is Oy")
        else
          yOffset = shape.getLayoutY - anchorY
          xOffset = shape.getLayoutX - anchorX
          
      else if  event.isSecondaryButtonDown then
        cycleStatus = 1
        shape.setTranslateX(0)
        shape.setTranslateY(0)
      else ()
      forTheFirstMove += 1


    shape.setOnMouseDragged((event: MouseEvent) =>
      if (cycleStatus != 0) then
        shape.setTranslateX(event.getSceneX - anchorX)
        shape.setTranslateY(event.getSceneY - anchorY))

    shape.setOnMouseReleased((event: MouseEvent) =>
        if target.overLapMistake then
          overLapMistakeAlert.showAndWait()
          shape.setLayoutY(previousY + yOffset)
          shape.setLayoutX(previousX + xOffset) 
          shape.setTranslateX(0)
          shape.setTranslateY(0)
        else
          println(shape.getLayoutX.toString + ", " + shape.getLayoutY.toString)
          shape.setLayoutY(event.getSceneY + yOffset)
          shape.setLayoutX(event.getSceneX + xOffset)
          target.x = event.getSceneX + xOffset
          target.y = event.getSceneY + yOffset
          shape.setTranslateX(0)
          shape.setTranslateY(0)

        forTheFirstMove += 1


        println(shape.getLayoutX.toString + ", " + shape.getLayoutY.toString))



