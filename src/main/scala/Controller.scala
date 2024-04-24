import Main.stage
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import scalafx.scene.control.{Alert, Button, ButtonType, ContextMenu, Dialog, Label, MenuButton, MenuItem, TextField}
import scalafx.scene.control.Alert.AlertType
import scala.language.postfixOps
import scalafx.scene.shape.{Circle, Shape}


class Controller:

  // These stores the cordinates for calculations
  private var anchorX: Double = 0
  private var anchorY: Double = 0

  // These offsets represent the difference between the current position of the shape and the position
  // of the mouse cursor when the button was pressed.
  // This ensures that when the shape is dragged, it maintains its relative position to the mouse cursor.
  private var yOffset: Double = 0
  private var xOffset: Double = 0

  // the coordinates are stored here for the overlapMistake.
  // if there is a problem with overlapping the program returns the shape in it's previous state.
  private var previousY: Double = 0
  private var previousX: Double = 0

  // When cycleStatus is 0 it's INACTIVE, when 1 it's ACTIVE
  // cycleStatus checks that the user presses the left button of the mouse and not the right.
  private var cycleStatus: Double = 0

  // Checks if the movement is the furinture's first.
  private var forTheFirstMove = 0

  // This is the method that handles the event of dragging Furnitures:
  def createHandlers(target: Furniture) =

  //shape that is being moved is separated here from the furniture
    val shape = target.shapeOut

  //Alert that pops up if the furniture is in a place where it's not possible to be.
    val overLapMistakeAlert = new Alert(AlertType.Error):
      initOwner(stage)
      title = "Exception Dialog"
      headerText = "Overlap mistake."
      contentText = "You can not put this furniture here!"

  // MenuItem and contextMenu which stores the MenuItem for
  // rotating the furnitrues are here:
    val menuForRotate = new MenuItem("Rotate")
      menuForRotate.onAction = (event) => shape.setRotate(shape.getRotate + 45)  //rotates shapse 45 degrees clockwise
    val contextMenu = new ContextMenu(menuForRotate)

  // What happens when the furniture is pressed:
    shape.onMousePressed = (event) =>
  // stores the starting point of the furniture before moving
      previousX = event.getSceneX
      previousY = event.getSceneY

  // When the primary button (left button of mouse) is pressed
  // the starting point is stored in anchors where the coordinates are used for
  // calculations for the new point (point where the furniture is moved).
      if (event.isPrimaryButtonDown) then
        cycleStatus = 1
        anchorY = event.getSceneY
        anchorX = event.getSceneX
        
  // If the moving event is this furnitures first move the calculations are
  // based on the furnitures layouts. Else the calculations are taken from the furnitures
  // updated coordinates.
        if forTheFirstMove != 0 then
          yOffset = target.y - anchorY
          xOffset = target.x - anchorX
        else
          yOffset = shape.getLayoutY - anchorY
          xOffset = shape.getLayoutX - anchorX
        forTheFirstMove += 1   //here we update the count of the moves
      
   // When the secondary button (right button of mouse) is pressed the Button 
   // "Rotate" is shown.
      else if  event.isSecondaryButtonDown then
        cycleStatus = 0
        contextMenu.show(stage, event.getScreenX, event.getScreenY)
      else ()

  // What happenes when the mouse is dragged:
  // These translations allow the shape to follow the movement of the mouse cursor during the dragging operation
    shape.setOnMouseDragged((event: MouseEvent) =>
      if (cycleStatus != 0) then
        shape.setTranslateX(event.getSceneX - anchorX)
        shape.setTranslateY(event.getSceneY - anchorY))

  // When the mouse is released it updates the coordinates to the moved spot
  // If there is an overlaping mistake it returns the furniture to it's previous coordinates.
    shape.setOnMouseReleased((event: MouseEvent) =>

      if cycleStatus != 0 then
  //When not wanted overlapping happens:
        if target.overlapMistake then
          overLapMistakeAlert.showAndWait()  // Alert pops up.
          shape.setLayoutY(previousY + yOffset)  // Furniture is returned to it's previous spot
          shape.setLayoutX(previousX + xOffset)
  // updating the coordinates to the furniture object:
          target.x = previousX + xOffset
          target.y = previousY + yOffset
          shape.setTranslateX(0)
          shape.setTranslateY(0)
        else

  //Here the furniture is getting located in the moved spot.
          shape.setLayoutY(event.getSceneY + yOffset)
          shape.setLayoutX(event.getSceneX + xOffset)
  // updating the coordinates to the furniture object
          target.x = event.getSceneX + xOffset
          target.y = event.getSceneY + yOffset
          shape.setTranslateX(0)
          shape.setTranslateY(0)
      else ())




