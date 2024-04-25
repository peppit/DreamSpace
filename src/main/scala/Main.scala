import scalafx.application.JFXApp3
import scalafx.scene.image
import scalafx.scene.layout.{Background, ColumnConstraints, GridPane, HBox, Pane, RowConstraints, VBox}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ButtonType, Label, Menu, MenuBar, MenuItem}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.*
import scalafx.scene.text.Font
import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter
import scalafx.scene.shape.Shape
import java.io.File
import javax.imageio.ImageIO
import scalafx.collections.ObservableBuffer
import scalafx.embed.swing.SwingFXUtils
import scalafx.scene.{Node, Scene}




object Main extends JFXApp3:

  var possibleFurniture: Option[String] = None  //Option for storing the info, which furniture did the user pick
  var possibleObject: Option[Shape] = None   //Option for storing the info, which shape did the user pick for the furniture
  var furnitures: Array[Furniture] = Array[Furniture]() //Array for storing the furnitures


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
            floorPlanBox.children.add(imageView)
          else
            println("No file selected")

  //Button for selecting new file
    val fileNew = MenuItem("New")
      fileNew.onAction = (event) => selectFile()

  // Here is the menu item for saving the file as a picture.
    val menuItemSave = new MenuItem("Save")

    menuItemSave.onAction = (event) =>
      val fileSaver = new FileChooser:
            title = "Save your file."
            extensionFilters.add(ExtensionFilter("PNG", Seq("*.png")))
      val selectedFile = fileSaver.showSaveDialog(stage)

   // Takes the snapshot from floorPlanBox and saves it to a File.
      if selectedFile != null then
        takeSnapshot(floorPlanBox, selectedFile)
      else
        println("The picture was not saved.")

  //method for taking the picture of the scene
    def takeSnapshot(node: Node, file: File) =
      val image = node.snapshot(null, null)
      val bufferedImage = SwingFXUtils.fromFXImage(image, null)
      assert(bufferedImage ne null)
      ImageIO.write(bufferedImage, "png", file)


  //Here is the menu button for filechooser
    val menu = new Menu("File"):
      items = Array(fileNew, menuItemSave)

  // Making the menu bar.
    val top = new MenuBar:
      menus = Array(menu)

  //Alert for selecting shapes
    def shapeSelect() =
  //Here are the buttons that the user can choose from:
      val circleB = new ButtonType("Circle")
      val rectangleB = new ButtonType("Rectangle")
      val ellipseB = new ButtonType("Ellipse")

  // The alert that pops up when it's time to select a shape
  // The inspiration for the alert has been taken from site:
  // https://www.scalafx.org/docs/dialogs_and_alerts/
      val select = new Alert(AlertType.Confirmation):
        initOwner(stage)
        title = "Selecting shape"
        headerText = "First select shape!"
        contentText = "Choose your option."
        buttonTypes = Seq(circleB, rectangleB, ellipseB, ButtonType.Cancel)

      val result = select.showAndWait()

  //Here we match the users choise so we get the next alert and the furniture on to the picture
      result match {
        case Some(button) => if button == circleB then
          SizeSelect().sizeSelectCircle match  //circle furniture out
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
            case _ => println("Something went wrong")

        else if button == rectangleB then
          SizeSelect().sizeSelectRectangle match  //rectangle furniture out
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
            case _ => println("Something went wrong")


        else if button == ellipseB then
          SizeSelect().sizeSelectEllipse match  //Ellipse furniture out
            case (f: Furniture) =>
              floorPlanBox.children += f //Here we add the shape to the picture
              furnitures = f +: furnitures
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
        SizeSelect().sizeSelectRectangle match
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
            case _ => println("Something went wrong.")

    val carpetButton = new Button("Carpet")
      carpetButton.onAction = (event) =>
        possibleFurniture = Option("Carpet")
        shapeSelect()

    val chairButton = new Button("Chair")
      chairButton.onAction = (event) =>
        possibleFurniture = Option("Chair")
        SizeSelect().sizeSelectCircle match
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
            case _ => println("Something went wrong.")

    val closetButton = new Button("Closet")
      closetButton.onAction = (event) =>
        possibleFurniture = Option("Closet")
        SizeSelect().sizeSelectRectangle match
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
            case _ => println("Something went wrong.")

    val lampButton = new Button("Lamp")
      lampButton.onAction = (event) =>
        possibleFurniture = Option("Lamp")
        SizeSelect().sizeSelectCircle match
            case (f: Furniture) =>
              floorPlanBox.children += f
              furnitures = f +: furnitures
            case _ => println("Something went wrong.")

    val tvButton = new Button("TV")
      tvButton.onAction = (event) =>
        possibleFurniture = Option("TV")
        SizeSelect().sizeSelectForTV match
            case (f: Furniture) =>
              furnitures = f +: furnitures
              floorPlanBox.children += f   //Here we add the shape to the picture
            case _ => println("Something went wrong.")

    val sofaButton = new Button("Sofa")
      sofaButton.onAction = (event) =>
        possibleFurniture = Option("Sofa")
        shapeSelect()

  //This is the original GUI
  //Here is the box on the left witch contais buttons for furnitures and other commands
    val sideLBox= new VBox:
      background = Background.fill(LightPink)
      spacing = 5
      children = Array( top, tableButton, bedButton,carpetButton, chairButton, closetButton, lampButton, tvButton, sofaButton)

    val label = new Label("Design your dream home!")  //The label that is written on top of the image space.
    label.font = Font(18)

    val topBox = new HBox:  //This is the top box
      spacing = 60
      background = Background.fill(White)
      children = Array(label)

  // here the program sets the boxes to their places
    root.add(sideLBox, 0, 0, 1, 2)
    root.add(topBox, 1, 0, 1, 1)
    root.add(floorPlanBox, 1, 1, 1, 1)

  // Implementung the perecentage of each box
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

  // Interface scene:
    val scene = new Scene(parent = root)
    stage.scene = scene



  end start
