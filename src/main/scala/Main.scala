import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, StackPane, VBox}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font

object Main extends JFXApp3:

  def start() =

    stage = new JFXApp3.PrimaryStage:
      title = "Dream Space"
      width = 900
      height = 600

    val root = GridPane()

    val menu = new Menu("File"):
      items = Array(MenuItem("New"), MenuItem("Save"))

    val top = new MenuBar:
      menus = Array(menu)

    val circleB = new ButtonType("Circle")
    val rectangleB = new ButtonType("Rectangle")
    val ellipseB = new ButtonType("Ellipse")
    val halfRB = new ButtonType("Half Round")

    val button1 = new Button("Table")
      button1.onAction = (event) =>

        val selectShape = new Alert(AlertType.Confirmation):
          initOwner(stage)
          headerText = "First select shape!"
          contentText = "Choose your option."
          buttonTypes = Seq(circleB, rectangleB, ellipseB, halfRB, ButtonType.Cancel)

        val result = selectShape.showAndWait()

        result match
          case Some(circleB) => println("you chose circle")
          case _ => println("either cancel or closed the whole thing")


    //This is the original GUI
    val sideLBox= new VBox:
      background = Background.fill(LightPink)
      spacing = 4
      children = Array( top, button1, Button("Bed"), Button("Chair"))

    val label = new Label("Design your dream home!")
    label.font = Font(18)

    val sideRBox = new VBox:
      background = Background.fill(White)
      children = Array(label)

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

    //Here comes the alert that I would like to show when the button "Table" is clicked.




    val scene = new Scene(parent = root)
    stage.scene = scene



  end start
