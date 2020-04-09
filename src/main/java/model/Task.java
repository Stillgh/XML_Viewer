package model;

public class Task extends Element {

  private int taskID;
  private String taskType;
  private String title;
  private int taskDuration;
  private String author;
  private String creationDate;
  private String taskStatus;
  private int taskMark;


  @Override
  public String toString() {
    return "" + title;
  }

  @Override
  public String forTextFlow() {
    return "Title: " + title + "\n" +
        "Author: " + author + "\n" +
        "Last modified: " + creationDate + "\n" +
        "Duration(hrs): " + taskDuration + "\n" +
        "Task type: " + taskType;
  }

  public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }

  public void setTaskMark(int taskMark) { this.taskMark = taskMark; }

  public int getTaskID() {
    return taskID;
  }

  public void setTaskID(int taskID) {
    this.taskID = taskID;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getTaskDuration() {
    return taskDuration;
  }

  public void setTaskDuration(int taskDuration) {
    this.taskDuration = taskDuration;
  }

  public void setAuthor(String author) { this.author = author; }

  public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
}
