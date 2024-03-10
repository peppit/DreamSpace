import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, StackPane, VBox}
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, ColorPicker, Label, Menu, MenuBar, MenuItem, RadioButton, ToggleGroup}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
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

    val button1 = new Button("Table"):   //Here is the button what I would like to get to work
      onAction = (event) => ???

    //Tässä luon vain sitä alku näkymää
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

    //HOW DO I GET THIS BUTTON "TABLE" WORK IN A WAY THAT ShapeNColor VBox COMES IN MY SCREEN AS A SMALL WINDOW???!!!
    //I WOULD LIKE TO MAKE A WINDOW WHERE I COULD PICK A COLOR AND SHAPE

    val rootB = Button("Table")

    val scene2 = Scene( parent = rootB )
    stage.scene = scene2

    val shapeSelector = ToggleGroup()

    val circleSelector = new RadioButton("Circle"):
      toggleGroup = shapeSelector
      selected = true

    val rectangleSelector = new RadioButton("Rectangle"):
      toggleGroup = shapeSelector

    val shapeNColor = new VBox:
      prefHeight = 400
      prefWidth = 400
      spacing = 10
      children = Array(ColorPicker(Color.Pink), circleSelector, rectangleSelector)



    val scene = Scene(parent = root)
    stage.scene = scene



  end start
