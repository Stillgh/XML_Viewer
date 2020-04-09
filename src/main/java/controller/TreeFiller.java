package controller;

import java.util.List;
import javafx.scene.control.TreeItem;
import model.Course;
import model.Curriculum;
import model.Element;
import model.Student;
import model.Task;


public class TreeFiller {


  private List<Student> allStudents;
  public TreeFiller(List<Student> allStudents) {
    this.allStudents = allStudents;
  }
  public TreeItem<Element> fillTree() {

    TreeItem<Element> myTree = new TreeItem<>(new Element());



    //добавляем студентов
    for (Student student : allStudents) {
      myTree.getChildren().add(new TreeItem<>(student));
    }

    //добавляем куррикулумы
    for (int i = 0; i < myTree.getChildren().size(); i++) {
      myTree.getChildren().get(i).getChildren()
          .add(new TreeItem<>(allStudents.get(i).getCurriculum()));
    }

    //добавляем курсы
    for (TreeItem<Element> studentItem : myTree.getChildren()) {
      for (TreeItem<Element> curriculumItem : studentItem.getChildren()) {
        Curriculum cur = (Curriculum) curriculumItem.getValue();
        for (Course course : cur.getCurriculumCoursesList()) {
          curriculumItem.getChildren().add(new TreeItem<>(course));
        }
      }
    }

    //добавляем таски
    for (TreeItem<Element> studentItem : myTree.getChildren()) {
      for (TreeItem<Element> curriculumItem : studentItem.getChildren()) {
        for (TreeItem<Element> courseItem : curriculumItem.getChildren()) {
          Course c = (Course) courseItem.getValue();
          for (Task task : c.getCourseTasksList()) {
            courseItem.getChildren().add(new TreeItem<>(task));
          }
        }
      }
    }
    return myTree;
  }
}

