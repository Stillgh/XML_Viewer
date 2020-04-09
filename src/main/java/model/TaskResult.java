package model;

public class TaskResult extends Element{
  private int taskID;
  private String taskStatus;
  private int taskMark;

  public int getTaskID() { return taskID; }

  public void setTaskID(int taskID) { this.taskID = taskID; }

  public String getTaskStatus() { return taskStatus; }

  public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }

  public int getTaskMark() { return taskMark; }

  public void setTaskMark(int taskMark) { this.taskMark = taskMark; }
}
