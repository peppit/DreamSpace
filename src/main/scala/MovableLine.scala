import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{GridPane, Pane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.scene.shape.Line
import scalafx.Includes.jfxMouseEvent2sfx
import scalafx.Includes._

import scala.language.postfixOps

class MovableLine(pane: Pane) {

  var startX0 = 200
  var startY0 = 200
  var endX0 = 300
  var endY0 = 200

  var line = new Line:
    startX = startX0
    startY = startY0
    endX = endX0
    endY = endY0
    stroke = Color.Black
    strokeWidth = 3

  private def createDraggablePoint(x: Double, y: Double): Circle = {
    var point = new Circle
    point.setCenterX(x)
    point.setCenterY(y)
    point.setRadius(4)
    point.fill = Color.Red
    point
  }

  val startPoint = createDraggablePoint(startX0, startY0)
  val endPoint = createDraggablePoint(endX0, endY0)

  startPoint.onMouseDragged = (event: MouseEvent) =>{
    val newX = event.x
    val newY = event.y
    //if (newX >= startX0 && newX <= endX0) then
    startPoint.centerX = newX
    startPoint.centerY = newY
    updateline()
  }
  endPoint.onMouseDragged = (event: MouseEvent) => handlePointMouseDragged(event, line, false)

  //startPoint.onMouseDragged = (event: MouseEvent) => handlePointMouseDragged(event, line, true)
  //endPoint.onMouseDragged = (event: MouseEvent) => handlePointMouseDragged(event, line, false)

  pane.children.addAll(line, startPoint, endPoint)

  def updateline() =
    line.startX = startPoint.centerX.value
    line.endY = startPoint.centerX.value
    line.endX = endPoint.centerX.value
    line.endY = endPoint.centerX.value


  def handlePointMouseDragged(event: MouseEvent, line: Line, isStartPoint: Boolean): Unit = {
    val point = event.getSource.asInstanceOf[javafx.scene.shape.Circle]
    val offsetX = event.y
    val offsetY = event.x
    point.centerX = offsetX
    point.centerY = offsetY
    point.layoutX = event.sceneX - offsetX
    point.layoutY = event.sceneY - offsetY

    if (isStartPoint) {
      line.startX = offsetX
      line.startY = offsetY
      line.layoutX = event.sceneX - offsetX
      line.layoutY = event.sceneY - offsetX

    } else {
      line.endX = offsetX
      line.endY = offsetY
      line.layoutX = event.sceneX - offsetX
      line.layoutY = event.sceneY - offsetX
    }
  }


}
/*
 //  public void createDraggableLine(Pane root){
     //Create the line
     line = new Line(50, 50, 100, 100);
     line.setStroke(color.BLACK);
     line.setStrokewidth(3);

     //Create the start and end points
     startPoint = createDraggablePoiny(line.getStartX(), line.getStartY());
     endPoint = createDraggablePoiny(line.getEndX(), line.ge tEndY());

     //Add mouse event handlers for dragging
     startPoint.setMouseDragged(e -> handlePointMouseDragged(e, line, true));
     endPoint.setOnMouseDragged(e -> handlePointMouseDragged(e, line, false));

   private void handlePointMouseDragged(MouseEvent event, Line line, Boolean startPoint)
     Circle point = (Circle) event.getSource();
     double offsetX = event.getX();
     double offsetY = event.getY();
     point.setCenterX(offsetX);
     point.setCenterY(offsetY);
     point.setLayoutX(event.getSceneX() - offsetX);
     point.setLayoutX(event.getSceneY() - offsetY);

     if (startPoint) then
       line.setStartX(offsetX);
       line.setStartY(offsetY);
     else
       line.setEndX(offsetX)
       line.setEnd(offsetY);


   private Circle createDraggablePoint(double x, double y)
     Circle point = new Circle(x, y, 5, Color.Black);
     point.setStroke(Color.Black);
     point.setStrokeWidth(1);
     point.setCenterX(x);
     point.setCenterY(y);
     return point;

   public void removeLineFrom(Pane root)
     root.getChildren().remove(line);
     root.getChildren().remove(endPoint);
     root.getChildren().remove(endPoint);




*/





