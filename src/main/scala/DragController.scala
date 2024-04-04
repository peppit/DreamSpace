import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import scalafx.beans.property.DoubleProperty
import scalafx.Includes.jfxNode2sfx
import scala.language.postfixOps

class DragController(target: Node, isDraggable: Boolean = false) {

  private val anchorX = DoubleProperty(0)
  private val anchorY = DoubleProperty(0)
  private val mouseOffsetFromNodeZeroX = DoubleProperty(0)
  private val mouseOffsetFromNodeZeroY = DoubleProperty(0)
  private var cycleStatus = 0    //when cycleStatus is 0 it's INACTIVE, when 1 it's ACTIVE

  private val setAnchor: EventHandler[MouseEvent] = (event: MouseEvent) =>
    if (event.isPrimaryButtonDown) then
      cycleStatus = 1
      anchorX() = event.getSceneX
      anchorY() = event.getSceneY
      mouseOffsetFromNodeZeroX() = event.getX
      mouseOffsetFromNodeZeroY() = event.getY

    else if (event.isSecondaryButtonDown) then
      cycleStatus = 0
      target.translateX = 0
      target.translateY = 0
    else ()

  private val updatePositionOnDrag: EventHandler[MouseEvent] = (event: MouseEvent) =>
    if cycleStatus != 0 then
      target.setTranslateX(event.getSceneX - anchorX())
      target.setTranslateY(event.getSceneY - anchorY())

  private val commitPositionOnRelease: EventHandler[MouseEvent] = (event: MouseEvent) =>
    if cycleStatus != 0 then
      target.setLayoutX(event.getSceneX - mouseOffsetFromNodeZeroX())
      target.setLayoutY(event.getSceneY - mouseOffsetFromNodeZeroY())
      //clear changes from TranslateX and TranslateY
      target.setTranslateX(0)
      target.setTranslateY(0)


   private val isDraggablePropertypriv: BooleanProperty = new SimpleBooleanProperty(isDraggable)

   def createDraggableProperty(): Unit =
       isDraggableProperty.addListener((_, oldValue, newValue) => {
      if (newValue) {
        target.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor)
        target.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag)
        target.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease)
      } else {
        target.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor)
        target.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag)
        target.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease)
      }
    })

   def isDraggablereal: Boolean = isDraggablePropertypriv.get()

   def isDraggableProperty: BooleanProperty = isDraggablePropertypriv













}
