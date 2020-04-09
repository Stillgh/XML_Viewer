package model;

import java.util.List;

public class Course extends Element {

  private int courseID;
  private String title;
  private String author;
  private String creationDate;
  private List<Task> courseTasksList;

  @Override
  public String toString() {
    return title;
  }

  @Override
  public String forTextFlow() {
    return "Title: " + title + "\n" +
        "Author: " + author + "\n" +
        "Last modified: " + creationDate + "\n" +
        "Duration(hrs): " + getDuration() + "\n" + "\n" +
        "Tasks: " + "\n" + getTasksTitles();
  }


  public String getTasksTitles() {
    String tasksTitles = "";
    for (Task task : courseTasksList) {
      tasksTitles += task.getTitle() + "\n";
    }
    return tasksTitles;
  }


  public int getDuration() {
    int overallDuration = 0;
    for (Task task : courseTasksList) {
      overallDuration += task.getTaskDuration();
    }
    return overallDuration;
  }


  public int getCourseID() {
    return courseID;
  }

  public void setCourseID(int courseID) {
    this.courseID = courseID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(String creationDate) {
    this.creationDate = creationDate;
  }

  public List<Task> getCourseTasksList() {
    return courseTasksList;
  }

  public void setCourseTasksList(List<Task> courseTasksList) {
    this.courseTasksList = courseTasksList;
  }


}
