package roles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class StudentTest {

	ArrayList<Student> students;
	ArrayList<Admin> admins; 
	ArrayList<Professor> professors; 
	ArrayList<Course> courses;
	@BeforeEach
	void setUp() throws Exception {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		students = fr.getStudentsInfo();
		fr.setUpAdmin("adminInfo.txt");
		admins = fr.getAdminsInfo();
		fr.setUpProfessor("profInfo.txt");
		professors = fr.getProfessorsInfo();
		fr.setUpCourse("courseInfo.txt");
		courses = fr.getCoursesInfo();
	}

	@Test
	void testGetStudentByName() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		assertEquals(Student.getStudentByName("StudentName1", students).getName(),"StudentName1");
		assertEquals(Student.getStudentByName("StudentName2", students).getId(),"002");
	}

	@Test
	void testGetStudentById() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		assertEquals(Student.getStudentById("001", students).getName(),"StudentName1");
		assertEquals(Student.getStudentById("002", students).getId(),"002");
	}

	@Test
	void testGetStudentByUserName() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		assertEquals(Student.getStudentByUserName("testStudent01", students).getName(),"StudentName1");
		assertEquals(Student.getStudentByUserName("testStudent02", students).getId(),"002");
	}
	

	@Test
	void testCheckExistById() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		assertTrue(Student.checkExistById ("001",students));
		assertTrue(Student.checkExistById("002", students));
		assertFalse(Student.checkExistById("020", students));
	}
	
	@Test
	void testCheckExistByUsername() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		assertTrue(Student.checkExistByUsername("testStudent01", students));
		assertTrue(Student.checkExistByUsername("testStudent02", students));
		assertFalse(Student.checkExistByUsername("testStudent03", students));
	}
	@Test
	void testAddNewCourse() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		Student student1=students.get(0);
		ArrayList<String> sc1=student1.getCoursesTake();
		assertEquals(sc1.size(),2);
		student1.addNewCourse("ESE542");
		assertTrue(student1.getCoursesTake().contains("ESE542"));
		assertEquals(sc1.size(),3);
	}
	@Test
	void testDropCourse() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		Student student1=students.get(0);
		ArrayList<String> sc1=student1.getCoursesTake();
		assertEquals(sc1.size(),2);
		student1.addNewCourse("ESE542");
		assertTrue(student1.getCoursesTake().contains("ESE542"));
		assertEquals(sc1.size(),3);
		student1.dropCourse("ESE542");
		assertEquals(sc1.size(),2);
	}

}
