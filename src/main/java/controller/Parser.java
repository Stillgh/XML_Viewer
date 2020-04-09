package controller;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import model.Course;
import model.Curriculum;
import model.Student;
import model.Task;
import model.TaskResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Класс парсит сущности из XML документа и добавляет их в коллекции.
 * Соответственно класс парсит сущности Task, Course, Curriculum, Student, создает их объекты и добавляет либо в лист, либо в мэпу
 * Также у класс проверяет спаршиваемый файла на соответствие определенной xsd схеме.
 */

public class Parser implements ParserHelper {

  private Map<Integer, Task> allTasksMap;
  private Map<Integer, Course> allCoursesMap;
  private Map<Integer, Curriculum> allCurriculumMap;
  private final String XSD_FILE_LOCATION = "src/main/resources/Report.xsd";

  public Map<Integer, Task> getAllTasksMap() { return allTasksMap; }
  public Map<Integer, Course> getAllCoursesMap() { return allCoursesMap; }
  public Map<Integer, Curriculum> getAllCurriculumMap() { return allCurriculumMap; }

  /**
   *Метод используется для проверки спаршиваемого xml документа на соответствие xsd схеме
   */

  public void createValidation(File selectedFile) throws SAXException, IOException {
    SchemaFactory factorySchema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = factorySchema.newSchema(new StreamSource(XSD_FILE_LOCATION));
    Validator validator = schema.newValidator();
    String xml = selectedFile.getPath();
    validator.validate(new StreamSource(xml));
  }

  /**
   * метод пробегается по списку спаршенных элементов Task, создаёт по ним объекты класса Task и
   * добавляет их в мапу со списком всех тасок
   */

  private void  fillTasksMap(NodeList allTasks){
    for (int i = 0; i < allTasks.getLength(); i++) {
      Node taskNode = allTasks.item(i);
      if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
        Element elTask = (Element) taskNode;
        Task myTask = new Task();
        myTask.setTaskID(Integer.parseInt(elTask.getAttribute("taskID")));
        myTask.setTaskType(elTask.getAttribute("type"));
        myTask.setTitle(elTask.getElementsByTagName("task_title").item(0).getTextContent());
        myTask.setTaskDuration(
            Integer.parseInt(elTask.getElementsByTagName("duration").item(0).getTextContent()));
        allTasksMap.put(Integer.parseInt(elTask.getAttribute("taskID")), myTask);
      }
    }
  }

  /**
   * метод пробегается по списку спаршенных элементов Course, создаёт по ним объекты класса Course и
   * добавляет их в мапу со списком всех курсов
   */
  private void fillCoursesMap(NodeList allCourses){
    for (int i = 0; i < allCourses.getLength(); i++) {
      Node courseNode = allCourses.item(i);
      if (courseNode.getNodeType() == Node.ELEMENT_NODE) {

        Element elCourse = (Element) courseNode;
        Course myCourse = new Course();
        myCourse.setCourseID(Integer.parseInt(elCourse.getAttribute("id")));
        myCourse.setTitle(elCourse.getElementsByTagName("title").item(0).getTextContent());
        myCourse.setAuthor(elCourse.getElementsByTagName("author").item(0).getTextContent());
        myCourse.setCreationDate(
            elCourse.getElementsByTagName("creationDate").item(0).getTextContent());

        Node courseTaskList = elCourse.getElementsByTagName("courseTasks").item(0);
        Element elCourseTaskList = (Element) courseTaskList;
        NodeList courseTaskListNodeList = elCourseTaskList.getChildNodes();
        List<Task> taskInCourse = new ArrayList<>();

        for (int j = 0; j < courseTaskListNodeList.getLength(); j++) {
          Node courseTaskNode = courseTaskListNodeList.item(j);
          if (courseTaskNode.getNodeType() == Node.ELEMENT_NODE) {
            Element elCourseTask = (Element) courseTaskNode;
            Task t = allTasksMap.get(
                Integer.parseInt(elCourseTask.getElementsByTagName("id").item(0).getTextContent()));
            t.setAuthor(elCourse.getElementsByTagName("author").item(0).getTextContent());
            t.setCreationDate(
                elCourse.getElementsByTagName("creationDate").item(0).getTextContent());
            taskInCourse.add(t);
          }
        }

        myCourse.setCourseTasksList(taskInCourse);
        allCoursesMap.put(Integer.parseInt(elCourse.getAttribute("id")), myCourse);
      }
    }
  }

  /**
   * метод пробегается по списку спаршенных элементов Curriculum, создаёт по ним объекты класса Curriculum и
   * добавляет их в мапу со списком всех куррикулумов
   */
  private void fillCurriculumsMap(NodeList allCurriculum){
    for (int i = 0; i < allCurriculum.getLength(); i++) {
      Node curriculumNode = allCurriculum.item(i);
      if (curriculumNode.getNodeType() == Node.ELEMENT_NODE) {
        Element elCurriculum = (Element) curriculumNode;
        Curriculum myCurriculum = new Curriculum();
        List<Course> curriculumCourses = new ArrayList<>();
        myCurriculum.setCurriculumID(Integer.parseInt(elCurriculum.getAttribute("curriculumID")));
        myCurriculum.setTitle(elCurriculum.getElementsByTagName("title").item(0).getTextContent());
        myCurriculum
            .setAuthor(elCurriculum.getElementsByTagName("author").item(0).getTextContent());
        myCurriculum.setCreationDate(
            elCurriculum.getElementsByTagName("creationDate").item(0).getTextContent());

        Node curriculumCoursesNode = elCurriculum.getElementsByTagName("curriculumCourses").item(0);
        Element elCurriculumCourses = (Element) curriculumCoursesNode;
        NodeList curriculumCoursesNodeList = elCurriculumCourses.getChildNodes();
        for (int j = 0; j < curriculumCoursesNodeList.getLength(); j++) {
          Node courseInCurNode = curriculumCoursesNodeList.item(j);
          Course myCourse = new Course();
          if (courseInCurNode.getNodeType() == Node.ELEMENT_NODE) {
            Element elCourseInCur = (Element) courseInCurNode;
            myCourse.setCourseID(Integer
                .parseInt(elCourseInCur.getElementsByTagName("id").item(0).getTextContent()));
            curriculumCourses.add(allCoursesMap.get(Integer
                .parseInt(elCourseInCur.getElementsByTagName("id").item(0).getTextContent())));
          }
        }
        myCurriculum.setCurriculumCoursesList(curriculumCourses);
        allCurriculumMap
            .put(Integer.parseInt(elCurriculum.getAttribute("curriculumID")), myCurriculum);
      }
    }
  }

  /**
   * метод пробегается по списку спаршенных элементов TaskResult, создаёт по ним объекты класса TaskResult и
   * добавляет их в лист со списком всех таскрезалтов, принадлежащих каждому конкретному студенту
   */

  private void fillStudentTaskResultList(NodeList studentTaskResultNodeList,
      Map<Integer, Task> studTaskMap, List<TaskResult> studTaskResultList){
    for (int j = 0; j < studentTaskResultNodeList.getLength(); j++) {
      Node taskResultNode = studentTaskResultNodeList.item(j);
      TaskResult taskResult = new TaskResult();
      if (taskResultNode.getNodeType() == Node.ELEMENT_NODE) {
        Element elTaskResult = (Element) taskResultNode;
        int taskID = Integer.parseInt(elTaskResult.getAttribute("taskID"));
        taskResult.setTaskID(taskID);
        String taskStat = elTaskResult.getElementsByTagName("task_status").item(0)
            .getTextContent();
        taskResult.setTaskStatus(
            elTaskResult.getElementsByTagName("task_status").item(0).getTextContent());
        studTaskMap.put(taskID, allTasksMap.get(taskID));
        studTaskMap.get(taskID).setTaskStatus(taskStat);
        if (elTaskResult.getElementsByTagName("mark").item(0) != null) {
          int markTask = Integer
              .parseInt(elTaskResult.getElementsByTagName("mark").item(0).getTextContent());
          studTaskMap.get(taskID).setTaskMark(markTask);
          taskResult.setTaskMark(Integer
              .parseInt(elTaskResult.getElementsByTagName("mark").item(0).getTextContent()));
        }
        studTaskResultList.add(taskResult);
      }
    }
  }

  /**
   * метод пробегается по списку спаршенных элементов Student, создаёт по ним объекты класса Student и
   * добавляет их в лист со списком всех студентов
   */

  private void fillStudentsList(NodeList studentsList, List<Student> students){

    for (int i = 0; i < studentsList.getLength(); i++) {
      Node node = studentsList.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        List<TaskResult> studTaskResultList = new ArrayList<>();

        Map<Integer, Task> studTaskMap = new HashMap<>();
        Element student = (Element) node;
        String studentID = student.getAttribute("id");

        Student myStudent = new Student();
        myStudent.setStudentID(Integer.parseInt(studentID));

        NodeList studentSubTags = student.getChildNodes();
        Element elStudentSubTags = (Element) studentSubTags;

        myStudent.setFullName(
            elStudentSubTags.getElementsByTagName("fullName").item(0).getTextContent());
        myStudent.setEmail(elStudentSubTags.getElementsByTagName("email").item(0).getTextContent());
        myStudent.setCity(elStudentSubTags.getElementsByTagName("city").item(0).getTextContent());
        myStudent.setStartOfStudying(
            elStudentSubTags.getElementsByTagName("startOfStudying").item(0).getTextContent());
        myStudent.setSigningOfContract(Boolean.parseBoolean(
            elStudentSubTags.getElementsByTagName("signingOfContract").item(0).getTextContent()));
        myStudent.setCurriculumID(Integer.parseInt(
            elStudentSubTags.getElementsByTagName("curriculumID").item(0).getTextContent()));
        myStudent.setCurriculum(allCurriculumMap.get(Integer.parseInt(
            elStudentSubTags.getElementsByTagName("curriculumID").item(0).getTextContent())));

        Node studentResult = elStudentSubTags.getElementsByTagName("results").item(0);
        Element elStudentResult = (Element) studentResult;
        NodeList studentTaskResultNodeList = elStudentResult.getChildNodes();

        fillStudentTaskResultList(studentTaskResultNodeList, studTaskMap, studTaskResultList);

        myStudent.setTasksMap(studTaskMap);
        myStudent.setTaskResultList(studTaskResultList);
        students.add(myStudent);
      }
    }
  }

  /**
   *метод парсит элементы Task и вызывает метод fillTasksMap
   */

  public void parseTasks(Document documentToParse) {
    NodeList taskListXML = documentToParse.getElementsByTagName("taskList");
    Node taskListNode = taskListXML.item(0);
    Element elTaskList = (Element) taskListNode;
    NodeList allTasks = elTaskList.getElementsByTagName("task");
    this.allTasksMap = new HashMap<>();

    fillTasksMap(allTasks);
  }

  /**
   *метод парсит элементы Course и вызывает метод fillCourseMap
   */

  public void parseCourses(Document documentToParse) {
    NodeList courseListXML = documentToParse.getElementsByTagName("courseList");
    Node courseListNode = courseListXML.item(0);
    Element elCourseList = (Element) courseListNode;
    NodeList allCourses = elCourseList.getElementsByTagName("course");
    this.allCoursesMap = new HashMap<>();

    fillCoursesMap(allCourses);
  }

  /**
   *метод парсит элементы Curriculum и вызывает метод parseCurriculums
   */

  public void parseCurriculums(Document documentoParse) {

    NodeList allCurriculum = documentoParse.getElementsByTagName("curriculum");
    this.allCurriculumMap = new HashMap<>();
    fillCurriculumsMap(allCurriculum);
  }

  /**
   *метод парсит элементы Student и вызывает метод fillStudentsList
   */

  public List<Student> parseAllStudents(Document documentToParse) {

    NodeList studentsList = documentToParse.getElementsByTagName("student");
    List<Student> students = new ArrayList<>();
    fillStudentsList(studentsList, students);
    return students;
  }

}
