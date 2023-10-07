package roles;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import courses.Course;
import files.FileInfoReader;

class AdminTest {
	ArrayList<Student> students;
	ArrayList<Admin> admins; 
	ArrayList<Professor> professors; 
	ArrayList<Course> courses;
	Admin testAdmin;
	Student testStudent;
	Professor testProfessor;
	Course testCourse;
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
		testAdmin=new Admin("testAdmin","111","123","testAdmin1");
		testStudent=new Student("testStudent","111","123","testStudent1");
		testProfessor=new Professor("testProfessor","111","123","testProfessor1");
		testCourse=new Course("111","testCourse","Max Mintz","MW", "7:00", "8:30", 100);
	}

	@Test
	void testAddNewCourse() {
		Admin.addNewCourse(testCourse, professors, courses);
		assertTrue(courses.contains(testCourse));
		Professor testProfessor =Professor.getProfessorByName(testCourse.getLecturer(), professors); 
		assertTrue(testProfessor.getGivenCourses().contains(testCourse));
	}
	@Test
	void testDeleteCourse() {
		assertFalse(courses.contains(testCourse));
		Admin.addNewCourse(testCourse, professors, courses);
		assertTrue(courses.contains(testCourse));
		Professor testProfessor =Professor.getProfessorByName(testCourse.getLecturer(), professors); 
		assertTrue(testProfessor.getGivenCourses().contains(testCourse));		
		Admin.deleteCourse("111", courses, students, professors);
		assertFalse(courses.contains(testCourse));
		assertFalse(testProfessor.getGivenCourses().contains(testCourse));
	}
	@Test
	void testAddNewStudentGrade() {
		Admin.addNewStudentGrade(testStudent, "CIT590", "A");
		assertTrue(testStudent.getCoursesTake().contains("CIT590"));
	}
	@Test
	void testDeleteStudent() {
		assertEquals(students.size(),2);
		Student studentDeleted=Student.getStudentById("001", students);
		Course courseToDeleteAStudent = Course.getCourseById("CIS191", courses) ;
		assertTrue(courseToDeleteAStudent.setAndGetStudentInTheCourse("CIS191",students).contains(studentDeleted));
		Admin.deleteStudent("001", students, courses);
		assertFalse(courseToDeleteAStudent.getStudentInCourse().contains(studentDeleted));
		assertEquals(students.size(),1);		
	}
	@Test
	void testAddNewProfessor() {
		assertEquals(professors.size(),32);
		Admin.addNewProfessor("testp", "321", "testp", "12345", professors);		
		assertEquals(professors.size(),33);
	}
	@Test
	void testDeleteProfessor() {
		assertEquals(professors.size(),32);
		Admin.deleteProfessor("032", professors);		
		assertEquals(professors.size(),31);
		assertEquals(Professor.getProfessorById("032", professors),null);
	}
	@Test
	void testGetAdminByUsername() {		
		assertEquals(Admin.getAdminByUsername("admin01", admins).getName(),"admin");
	}
}








