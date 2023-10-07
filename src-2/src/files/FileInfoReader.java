package files;
/**
 *  read and parse the files
 *  Creates four ArrayLists for storing the information in those data files.
 *  @author Qiwen Xiao &Siyuan Hong
 */

import courses.*;
import roles.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileInfoReader {

    /**
     * arrayList of admin information.
     */
    private ArrayList<Admin> adminInfo = new ArrayList<Admin>();
    /**
     * arrayList of course information.
     */
    private ArrayList<Course> courseInfo = new ArrayList<Course>();
    /**
     * arrayList of professor information.
     */
    private ArrayList<Professor> professorInfo = new ArrayList<Professor>();
    /**
     * arrayList of student information.
     */
    private ArrayList<Student> studentInfo = new ArrayList<Student>();
    /**
     * Load and parses the admin info file
     * @param Admin is adminInfo file
     */
    public void setUpAdmin(String Admin){
        //load Admin data
        try {
            //create file object
            File myFile = new File(Admin);
            //create fileReader
            FileReader fileReader = new FileReader(myFile);
            //create buffereRreader
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //Iterate over every line of given file
            while (true) {
                //every line is a user
                String adminLine = bufferedReader.readLine();
                if (adminLine == null) {
                    //when the line has no info, leave the iteration
                    break;
                }
                //store every line in an array
                String[] singleAdminInfo = adminLine.trim().split(";");
                //construct a new user with the info in the array
                Admin newAdmin = new Admin(singleAdminInfo[1].trim(), singleAdminInfo[0].trim(), singleAdminInfo[3].trim(), singleAdminInfo[2].trim());
                //add the new user to it's info arrayList
                adminInfo.add(newAdmin);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and parses the professor info file
     * @param Professor
     */
    public void setUpProfessor(String Professor) {
        //load professor data
        try {
            //create file object
            File myFile = new File(Professor);
            //create fileReader
            FileReader fileReader = new FileReader(myFile);
            //create bufferReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //Iterate over every line of given file
            while (true) {
                //every line is a user
                String professorLine = bufferedReader.readLine();
                if (professorLine == null) {
                    //when the line has no info, leave the iteration
                    break;
                }
                //store every line in an array
                String[] singleProfessorInfo = professorLine.trim().split(";");
                Professor newProfessor = new Professor(singleProfessorInfo[0], singleProfessorInfo[1].trim(), singleProfessorInfo[3].trim(), singleProfessorInfo[2].trim());
                professorInfo.add(newProfessor);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Load and parses the student info file
     * @param Student
     */
    public void setUpStudent(String Student) {
        //load student data
        try {
            //create file object
            File myFile = new File(Student);
            //create fileReader
            FileReader fileReader = new FileReader(myFile);
            //create bufferReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //Iterate over every line of given file
            while (true) {
                //every line is a user
                String studentLine = bufferedReader.readLine();
                if (studentLine == null) {
                    //when the line has no info, leave the iteration
                    break;
                }
                //store every line in an array
                String[] singleStudentInfo = studentLine.trim().split(";");
                Student newStudent = new Student(singleStudentInfo[1].trim(), singleStudentInfo[0].trim(), singleStudentInfo[3].trim(), singleStudentInfo[2].trim());

                //course list
                String cs = singleStudentInfo[4];
                if(cs.length() != 0){
                    //split to course and its grade
                    String[] courses = cs.split(",");
                    for(int i =0;i<courses.length;i++){
                        String[] courseL = courses[i].trim().split(":");
                        newStudent.addNewCourse(courseL[0].trim());
                        String grade = courseL[1].trim();
                    }
                }
                //create a new hashmap for each student
                HashMap<String, String> CoursesTaken = new HashMap<String, String>();
                //split the courses by ","
                String[] CourseWithGrade = singleStudentInfo[4].split(", ");
                //check if there is any courses
                if (CourseWithGrade.length>0) {
                    //iterate over each course
                    for (String c:CourseWithGrade) {
                        //use courseId to find this course
                        String thisCourse = c.split(": ")[0].trim();
                        //get the grade for this course
                        String CourseGrade = c.split(": ")[1].trim();
                        //put this course and grade into CoursesTaken
                        CoursesTaken.put(thisCourse, CourseGrade);
                    }
                }
                newStudent.setCoursesTakenAndGrade(CoursesTaken);
                studentInfo.add(newStudent);

            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Load and parses the course info file
     * @param Course
     */
    public void setUpCourse(String Course) {
        //load courses data
        try {
            //create file object
            File myFile = new File(Course);
            //create fileReader
            FileReader fileReader = new FileReader(myFile);
            //create bufferReader
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //Iterate over every line of given file
            while (true) {
                //every line is a course
                String courseLine = bufferedReader.readLine();
                if (courseLine == null) {
                    //when the line has no info, leave the iteration
                    break;
                }
                //store every line in an array
                String[] singleCourseInfo = courseLine.trim().split(";");
                //store the course capacity
                int newCourseCapacity=Integer.parseInt(singleCourseInfo[6].trim());
                //construct a new course with the info in the array
                Course newCourse = new Course(singleCourseInfo[0].trim(), singleCourseInfo[1].trim(), singleCourseInfo[2].trim(), singleCourseInfo[3].trim(), singleCourseInfo[4].trim(), singleCourseInfo[5].trim(), newCourseCapacity);
                //add the new course to course Info arrayList
                courseInfo.add(newCourse);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get Admins information.
     * @return list of Admins
     */
    public ArrayList<Admin> getAdminsInfo(){
        return this.adminInfo;
    }
    /**
     * Get course information.
     * @return list of Course
     */
    public ArrayList<Course> getCoursesInfo(){
        return this.courseInfo;
    }
    /**
     * Get professors information.
     * @return list of professors
     */
    public ArrayList<Professor> getProfessorsInfo(){
        return this.professorInfo;
    }
    /**
     * Get student information.
     * @return list of student
     */
    public ArrayList<Student> getStudentsInfo(){
        return this.studentInfo;
    }

}