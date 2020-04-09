package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.Course;
import model.Curriculum;
import model.Student;
import model.Task;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public interface ParserHelper {

  public Map<Integer, Task> getAllTasksMap();
  public Map<Integer, Course> getAllCoursesMap();
  public Map<Integer, Curriculum> getAllCurriculumMap();
  public void createValidation(File selectedFile) throws SAXException, IOException;
  public void parseTasks(Document documentToParse);
  public void parseCourses(Document documentToParse);
  public void parseCurriculums(Document documentoParse);
  public List<Student> parseAllStudents(Document documentToParse);


}
