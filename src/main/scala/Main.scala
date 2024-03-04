import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, VBox}
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font

object Main extends JFXApp3:

  def start() =

    val root = GridPane()

    val sideLBox= new VBox:
      background = Background.fill(LightPink)
      spacing = 4
      children = Array(Button("Table"), Button("Bed"), Button("Chair"))

    val label = new Label("Design your dream home!")
    label.font = Font(18)

    val sideRBox = new VBox:
      background = Background.fill(White)
      children = Array(label)
      //g.fillText("Design Your Dream Home", 500, 100)




/**      margin = Insets.apply(10,10,10,10)
      spacing = 4
      background = Background.fill(LightPink)
      children = Array(Button("Table"), Button("Bed"), Button("Chair"))
*/
/**
    val canvas = Canvas(, 300)
    val g = canvas.graphicsContext2D
    g.fill = White
    g.fillRect(0,0,300,400)
    g.fill = Black
    g.font = Font(18)
    g.fillText("Design Your Dream Home", 500, 100)
*/
    root.add(sideLBox, 0, 0, 1, 2)
    root.add(sideRBox, 1, 0)


    val column0 = new ColumnConstraints:
      percentWidth = 10
    val column1 = new ColumnConstraints:
      percentWidth = 90
    val row0 = new RowConstraints:
      percentHeight = 100

    root.columnConstraints = Array(column0,column1)
    root.rowConstraints = Array(row0)


    stage = new JFXApp3.PrimaryStage:
      title = "Dream Space"
      width = 900
      height = 600

    val scene = Scene(parent = root)
    stage.scene = scene


  end start

end Main

