package model;

import java.util.List;

public class Curriculum extends Element {

  private int curriculumID;
  private String title;
  private String author;
  private String creationDate;
  private List<Course> curriculumCoursesList;

  @Override
  public String toString() {
    return "" + title;
  }

  @Override
  public String forTextFlow() {
    return "Title: " + title + "\n" +
        "Author: " + author + "\n" +
        "Last modified: " + creationDate + "\n" +
        "Duration(hrs): " + getCoursesDuration() + "\n" + "\n" +
        "Courses: " + "\n" + getCoursesTitles();
  }

  public String getCoursesTitles() {
    String coursesTitles = "";
    for (Course c : curriculumCoursesList) {
      coursesTitles += c.getTitle() + "\n";
    }
    return coursesTitles;
  }

  public int getCoursesDuration() {
    int coursesDuration = 0;
    for (Course c : curriculumCoursesList) {
      coursesDuration += c.getDuration();
    }
    return coursesDuration;
  }

  public int getCurriculumID() {
    return curriculumID;
  }

  public void setCurriculumID(int curriculumID) {
    this.curriculumID = curriculumID;
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

  public List<Course> getCurriculumCoursesList() {
    return curriculumCoursesList;
  }

  public void setCurriculumCoursesList(List<Course> curriculumCoursesList) {
    this.curriculumCoursesList = curriculumCoursesList;
  }
}
