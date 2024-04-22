import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.{Scene, image}
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, StackPane, VBox}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{Alert, Button, ButtonType, ColorPicker, Dialog, Label, Menu, MenuBar, MenuItem, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.Includes.jfxDialogPane2sfx
import scalafx.Includes.jfxNode2sfx
import scalafx.scene.input.KeyCode.A
import scalafx.scene.shape.{Circle, Ellipse, Rectangle, Shape}
import scalafx.Includes.string2sfxColor
import scalafx.scene.input.{DragEvent, MouseDragEvent}
import scalafx.scene.layout.StackPane
import scalafx.Includes.jfxPaint2sfx
import scalafx.Includes.jfxColor2sfx
import io.circe.generic.auto.*
import cats.syntax.either.*
import io.circe.*

import java.io.File
import scala.collection.mutable



object Main extends JFXApp3:

  var possibleFurniture: Option[String] = None  //Option for storing the info, which furniture did the user pick
  var possibleObject: Option[Shape] = None   //Option for storing the info, which shape did the user pick for the furniture
  var furnitures: Array[Furniture] = Array[Furniture]() //Array for storing the furnitures
  val movableWalls = Array[MovableLine]() //Array for storing the walls
  val furnituresForData = mutable.Buffer[FurnitureData]()
  var pictureForData: Option[ImageView] = None

  case class Data(val picture: ImageView, val furnitures: mutable.Buffer[FurnitureData])
  case class FurnitureData(val shape: Shape, val color: Color, val xPos: Double, val yPos: Double)

  def saveData() =
    for f <- furnitures do
      furnituresForData += FurnitureData(f.shapeOut,f.colorOut, f.x, f.y)
    Data(pictureForData.get, furnituresForData)



  def start() =

    //the stage for the interface
    stage = new JFXApp3.PrimaryStage:
      title = "Dream Space"
      width = 1000
      height = 700

    val root = GridPane()


// Possible imageview
    var imageView = new ImageView:
      fitHeight = 650
      fitWidth = 650
      preserveRatio = true
      layoutX = 150
    pictureForData = Some(imageView)   //save this imageView for data.

// This is were the image of an floorplan is imported and where the shapes are drawn on
    var floorPlanBox = new Pane()


// method for selecting the wanted file
    def selectFile() =
          val filechooser = new FileChooser:
            title = "Select the picture of the floorplan"
            initialDirectory = File("./src/main/DreamSpace.testikansio")
            extensionFilters.add(ExtensionFilter("PNG and JPG", Seq("*.png", "*.jpg"))) //only JPG and PNG -pictures allowed

          val selectedFile = filechooser.showOpenDialog(stage)

          if selectedFile != null then
            imageView.image = new Image(selectedFile.toURI.toString)
            pictureForData = Some(imageView)
            floorPlanBox.children.add(imageView)
          else
            println("No file selected")

    val fileNew = MenuItem("New")                 //Button for selecting new file
      fileNew.onAction = (event) => selectFile()




    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //HERE IS THE PROBLEM
    val menuItemSave = new MenuItem("Save")

    menuItemSave.onAction = (event) =>
      val fileChooser = new FileChooser:
            title = "Save your file."
            extensionFilters.add(ExtensionFilter("PNG", Seq("*.png")))
      fileChooser.showSaveDialog(stage)
    /// WHY DOESN'T it save the stage??




    //Here is the menu button for filechooser
    val menu = new Menu("File"):
      items = Array(fileNew, menuItemSave)

    val top = new MenuBar:   //making the menu bar.
      menus = Array(menu)

    //Alert for selecting shapes
    def shapeSelect() =
      //Here are the buttons that the user can choose from:
      val circleB = new ButtonType("Circle")
      val rectangleB = new ButtonType("Rectangle")
      val ellipseB = new ButtonType("Ellipse")
      //The alert that pops up when it's time to select a shape
      val select = new Alert(AlertType.Confirmation):
        initOwner(stage)
        title = "Selecting shape"
        headerText = "First select shape!"
        contentText = "Choose your option."
        buttonTypes = Seq(circleB, rectangleB, ellipseB, ButtonType.Cancel)

      val result = select.showAndWait()

      result match {
        case Some(button) => if button == circleB then
          sizeSelect().sizeSelectCircle match
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
              println(f.nameOut)
              println("Sait toimiin")
            case _ => println("Something went wrong")

        else if button == rectangleB then
          sizeSelect().sizeSelectRectangle match
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
              println(f.nameOut)
              println("Sait toimiin")
            case _ => println("Something went wrong")


        else if button == ellipseB then
          sizeSelect().sizeSelectEllipse match
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
              println(f.nameOut)
              println(f.shapeOut.toString)
            case _ => println("Something went wrong")

        else
          println("you chose cancel")
        case _ => println("No button selected")
        }


    // Buttons for furnitures:
    val tableButton = new Button("Table")
      tableButton.onAction = (event) =>
        possibleFurniture = Option("Table")
        shapeSelect()

    val bedButton = new Button("Bed")
      bedButton.onAction = (event) =>
        possibleFurniture = Option("Bed")
        sizeSelect().sizeSelectRectangle match
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
              println(f.nameOut)
              println("Sait toimiin")
            case _ => println("something else happened")

    val carpetButton = new Button("Carpet")
      carpetButton.onAction = (event) =>
        possibleFurniture = Option("Carpet")
        shapeSelect()

    val chairButton = new Button("Chair")
      chairButton.onAction = (event) =>
        possibleFurniture = Option("Chair")
        sizeSelect().sizeSelectCircle match
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
              println(f.nameOut)
              println("Sait toimiin")
            case _ => println("Something went wrong")

    val closetButton = new Button("Closet")
      closetButton.onAction = (event) =>
        possibleFurniture = Option("Closet")
        sizeSelect().sizeSelectRectangle match
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
              println(f.nameOut)
              println("Sait toimiin")
            case _ => println("something else happened")

    val lampButton = new Button("Lamp")
      lampButton.onAction = (event) =>
        possibleFurniture = Option("Lamp")
        sizeSelect().sizeSelectCircle match
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
              println(f.nameOut)
              println("Sait toimiin")
            case _ => println("Something went wrong")

    val tvButton = new Button("TV")

    val sofaButton = new Button("Sofa")
      sofaButton.onAction = (event) =>
        possibleFurniture = Option("Sofa")
        shapeSelect()
    val addWall = new Button("Add wall")
      addWall.layoutX = 400
      addWall.onAction = (event) => MovableLine(floorPlanBox)


    //This is the original GUI
    val sideLBox= new VBox:
      background = Background.fill(LightPink)
      spacing = 5
      children = Array( top, tableButton, bedButton,carpetButton, chairButton, closetButton, lampButton, tvButton, sofaButton)

    val label = new Label("Design your dream home!")
    label.font = Font(18)

    val topBox = new HBox:
      spacing = 60
      background = Background.fill(White)
      children = Array(label, addWall)

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
