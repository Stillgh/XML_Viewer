package view;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AlertHandler {
  private static final Logger LOGGER = LoggerFactory.getLogger(AlertHandler.class);;

  public static void showAlertWindow(Throwable throwable) {
    Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.setHeaderText("File format/validation error");
    errorAlert.setContentText("Not a XML file or a problem with XSD validation");

    LOGGER.error(throwable.getMessage());

    errorAlert.showAndWait();
  }
}
