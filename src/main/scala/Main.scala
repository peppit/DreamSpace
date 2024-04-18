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

import java.io.File



object Main extends JFXApp3:

  var possibleObject: Option[Shape] = None
  var possibleFurniture: Option[String] = None
  var furnitures: Array[Furniture] = Array[Furniture]()
  val movableWalls = Array[MovableLine]()


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


// This is were the image of an floorplan is imported and where the shapes are drawn on
    var floorPlanBox = new Pane():
      var furnitures = Array[Furniture]()


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




    //Here is the button for filechooser
    val menu = new Menu("File"):
      items = Array(fileNew, menuItemSave)

    val top = new MenuBar:
      menus = Array(menu)

    //Alert for selecting shapes
    def shapeSelect() =

      val circleB = new ButtonType("Circle")
      val rectangleB = new ButtonType("Rectangle")
      val ellipseB = new ButtonType("Ellipse")

      val select = new Alert(AlertType.Confirmation):
        initOwner(stage)
        title = "Selecting shape"
        headerText = "First select shape!"
        contentText = "Choose your option."
        buttonTypes = Seq(circleB, rectangleB, ellipseB, ButtonType.Cancel)

      val result = select.showAndWait()

      result match {
        case Some(button) => if button == circleB then
          sizeSelectCircle()
        else if button == rectangleB then
          sizeSelectRectangle()
        else if button == ellipseB then
          sizeSelectEllipse()
        else
          println("you chose cancel")
        case _ => println("No button selected")
        }


     // Alert for selecting size and color for rectangle shaped objects
    def sizeSelectRectangle() =

      var possibleRect: Option[Rectangle] = None

      val dialog = new Dialog[Rectangle]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements and wanted colour."

      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

      val sideL1 = new TextField():
        promptText = "lenght in cm"

      val sideL2 = new TextField():
        promptText = "lenght in cm"

      val colorPick = new ColorPicker()

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
          possibleRect = Option(Rectangle(sideL1.text().toDouble, sideL2.text().toDouble, colorPick.getValue))
          Rectangle(sideL1.text().toDouble, sideL2.text().toDouble, colorPick.getValue)
        else
          null

      val result = dialog.showAndWait()

      result match
        case Some(re: Rectangle) =>
          val r = new Rectangle()
          r.setX(400)
          r.setY(50)
          r.setWidth(possibleRect.get.width.toDouble)
          r.setHeight(possibleRect.get.height.toDouble)
          r.fill = possibleRect.get.fill.get()

          val RectangleFurniture = Furniture(possibleFurniture.get, r)
          RectangleFurniture.x = 400
          RectangleFurniture.y = 50
          val drag = new DragController()
          drag.createHandlers(RectangleFurniture)
          
          furnitures = RectangleFurniture +: furnitures
          floorPlanBox.children += RectangleFurniture   //Here we add the shape to the picture
          println(RectangleFurniture.nameOut)
          println("Sait toimiin")

        case None => println("Dialog returned: None")
        case _ => println("something else happened")

    // Alert for selecting size and color for circle shaped furniture:
    def sizeSelectCircle() =
      var possibleCircle: Option[Circle]= None

      val dialog = new Dialog[Circle]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements and wanted colour."

      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

      val diameter = new TextField():
        promptText = "diameter in cm"

      val colorPick = new ColorPicker()

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

      dialog.dialogPane().content = grid

      Platform.runLater(diameter.requestFocus())

      dialog.resultConverter = dialogButton =>
        if (dialogButton == confirmButtonType) then
          possibleCircle = Option(Circle(diameter.text().toDouble, colorPick.getValue))
          Circle(diameter.text().toDouble, colorPick.getValue)
        else
          null

      val result = dialog.showAndWait()

      result match
        case Some(c: Circle) =>
          val cir = new Circle()
          cir.setCenterX(400)
          cir.setCenterY(50)
          cir.setRadius(possibleCircle.get.radius.toDouble /2)
          cir.fill = possibleCircle.get.fill.get()
          val circleFurniture = Furniture(possibleFurniture.get, cir)
          circleFurniture.x = 400
          circleFurniture.y = 50
          val drag = new DragController()
          drag.createHandlers(circleFurniture)
          floorPlanBox.children += circleFurniture
          furnitures = circleFurniture +: furnitures
          println(circleFurniture.nameOut)
          println("Sait toimiin")

        case None => println("Dialog returned: None")
        case _ => println("something else happened")

        // Alert for selecting size and color for circle shaped furniture:
    def sizeSelectEllipse() =
      var possibleEllipse: Option[Ellipse]= None
      var possibleColor: Option[Color] = None

      val dialog = new Dialog[Ellipse]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements and wanted colour."

      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

      val diameterX = new TextField():
        promptText = "diameter in cm"

      val diameterY = new TextField():
        promptText = "diameter in cm"

      val colorPick = new ColorPicker()

      val grid = new GridPane():
         hgap = 10
         vgap = 10
         padding = Insets(20, 100, 10, 10)
         add(new Label("Diameter x in cm:"), 0, 0)
         add(diameterX, 1,0)
         add(new Label("Diameter y in cm:"), 0, 1)
         add(diameterY, 1,1)
         add(new Label("Color:"), 0, 2)
         add(colorPick, 1, 2)

      val confirmButton = dialog.dialogPane().lookupButton(confirmButtonType)
      confirmButton.disable = true

      diameterX.text.onChange { (_, _, newValue) =>
        confirmButton.disable = newValue.trim().isEmpty
      }
      diameterY.text.onChange { (_, _, newValue) =>
        confirmButton.disable = newValue.trim().isEmpty
      }

      dialog.dialogPane().content = grid

      Platform.runLater(diameterX.requestFocus())

      dialog.resultConverter = dialogButton =>
        if (dialogButton == confirmButtonType) then
          possibleEllipse = Option(Ellipse(diameterX.text().toDouble/2, diameterY.text().toDouble/2))
          possibleColor = Option(colorPick.getValue)
          Ellipse(diameterX.text().toDouble/2, diameterY.text().toDouble/2)
        else
          null

      val result = dialog.showAndWait()

      result match
        case Some(c: Ellipse) =>
          val el = new Ellipse()
          el.setCenterX(400)
          el.setCenterY(50)
          el.setRadiusX(possibleEllipse.get.radiusX.toDouble)
          el.setRadiusY(possibleEllipse.get.radiusY.toDouble)
          el.fill = possibleColor.get
          val ellipseFurniture = Furniture(possibleFurniture.get, el)
          ellipseFurniture.x = 400
          ellipseFurniture.y = 50
          val drag = new DragController()
          drag.createHandlers(ellipseFurniture)
          floorPlanBox.children += ellipseFurniture
          furnitures = ellipseFurniture +: furnitures
          println(ellipseFurniture.nameOut)
        case None => println("Dialog returned: None")
        case _ => println("something else happened")


    // Buttons for furnitures:
    val tableButton = new Button("Table")
      tableButton.onAction = (event) =>
        possibleFurniture = Option("Table")
        shapeSelect()
    val bedButton = new Button("Bed")
      bedButton.onAction = (event) =>
        possibleFurniture = Option("Bed")
        sizeSelectRectangle()
    val carpetButton = new Button("Carpet")
      carpetButton.onAction = (event) =>
        possibleFurniture = Option("Carpet")
        shapeSelect()
    val chairButton = new Button("Chair")
      chairButton.onAction = (event) =>
        possibleFurniture = Option("Chair")
        sizeSelectCircle()
    val closetButton = new Button("Closet")
      possibleFurniture = Option("Closet")
      closetButton.onAction = (event) =>
        possibleFurniture = Option("Closet")
        sizeSelectRectangle()
    val lampButton = new Button("Lamp")
      lampButton.onAction = (event) =>
        possibleFurniture = Option("Lamp")
        sizeSelectCircle()
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
