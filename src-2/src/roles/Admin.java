package roles;

/**
 * @author Qiwen Xiao 39823555 &Siyuan Hong 23793670
 */


import java.util.ArrayList;
import java.util.HashMap;

import courses.Course;
import files.FileInfoReader;

public class Admin extends User{
    //admins store all admin info
    public static ArrayList<Admin> admins = new ArrayList<Admin>();
    /**
     * Constructor
     * create a new admin
     * @param Name
     * @param Id
     * @param PassWord
     * @param UserName
     */
    public Admin(String Name,String Id,String PassWord,String UserName) {
        super(Name, Id, PassWord, UserName);
        Admin.admins.add(this);
    }

    /**
     * Add a new course with some info
     */
    public static void addNewCourse(Course newCourse, ArrayList<Professor> professors,ArrayList<Course> courses) {
        //Store the name of professor
    	String pn = newCourse.getLecturer();
    	Professor p = Professor.getProfessorByName(pn,professors);
        String pName = p.getName();
        //create a new course
        courses.add(newCourse);
        p.addNewCourse(newCourse);
        

        //print a message
        System.out.println("Successfully added the course: "+newCourse);
    }
    /**
     * delete a course with given id
     * @param id
     */
    public static void deleteCourse(String id,ArrayList<Course> courses,ArrayList<Student> students,ArrayList<Professor> professors) {
        //get the course that needs to be deleted
        Course courseDeleted= Course.getCourseById(id,courses);
        //remove the course from the arrayList of every course info
        courses.remove(courseDeleted);
        //iterate over every students info
        for (Student s: students) {
            //if any student choose this given course
            if(s.getCoursesTake().contains(courseDeleted)) {
                //remove this course from student chosen course arrayList
                s.getCoursesTake().remove(courseDeleted);
            }
        }
        //iterate over every professors info
        for (Professor p: professors) {
            //if any professor teach this given course
            if (p.getGivenCourses().contains(courseDeleted)) {
                //remove this course from professor's given courses arrayList
                p.getGivenCourses().remove(courseDeleted);
            }
        }
        System.out.println("Successfully deleted the course: "+id);
    }
    /**
     * Add a new student with given information
     */
    public static void addNewStudentGrade(Student newStudent,String cID, String grade) {

        HashMap<String,String> courseTaken = new HashMap<>();
        courseTaken.put(cID,grade);
        newStudent.addNewCourse(cID);;
        newStudent.setCoursesTakenAndGrade(courseTaken);


    }
    /**
     * delete a student with given id
     * @param id
     */
    public static void deleteStudent(String id,ArrayList<Student> students,ArrayList<Course> courses) {
        //get the student that needs to be deleted
        Student studentDeleted= Student.getStudentById(id,students);
        //remove the student from the arrayList of every student info
        students.remove(studentDeleted);
        //delete the student in all the course he take
        for (Course c: courses) {
            //if any course has this student in its student list
            if(c.getStudentList().contains(studentDeleted)) {
                //remove that student from the student list
                c.getStudentList().remove(studentDeleted);
            }
        }

        System.out.println("Successfully deleted the student: "+id);
        System.out.println("Successfully deleted the student: "+id);
    }

    public static Admin getAdminByUsername(String usen, ArrayList<Admin> admins) {
        //iterate over professors
        for (Admin a: admins) {
            //if this name equals given name
            if (a.getUserName().equals(usen)) {
                return a;
            }
        }
        return null;
    }
    /**
     * Add a new professor with given information
     * @param profId
     * @param profName
     * @param userName
     * @param password
     */
    public static void addNewProfessor(String profName, String profId, String userName, String password,ArrayList<Professor> professors) {
        //create a new professor
        Professor newProfessor = new Professor(profName, profId, password, userName);
        professors.add(newProfessor);
        System.out.println("Successfully added the new professor: "+newProfessor.getId()+" "+newProfessor.getName());

    }
    /**
     * delete a professor
     */
    public static void deleteProfessor(String id,ArrayList<Professor> professors) {
        //get the professor that needs to be deleted
        Professor professorDeleted = Professor.getProfessorById(id,professors);
        //remove that professor
        professors.remove(professorDeleted);
        System.out.println("Successfully deleted the professor: "+id);
    }
}