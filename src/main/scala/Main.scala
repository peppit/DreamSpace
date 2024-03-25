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
import scalafx.scene.shape.Rectangle
import scalafx.Includes.string2sfxColor

import java.io.File

object Main extends JFXApp3:

  var possibleObject: Option[Shape] = None  // not sure if I'm gonna use this one. You can ignore this.

  def start() =

    //the stage for the interface
    stage = new JFXApp3.PrimaryStage:
      title = "Dream Space"
      width = 1000
      height = 700

    val root = GridPane()   //Do I keep the GridPane or switch to the StackPane?

// Possible imageview
    var imageView = new ImageView:
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
          println("you chose circle")
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


     // Alert for selecting size and color!!!! The problem is in this method
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
      // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
          possibleObject = Some(RectangleObject(u,p, c))
          // HOW DO I GET THIS RECTANGLEOBJECT ON THE SCEEN? I WOULD LIKE TO HAVE IT ON TOP OF THE PICTURE OF FLOORPLAN.

          val r = new Rectangle()
          r.setX(300)
          r.setY(200)
          r.setWidth(u)
          r.setHeight(p)
          r.fill = c

          println("Sait toimiin")

     //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        case None => println("Dialog returned: None")
        case _ => println("something else happened")


    // Buttons for furnitures:
    val tableButton = new Button("Table")
      tableButton.onAction = (event) => shapeSelect()
    val bedButton = new Button("Bed")
      bedButton.onAction = (event) => sizeSelectRectangle()
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
