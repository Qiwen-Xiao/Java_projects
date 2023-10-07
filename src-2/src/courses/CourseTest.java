package courses;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

class CourseTest {
	ArrayList<Student> students;
	ArrayList<Admin> admins; 
	ArrayList<Professor> professors; 
	ArrayList<Course> courses;
	Admin testAdmin;
	Student testStudent;
	Professor testProfessor;
	Course testCourse1;
	Course testCourse2;
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		students = fr.getStudentsInfo();
		fr.setUpAdmin("adminInfo.txt");
		admins = fr.getAdminsInfo();
		fr.setUpProfessor("profInfo.txt");
		professors = fr.getProfessorsInfo();
		fr.setUpCourse("courseInfo.txt");
		courses = fr.getCoursesInfo();
		testAdmin=new Admin("testAdmin","111","123","testAdmin1");
		testStudent=new Student("testStudent","111","123","testStudent1");
		testProfessor=new Professor("testProfessor","111","123","testProfessor1");
		testCourse1=new Course("ESE111","testCourse","Max Mintz","MW", "22:00", "22:30", 100);
		testCourse2=new Course("ESE222","testCourse","Max Mintz","MW", "16:00", "19:00", 100);
	}

	@Test
	void testCheckCourseTimeConflict() throws ParseException {
		assertFalse(testCourse1.checkCourseTimeConflict(testCourse1, courses));
		assertTrue(testCourse2.checkCourseTimeConflict(testCourse2, courses));
		
	}

	@Test
	void testAddAStudent() {
		assertFalse(testCourse1.getStudentInCourse().contains(testStudent));
		assertEquals(testCourse1.getStudentInCourse().size(),0);
		testCourse1.addAStudent(testStudent);
		assertTrue(testCourse1.getStudentInCourse().contains(testStudent));
		assertEquals(testCourse1.getStudentInCourse().size(),1);
		
	}

	@Test
	void testRemoveAStudent() {
		assertFalse(testCourse1.getStudentInCourse().contains(testStudent));
		testCourse1.addAStudent(testStudent);
		assertTrue(testCourse1.getStudentInCourse().contains(testStudent));
		testCourse1.removeAStudent(testStudent);
		assertFalse(testCourse1.getStudentInCourse().contains(testStudent));
	}

	@Test
	void testGetCourseById() {
		Course testCourse1=Course.getCourseById("CIS660", courses);
		Course testCourse2=Course.getCourseById("CIS666", courses);
		assertEquals(testCourse1.getCourseName(),"Advanced Topics in Computer Graphics and Animation");
		assertEquals(testCourse2,null);
	}

	@Test
	void testSetAndGetStudentInTheCourse() {
		assertEquals(testCourse1.setAndGetStudentInTheCourse("CIS191", students).get(0).getId(),"001");
		assertEquals(testCourse1.setAndGetStudentInTheCourse("CIS530", students).size(),0);
	}

	@Test
	void testCheckCapacity() {
		Course CIS660=Course.getCourseById("CIS660", courses);
		for(int i=1;i<=31;i++) {
			CIS660.addAStudent(testStudent);
		}
		assertEquals(CIS660.checkCapacity(),true);
		assertEquals(CIS660.getStudentInCourse().size(),31);
		CIS660.addAStudent(testStudent);
		assertEquals(CIS660.getStudentInCourse().size(),32);
		assertFalse(CIS660.checkCapacity());
	}
	
	@Test
	void testCheckExistById() {
		assertTrue(Course.checkExistById("CIT590", courses));
		assertFalse(Course.checkExistById("CIT999", courses));
	}

}
