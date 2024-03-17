import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, StackPane, VBox}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter
import java.io.File

object Main extends JFXApp3:

  def start() =

    //the stage for the interface
    stage = new JFXApp3.PrimaryStage:
      title = "Dream Space"
      width = 900
      height = 600

    val root = GridPane()

    val fileNew = MenuItem("New")
      fileNew.onAction = (event) =>

        val filechooser = new FileChooser:
         title = "Select the picture of the floorplan"
      //Opens the files
         initialDirectory = File("./src/main/DreamSpace.testikansio")  //MIKS EI LÖYDÄ?
         extensionFilters.add(ExtensionFilter("PNG and JPG", Seq("*.png", "*.jpg"))) //WHAAAT???

        val file = filechooser.showOpenDialog(stage)


    //Here is the button for filechooser don't know how to combine the filechooserbutton to this
    val menu = new Menu("File"):
      items = Array(fileNew, MenuItem("Save"))

    val top = new MenuBar:
      menus = Array(menu)

    //NÄÄ 2 KOMMATAAN NII OHJELMA TOIMII


    // Buttons for shapes
    /*val circleB = new ButtonType("Circle")
    val rectangleB = new ButtonType("Rectangle")
    val ellipseB = new ButtonType("Ellipse")
    val halfRB = new ButtonType("Half Round")*/


    // Buttons for furnitures:
    val tableButton = new Button("Table")
      tableButton.onAction = (event) =>

        val circleB = new ButtonType("Circle")
        val rectangleB = new ButtonType("Rectangle")
        val ellipseB = new ButtonType("Ellipse")
        val halfRB = new ButtonType("Half Round")

        val selectShape = new Alert(AlertType.Confirmation):
          initOwner(stage)
          headerText = "First select shape!"
          contentText = "Choose your option."
          buttonTypes = Seq(circleB, rectangleB, ellipseB, halfRB, ButtonType.Cancel)

        val result = selectShape.showAndWait()

        result match
          case Some(rectangleB) => println("lslslslsl")
          case Some(circleB) => println("you chose circle")
          case _ => println("either cancel or closed the whole thing")   //BUGI!!!!!!


    val bedButton = new Button("Bed")
    val carpetButton = new Button("Carpet")
    val chairButton = new Button("Chair")
    val closetButton = new Button("Closet")
    val lampButton = new Button("Lamp")
    val tvButton = new Button("TV")
    val sofaButton = new Button("Sofa")




    //This is the original GUI
    val sideLBox= new VBox:
      background = Background.fill(LightPink)
      spacing = 5
      children = Array( top, tableButton, bedButton,carpetButton, chairButton, closetButton, lampButton, tvButton, sofaButton)

    val label = new Label("Design your dream home!")
    label.font = Font(18)

    val sideRBox = new VBox:
      background = Background.fill(White)
      children = Array(label)

    root.add(sideLBox, 0, 0, 1, 2)
    root.add(sideRBox, 1, 0)

    val column0 = new ColumnConstraints:
      percentWidth = 6
    val column1 = new ColumnConstraints:
      percentWidth = 94
    val row0 = new RowConstraints:
      percentHeight = 100

    root.columnConstraints = Array(column0,column1)
    root.rowConstraints = Array(row0)


    val scene = new Scene(parent = root)
    stage.scene = scene



  end start
