import Main.{possibleFurniture, stage}
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.scene.control.ButtonBar.ButtonData
import scalafx.scene.control.{ButtonType, ColorPicker, Dialog, Label, TextField}
import scalafx.scene.layout.GridPane
import scalafx.scene.shape.{Circle, Ellipse, Rectangle}
import scalafx.Includes.jfxDialogPane2sfx
import scalafx.Includes.jfxNode2sfx
import scalafx.Includes.jfxColor2sfx
import scalafx.Includes.jfxPaint2sfx
import scalafx.scene.paint.Color

// This is the class witch contains the different shape option's alerts.
// After choosing one of the shapes, there will be an Alert which asks for measurements.
// Depending on the shape that has been chosen, the Alert may vary as well as the drawn shape.

class sizeSelect:


  // If user has chosen Rectangle:
  def sizeSelectRectangle =

  //Measured shape and chosen color are being stored in here for later, when the program constructs the wanted Furniture
      var possibleRect: Option[Rectangle] = None
      var possibleColor: Option[Color] = None

    //The dialog which asks the user to implement the color and shape of furniture
      val dialog = new Dialog[Rectangle]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements and wanted colour."

    //Buttons for confirmation and cancel
      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

    //Text fields that asks for measurements
    // The measurements are given in cm.
      val sideL1 = new TextField():
        promptText = "lenght in cm"

      val sideL2 = new TextField():
        promptText = "lenght in cm"

    //The color picker
      val colorPick = new ColorPicker()

    //Here is given the coordinates for textfields and colorpicker.
      val grid = new GridPane():
         hgap = 10
         vgap = 10
         padding = Insets(20, 100, 10, 10)

         add(new Label("Width x:"), 0, 0)
         add(sideL1, 1,0)
         add(new Label("Width y:"), 0, 1)
         add(sideL2,1,1)
         add(new Label("Color:"), 0, 2)
         add(colorPick, 1, 2)

    // Here for the confirmation button is set that it won't work unless there has been given right amount of information
    // and that the information is being given in right form
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

    // From the given measurements and color the program forms a rectangle.
    // The rectangle is scaled to the picture by factor 0.636.
    // By doing this the furnitures are in their realistic measurements on the picture.
      dialog.resultConverter = dialogButton =>
        if (dialogButton == confirmButtonType) then
          possibleRect = Option(Rectangle(sideL1.text().toDouble * 0.636, sideL2.text().toDouble * 0.636, colorPick.getValue))
          possibleColor = Option(colorPick.getValue)
          Rectangle(sideL1.text().toDouble * 0.636, sideL2.text().toDouble * 0.636, colorPick.getValue)
        else
          null

      val result = dialog.showAndWait()

    // Here the formed Rectangle is implemented on the floorplan.
      result match
        case Some(re: Rectangle) =>
          val r = new Rectangle()
          r.setX(400)
          r.setY(50)
          r.setWidth(possibleRect.get.width.toDouble)
          r.setHeight(possibleRect.get.height.toDouble)
          r.fill = possibleRect.get.fill.get()

          val RectangleFurniture = Furniture(possibleFurniture.get, r, possibleColor.get)
          RectangleFurniture.x = 400
          RectangleFurniture.y = 50
          val drag = new Controller()
          drag.createHandlers(RectangleFurniture)
          RectangleFurniture

        case None => println("Dialog returned: None")
        case _ => println("something else happened")




  // Alert for selecting size and color for circle shaped furniture:
  def sizeSelectCircle =

  //Measured shape and chosen color are being stored in here for later, when the program constructs the wanted Furniture
    var possibleColor: Option[Color] = None
    var possibleCircle: Option[Circle]= None

  //The dialog which asks the user to implement the color and shape of furniture
    val dialog = new Dialog[Circle]():
      initOwner(stage)
      title = "Measurements"
      headerText = "Please enter the measurements and wanted colour."

  //Buttons for confirmation and cancel
    val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
    dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

  // Text fields that asks for measurements.
  // The measurements are given in cm.
    val diameter = new TextField():
      promptText = "diameter in cm"

  //The color picker
    val colorPick = new ColorPicker()

  //Here is given the coordinates for textfield and colorpicker.
    val grid = new GridPane():
       hgap = 10
       vgap = 10
       padding = Insets(20, 100, 10, 10)

       add(new Label("Diameter in cm:"), 0, 0)
       add(diameter, 1,0)
       add(new Label("Color:"), 0, 1)
       add(colorPick, 1, 1)

  // Here for the confirmation button is set that it won't work unless there has been given right amount of information
  // and that the information is being given in right form.
    val confirmButton = dialog.dialogPane().lookupButton(confirmButtonType)
    confirmButton.disable = true

    diameter.text.onChange { (_, _, newValue) =>
      confirmButton.disable = newValue.trim().isEmpty
    }

    dialog.dialogPane().content = grid

    Platform.runLater(diameter.requestFocus())

  // From the given measurements and color, the program forms a circle.
  // The circle is scaled to the picture by factor 0.636.
  // By doing this the furnitures are in their realistic measurements on the picture.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == confirmButtonType) then
        possibleCircle = Option(Circle(diameter.text().toDouble * 0.636, colorPick.getValue))
        possibleColor = Option(colorPick.getValue)
        Circle(diameter.text().toDouble * 0.636, colorPick.getValue)
      else
        null

    val result = dialog.showAndWait()

  // Here the formed Circle is implemented on the floorplan.
    result match
      case Some(c: Circle) =>
        val cir = new Circle()
        cir.setCenterX(400)
        cir.setCenterY(50)
        cir.setRadius(possibleCircle.get.radius.toDouble /2)
        cir.fill = possibleCircle.get.fill.get()
        val circleFurniture = Furniture(possibleFurniture.get, cir, possibleColor.get)
        circleFurniture.x = 400
        circleFurniture.y = 50
        val drag = new Controller()
        drag.createHandlers(circleFurniture)
        circleFurniture

      case None => println("Dialog returned: None")
      case _ => println("something else happened")


  // Alert for selecting size and color for ellipse shaped furniture:
  def sizeSelectEllipse =

  //Measured shape and chosen color are being stored in here for later, when the program constructs the wanted Furniture
    var possibleEllipse: Option[Ellipse]= None
    var possibleColor: Option[Color] = None

  //The dialog which asks the user to implement the color and shape of furniture
    val dialog = new Dialog[Ellipse]():
      initOwner(stage)
      title = "Measurements"
      headerText = "Please enter the measurements and wanted colour."

  //Buttons for confirmation and cancel
    val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
    dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

  // Text fields that asks for measurements
  // The measurements are given in cm.
    val diameterX = new TextField():
      promptText = "diameter in cm"
    val diameterY = new TextField():
      promptText = "diameter in cm"

  //The color picker
    val colorPick = new ColorPicker()

  //Here is given the coordinates for textfields and colorpicker.
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

  // Here for the confirmation button is set that it won't work unless there has been given right amount of information
  // and that the information is being given in right form
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

  // From the given measurements and color the program forms an ellipse.
  // The ellipse is scaled to the picture by factor 0.636.
  // By doing this the furnitures are in their realistic measurements on the picture.
    dialog.resultConverter = dialogButton =>
      if (dialogButton == confirmButtonType) then
        possibleEllipse = Option(Ellipse(diameterX.text().toDouble * 0.636, diameterY.text().toDouble * 0.636))
        possibleColor = Option(colorPick.getValue)
        Ellipse((diameterX.text().toDouble * 0.636)/2, (diameterY.text().toDouble * 0.636)/2)
      else
        null

    val result = dialog.showAndWait()

  // Here the formed Ellipse is implemented on the floorplan.
    result match
      case Some(c: Ellipse) =>
        val el = new Ellipse()
        el.setCenterX(400)
        el.setCenterY(50)
        el.setRadiusX(possibleEllipse.get.radiusX.toDouble/2)
        el.setRadiusY(possibleEllipse.get.radiusY.toDouble/2)
        el.fill = possibleColor.get
        val ellipseFurniture = Furniture(possibleFurniture.get, el, possibleColor.get)
        ellipseFurniture.x = 400
        ellipseFurniture.y = 50
        val drag = new Controller()
        drag.createHandlers(ellipseFurniture)
        ellipseFurniture
      case None => println("Dialog returned: None")
      case _ => println("something else happened")

  // If user has chosen TV as an furniture, the height and the width are constant:
  def sizeSelectForTV =

  //Measured shape is being stored in here for later, when the program constructs the wanted Furniture
      var possibleRect: Option[Rectangle] = None

  //The dialog which asks the user to implement measurements for the TV
      val dialog = new Dialog[Rectangle]():
        initOwner(stage)
        title = "Measurements"
        headerText = "Please enter the measurements"

  //Buttons for confirmation and cancel
      val confirmButtonType = new ButtonType("Confirm", ButtonData.OKDone)
      dialog.dialogPane().buttonTypes = Seq(confirmButtonType, ButtonType.Cancel)

  // Text fields that asks for measurements
  // The measurements are given in cm.
      val sideL1 = new TextField():
        promptText = "lenght in cm"

      val sideL2 = new TextField():
        promptText = "lenght in cm"
  //Here is given the coordinates for textfields.
      val grid = new GridPane():
         hgap = 10
         vgap = 10
         padding = Insets(20, 100, 10, 10)

         add(new Label("Width x:"), 0, 0)
         add(sideL1, 1,0)
         add(new Label("Width y:"), 0, 1)
         add(sideL2,1,1)

  // Here for the confirmation button is set that it won't work unless there has been given right amount of information
  // and that the information is being given in right form
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

  // From the given measurements and color the program forms a rectangle.
  // The rectangle is scaled to the picture by factor 0.636.
  // By doing this the furnitures are in their realistic measurements on the picture.
      dialog.resultConverter = dialogButton =>
        if (dialogButton == confirmButtonType) then
          possibleRect = Option(Rectangle(sideL1.text().toDouble * 0.636, sideL2.text().toDouble * 0.636, Color.Black))
          Rectangle(sideL1.text().toDouble * 0.636, sideL2.text().toDouble * 0.636, Color.Black)
        else
          null

      val result = dialog.showAndWait()

  // Here the formed Rectangle is implemented on the floorplan.
      result match
        case Some(re: Rectangle) =>
          val r = new Rectangle()
          r.setX(400)
          r.setY(50)
          r.setWidth(possibleRect.get.width.toDouble)
          r.setHeight(possibleRect.get.height.toDouble)
          r.fill = possibleRect.get.fill.get()

          val RectangleFurniture = Furniture(possibleFurniture.get, r, Color.Black)
          RectangleFurniture.x = 400
          RectangleFurniture.y = 50
          val drag = new Controller()
          drag.createHandlers(RectangleFurniture)
          RectangleFurniture

        case None => println("Dialog returned: None")
        case _ => println("something else happened")

