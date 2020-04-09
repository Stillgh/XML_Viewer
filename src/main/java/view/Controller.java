package view;

import controller.Parser;
import controller.ParserHelper;
import controller.TreeFiller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Element;
import model.Student;
import model.Task;
import model.TaskResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Controller {


  @FXML
  public AnchorPane Main;
  @FXML
  public MenuItem loadXML;
  @FXML
  public TreeView treeView;
  @FXML
  public TextFlow textFlow;



  @FXML
  public void initialize() {

    treeView.setCellFactory(param -> new TreeCell<Element>() {
      @Override
      protected void updateItem(Element item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText("");
        } else {
          setText(item.toString());
        }
      }
    });

  }

  public void selectItem(MouseEvent mouseEvent) {
    TreeItem<Element> selectedItem = (TreeItem<Element>) treeView.getSelectionModel()
        .getSelectedItem();

    textFlow.getChildren().clear();


     if (selectedItem != null && selectedItem.getValue().getClass().equals(Task.class)) {
      Task taskForId = (Task) selectedItem.getValue();
      int taskID = taskForId.getTaskID();
      Student studentForTaskResult = (Student) selectedItem.getParent().getParent().getParent()
          .getValue();
      List<TaskResult> taskResultList = studentForTaskResult.getTaskResultList();
      String taskStatus;
      int taskMark;
      for (TaskResult tr : taskResultList) {
        if (tr.getTaskID() == taskID) {
          taskStatus = tr.getTaskStatus();
          if (tr.getTaskMark() != 0) {
            taskMark = tr.getTaskMark();
            textFlow.getChildren().add(new Text(
                selectedItem.getValue().forTextFlow() + "\n" + "Status: " + taskStatus + "\n"
                    + "Mark: " + taskMark));
          } else {
            textFlow.getChildren().add(
                new Text(selectedItem.getValue().forTextFlow() + "\n" + "Status: " + taskStatus));
          }
        }
      }
    } else if (selectedItem != null){
      String str = selectedItem.getValue().forTextFlow();
      Text text = new Text(str);
      textFlow.getChildren().add(text);
    }
  }

  public void loadFile(ActionEvent actionEvent) throws ParserConfigurationException {
    FileChooser fc = new FileChooser();
    File selectedFile = fc.showOpenDialog(null);
    ParserHelper parser = new Parser();

    if (selectedFile != null) {
      try {
        parser.createValidation(selectedFile);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File fileToParse = new File(selectedFile.getPath());
        Document doc = builder.parse(fileToParse);

        parser.parseTasks(doc);
        parser.parseCourses(doc);
        parser.parseCurriculums(doc);
        List<Student> allStudents = parser.parseAllStudents(doc);

        TreeFiller treeFiller = new TreeFiller(allStudents);
        TreeItem<Element> mainTree = treeFiller.fillTree();
        treeView.setRoot(mainTree);
      } catch (SAXException | IOException e) {
        AlertHandler.showAlertWindow(e);
      }
    }
  }

  public void closeApp(ActionEvent actionEvent) {
    Alert alert = new Alert(AlertType.INFORMATION,
        "GOOD BYE!");
    alert.setTitle("GOOD BYE!");
    alert.setHeaderText("=(");
    alert.show();
    PauseTransition delay = new PauseTransition(Duration.millis(700));
    delay.setOnFinished(event -> System.exit(1));
    delay.play();
  }
}
