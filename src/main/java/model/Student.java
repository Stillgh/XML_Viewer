package model;

import java.util.List;
import java.util.Map;

public class Student extends Element {

  private int studentID;
  private String fullName;
  private String email;
  private String city;
  private String startOfStudying;
  private boolean signingOfContract;
  private int curriculumID;
  private Curriculum curriculum;
  private List<TaskResult> taskResultList;
  private Map<Integer, Task> tasksMap;


  @Override
  public String toString() {
    return "" + fullName;
  }

  @Override
  public String forTextFlow() {
    return "Full Name: " + fullName + "\n" +
        "E-mail: " + email + "\n" +
        "Region: " + city + "\n" +
        "Contract signed: " + signingOfContract + "\n" +
        "Start date: " + startOfStudying;
  }

  public Curriculum getCurriculum() {
    return curriculum;
  }

  public void setCurriculum(Curriculum curriculum) {
    this.curriculum = curriculum;
  }

  public int getStudentID() {
    return studentID;
  }

  public void setStudentID(int studentID) {
    this.studentID = studentID;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStartOfStudying() {
    return startOfStudying;
  }

  public void setStartOfStudying(String startOfStudying) {
    this.startOfStudying = startOfStudying;
  }

  public boolean isSigningOfContract() {
    return signingOfContract;
  }

  public void setSigningOfContract(boolean signingOfContract) {
    this.signingOfContract = signingOfContract;
  }

  public int getCurriculumID() {
    return curriculumID;
  }

  public void setCurriculumID(int curriculumID) {
    this.curriculumID = curriculumID;
  }

  public List<TaskResult> getTaskResultList() {
    return taskResultList;
  }

  public void setTaskResultList(List<TaskResult> taskResultList) {
    this.taskResultList = taskResultList;
  }

  public Map<Integer, Task> getTasksMap() {
    for (Course c : curriculum.getCurriculumCoursesList()) {
      for (Task t : c.getCourseTasksList()) {
        tasksMap.put(t.getTaskID(), t);
      }
    }
    return tasksMap;
  }

  public void setTasksMap(Map<Integer, Task> tasksMap) {
    this.tasksMap = tasksMap;
  }
}
