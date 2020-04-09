package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ParserTest {

  ParserHelper parser = new Parser();
  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
  DocumentBuilder builder;
  Document doc;
  {
    try {
      builder = factory.newDocumentBuilder();
      doc = builder.parse("src/main/resources/StudentReport.xml");
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }






  @org.junit.jupiter.api.Test
  void parseTasks() {
    parser.parseTasks(doc);
    assertEquals(16, parser.getAllTasksMap().size());
  }

  @org.junit.jupiter.api.Test
  void parseCourses() {
    parser.parseTasks(doc);
    parser.parseCourses(doc);
    assertEquals(8, parser.getAllCoursesMap().size());
  }

  @org.junit.jupiter.api.Test
  void parseCurriculums() {
    parser.parseTasks(doc);
    parser.parseCourses(doc);
    parser.parseCurriculums(doc);
    assertEquals(3, parser.getAllCurriculumMap().size());
  }

  @org.junit.jupiter.api.Test
  void parseAllStudents() {
    parser.parseTasks(doc);
    parser.parseCourses(doc);
    parser.parseCurriculums(doc);
    assertEquals(4, parser.parseAllStudents(doc).size());
  }
}