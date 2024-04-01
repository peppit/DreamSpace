import scalafx.application.{JFXApp3, Platform}
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.{Scene, image}
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, StackPane, VBox}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{Alert, Button, ButtonType, Dialog, Label, Menu, MenuBar, MenuItem, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.Includes.jfxDialogPane2sfx
import scalafx.Includes.jfxNode2sfx
import scalafx.scene.input.KeyCode.A
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.Includes.string2sfxColor
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.DragEvent

import java.io.File

object Main extends JFXApp3:

  var possibleObject: Option[Shape] = None
  var possibleFurniture: Option[String] = None
  var furnitures: Array[Furniture] = Array[Furniture]()


  def start() =

    //the stage for the interface
    stage = new JFXApp3.PrimaryStage:
      title = "Dream Space"
      width = 1000
      height = 700

    val root = GridPane()


// Possible imageview
    var imageView = new ImageView:
      fitHeight = 580
      fitWidth = 580
      preserveRatio = true
// This is were the image of an floorplan is imported and where the shapes are drawn on
    var floorPlanBox = new StackPane():    //Should we put the on mouse move here??
      var shapes = Array[Shape]()          // I was thinking of doing an array for shapes?
                                           // But also I should do something with the furniture class
      
      
   //How do you even use "onMouseMove" ????
   // I'm so stuck I don't know what to do. How do I get my shapes moving from dragging the mouse??
    


// method for selecting the wanted file
    def selectFile() =
          val filechooser = new FileChooser:
            title = "Select the picture of the floorplan"
            initialDirectory = File("./src/main/DreamSpace.testikansio")
            extensionFilters.add(ExtensionFilter("PNG and JPG", Seq("*.png", "*.jpg")))

          val selectedFile = filechooser.showOpenDialog(stage)

          if selectedFile != null then
            imageView.image = new Image(selectedFile.toURI.toString)
            floorPlanBox.children.add(imageView)
          else
            println("No file selected")


    val fileNew = MenuItem("New")
      fileNew.onAction = (event) => selectFile()

    //Here is the button for filechooser
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
        title = "Selecting shape"
        headerText = "First select shape!"
        contentText = "Choose your option."
        buttonTypes = Seq(circleB, rectangleB, ellipseB, halfRB, ButtonType.Cancel)

      val result = select.showAndWait()

      result match {
        case Some(button) => if button == circleB then
          sizeSelectCircle()
        else if button == rectangleB then
          sizeSelectRectangle()
        else if button == ellipseB then
          println("you chose ellipse")
        else if button == halfRB then
          println("you chose half round")
        else
          println("you chose cancel")
        case _ => println("No button selected")
        }


     // Alert for selecting size and color for rectangle shaped objects
    def sizeSelectRectangle() =

      val dialog = new Dialog[RectangleObject]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements and wanted colour."

      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

      val sideL1 = new TextField():
        promptText = "lenght in cm"

      val sideL2 = new TextField():
        promptText = "lenght in cm"

      val colorPick = new TextField():
        promptText = "Write the first letter with caps"

      val grid = new GridPane():
         hgap = 10
         vgap = 10
         padding = Insets(20, 100, 10, 10)

         add(new Label("Side 1 lenght:"), 0, 0)
         add(sideL1, 1,0)
         add(new Label("Side 2 lenght:"), 0, 1)
         add(sideL2,1,1)
         add(new Label("Color:"), 0, 2)
         add(colorPick, 1, 2)

      val confirmButton = dialog.dialogPane().lookupButton(confirmButtonType)
      confirmButton.disable = true

      sideL1.text.onChange { (_, _, newValue) =>
        confirmButton.disable = newValue.trim().isEmpty
      }
      sideL2.text.onChange { (_, _, newValue) =>
        confirmButton.disable = newValue.trim().isEmpty
      }

      dialog.dialogPane().content = grid

      Platform.runLater(sideL1.requestFocus())

      dialog.resultConverter = dialogButton =>
        if (dialogButton == confirmButtonType) then
          RectangleObject(sideL1.text().toDouble, sideL2.text().toDouble, colorPick.text())
        else
          null

      val result = dialog.showAndWait()

      result match
        case Some(RectangleObject(u,p, c)) =>
          possibleObject = Some(RectangleObject(u,p, c))
          val r = new Rectangle()
          r.setX(400)
          r.setY(50)
          r.setWidth(u)
          r.setHeight(p)
          r.fill = c

          floorPlanBox.children += r   //Here we add the shape to the picture

          println("Sait toimiin")

        case None => println("Dialog returned: None")
        case _ => println("something else happened")

    // Alert for selecting size and color for circle shaped furniture:
    def sizeSelectCircle() =
      val dialog = new Dialog[RoundObject]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements and wanted colour."

      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

      val diameter = new TextField():
        promptText = "diameter in cm"

      val colorPick = new TextField():
        promptText = "Write the first letter with caps"

      val grid = new GridPane():
         hgap = 10
         vgap = 10
         padding = Insets(20, 100, 10, 10)

         add(new Label("Diameter in cm:"), 0, 0)
         add(diameter, 1,0)
         add(new Label("Color:"), 0, 1)
         add(colorPick, 1, 1)

      val confirmButton = dialog.dialogPane().lookupButton(confirmButtonType)
      confirmButton.disable = true

      diameter.text.onChange { (_, _, newValue) =>
        confirmButton.disable = newValue.trim().isEmpty
      }
      colorPick.text.onChange { (_, _, newValue) =>
        confirmButton.disable = newValue.trim().isEmpty
      }

      dialog.dialogPane().content = grid

      Platform.runLater(diameter.requestFocus())

      dialog.resultConverter = dialogButton =>
        if (dialogButton == confirmButtonType) then
          RoundObject(diameter.text().toDouble, colorPick.text())
        else
          null

      val result = dialog.showAndWait()

      result match
        case Some(RoundObject(d, c)) =>
          possibleObject = Some(RoundObject(d, c))

          val cir = new Circle()
          cir.setCenterX(400)
          cir.setCenterY(50)
          cir.setRadius(d/2)
          cir.fill = c

          //furnitures += Furniture(possibleFurniture.get, cir, c)
          floorPlanBox.children += cir

          println("Sait toimiin")

        case None => println("Dialog returned: None")
        case _ => println("something else happened")

    // Buttons for furnitures:
    val tableButton = new Button("Table")
      tableButton.onAction = (event) =>
        shapeSelect()
    val bedButton = new Button("Bed")
      bedButton.onAction = (event) =>
        possibleFurniture = Option("Bed")
        sizeSelectRectangle()
    val carpetButton = new Button("Carpet")
      carpetButton.onAction = (event) => shapeSelect()
    val chairButton = new Button("Chair")
      chairButton.onAction = (event) => sizeSelectCircle()
    val closetButton = new Button("Closet")
      closetButton.onAction = (event) => sizeSelectRectangle()
    val lampButton = new Button("Lamp")
      lampButton.onAction = (event) => sizeSelectCircle()
    val tvButton = new Button("TV")
    val sofaButton = new Button("Sofa")
      sofaButton.onAction = (event) => shapeSelect()


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
