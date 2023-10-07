package roles;

/**
 * this class represent a student
 * @author Qiwen Xiao 39823555 &Siyuan Hong 23793670
 */


import courses.Course;
import files.FileInfoReader;


import java.util.ArrayList;

import java.io.File;
import java.util.HashMap;

public class Student extends User{
    //student store all student info
    private ArrayList<String> coursesTake = new ArrayList<>();

    private HashMap<String, String> CoursesTakenAndGrade = new HashMap<>();

    public HashMap<String,String> getCoursesTakenAndGrade(){
        return this.CoursesTakenAndGrade;
    }
    public void setCoursesTakenAndGrade(HashMap<String,String> h){
        this.CoursesTakenAndGrade = h;
    }
    /**
     * Constructor
     * create a new student
     * @param Name
     * @param Id
     * @param PassWord
     * @param UserName
     */
    public Student(String Name,String Id,String PassWord,String UserName) {
        super(Name, Id, PassWord, UserName);
    }

    public static Student getStudentByName (String name,ArrayList<Student> students) {

        //iterate over students
        for (Student student: students) {
            //if this name equals given name
            if (student.getName().equals(name)) {
                return  student;
            }
        }
        return null;
    }
    /**
     * get a student with given name
     * @param id
     * @return this student
     */
    public static Student getStudentById(String id, ArrayList<Student> students) {
        //iterate over students
        for (Student student: students) {
            //if this name equals given name
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public static Student getStudentByUserName (String username,ArrayList<Student> students) {
        Student s = null;
        //iterate over students
        for (Student student: students) {
            //if this name equals given name
            if (student.getUserName().equals(username)) {
                s = student;
            }
        }

        return s;

    }
    /**
     * check if this student exists with given username is in system
     * @param newStudentUsrename
     * @param students arrrayList of all students
     * @return true if he exists
     */
    public static boolean checkExistByUsername (String newStudentUsrename,ArrayList<Student> students) {
        //iterate over students
        for(Student s: students) {
            //if this id equals given id
            if(s.getUserName().equals(newStudentUsrename)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if this professor exists with given ID is in system
     * @param newStudentId
     * @param students arrrayList of all students
     * @return true if he exists
     */
    public static boolean checkExistById (String newStudentId,ArrayList<Student> students) {
        //iterate over students
        for(Student s: students) {
            //if this id equals given id
            if(s.getId().equals(newStudentId)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Add a new course to all the course that student learn
     */
    public void addNewCourse(String c) {
        this.coursesTake.add(c);
    }

    public void dropCourse(String c){
        coursesTake.remove(c);
    }

    public ArrayList<String> getCoursesTake(){
        return coursesTake;
    }
    @Override
    public String toString() {
        return getId()+getPassword()+getCoursesTake().get(0);
    }

}