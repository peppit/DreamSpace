import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, StackPane, VBox}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.image.{Image, ImageView}
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
      width = 1000
      height = 700

    val root = GridPane()

// Possible imageview
    val imageView = new ImageView:
      fitHeight = 580
      fitWidth = 580
      preserveRatio = true


// method for selecting the wanted file
    def selectFile() =
          val filechooser = new FileChooser:
            title = "Select the picture of the floorplan"
            initialDirectory = File("./src/main/DreamSpace.testikansio")
            extensionFilters.add(ExtensionFilter("PNG and JPG", Seq("*.png", "*.jpg")))

          val selectedFile = filechooser.showOpenDialog(stage)

          if selectedFile != null then
            imageView.image = new Image(selectedFile.toURI.toString)
          else
            println("No file selected")


    val fileNew = MenuItem("New")
      fileNew.onAction = (event) => selectFile()


    //Here is the button for filechooser don't know how to combine the filechooserbutton to this
    val menu = new Menu("File"):
      items = Array(fileNew, MenuItem("Save"))

    val top = new MenuBar:
      menus = Array(menu)

    //Alert for selecting shapes
    def shapeSelect() =

      val circleB = new ButtonType("Circle")
      val rectangleB = new ButtonType("Rectangle")
      val ellipseB = new ButtonType("Ellipse")
      val halfRB = new ButtonType("Half Round")

      val select = new Alert(AlertType.Confirmation):
        initOwner(stage)
        headerText = "First select shape!"
        contentText = "Choose your option."
        buttonTypes = Seq(circleB, rectangleB, ellipseB, halfRB, ButtonType.Cancel)

      val result = select.showAndWait()

      result match
        case Some(circleB) => println("you chose circle")
        case _ => println("either cancel or closed the whole thing")

    // Buttons for furnitures:
    val tableButton = new Button("Table")
      tableButton.onAction = (event) => shapeSelect()
    val bedButton = new Button("Bed")
    val carpetButton = new Button("Carpet")
      carpetButton.onAction = (event) => shapeSelect()
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

    val topBox = new VBox:
      background = Background.fill(White)
      children = Array(label)

    var floorPlanBox = new VBox:
      background = Background.fill(White)
      children = Array(imageView)


    root.add(sideLBox, 0, 0, 1, 2)
    root.add(topBox, 1, 0, 1, 1)
    root.add(floorPlanBox, 1, 1, 1, 1)


    val column0 = new ColumnConstraints:
      percentWidth = 6
    val column1 = new ColumnConstraints:
      percentWidth = 94
    val row0 = new RowConstraints:
      percentHeight = 6
    val row1 = new RowConstraints:
      percentHeight = 94

    root.columnConstraints = Array(column0,column1)
    root.rowConstraints = Array(row0, row1)

    val scene = new Scene(parent = root)
    stage.scene = scene



  end start
