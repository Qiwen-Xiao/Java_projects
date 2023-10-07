package roles;

import static org.junit.jupiter.api.Assertions.*;

import courses.Course;
import files.FileInfoReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

class ProfessorTest {

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
	void testGetProfessorByName() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpProfessor("profInfo.txt");
		ArrayList<Professor> professors = fr.getProfessorsInfo();
		assertEquals(Professor.getProfessorByName("Clayton Greenberg", professors).getId(),"001");
		assertEquals(Professor.getProfessorByName("Joseph L Devietti", professors).getId(),"015");
	}
	
	@Test
	void testGetProfessorByID() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpProfessor("profInfo.txt");
		ArrayList<Professor> professors = fr.getProfessorsInfo();
	assertEquals(Professor.getProfessorById("001", professors).getName(),"Clayton Greenberg");
	assertEquals(Professor.getProfessorById("002", professors).getName(),"Harry Smith");
	}
	

	@Test
	void testGetProfessorByUsername() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpProfessor("profInfo.txt");
		ArrayList<Professor> professors = fr.getProfessorsInfo();
		assertEquals(Professor.getProfessorByUsername("Greenberg", professors).getName(),"Clayton Greenberg");
		assertEquals(Professor.getProfessorByUsername("Davidson", professors).getId(),"021");
	}
	@Test
	void testCheckExistById() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpProfessor("profInfo.txt");
		ArrayList<Professor> professors = fr.getProfessorsInfo();
		assertTrue(Professor.checkExistById ("010",professors));
		assertFalse(Professor.checkExistById("200", professors));
	}

	@Test
	void testcheckExistByUsername() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpProfessor("profInfo.txt");
		ArrayList<Professor> professors = fr.getProfessorsInfo();
		assertTrue(Professor.checkExistByUsername("Ives", professors));
		assertTrue(Professor.checkExistByUsername("Gallier", professors));
		assertFalse(Professor.checkExistByUsername("test", professors));
	}
	
	@Test
	void testSetCoursesTeach() {
		FileInfoReader fr = new FileInfoReader();
		fr.setUpStudent("studentInfo.txt");
		ArrayList<Student> students = fr.getStudentsInfo();
		fr.setUpAdmin("adminInfo.txt");
		ArrayList<Admin> admins = fr.getAdminsInfo();
		fr.setUpProfessor("profInfo.txt");
		ArrayList<Professor> professors = fr.getProfessorsInfo();
		fr.setUpCourse("courseInfo.txt");
		ArrayList<Course> courses = fr.getCoursesInfo();
		Professor f = Professor.getProfessorById("029", professors);
		f.setCoursesTeach(f.getName(),courses);
		assertEquals(f.getGivenCourses().get(0).getCourseID(),"CIT590");
	}

}
