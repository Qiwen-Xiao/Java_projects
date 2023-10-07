package courses;


/**
 * This class defines a single course
 * @author Qiwen Xiao 39823555 &Siyuan Hong 23793670
 */

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import files.FileInfoReader;
import roles.Professor;
import roles.Student;


public class Course {
    /**
     * ID of the Course
     */
    private String courseID;
    /**
     * Name of the course
     */
    private String courseName;
    /**
     * Lecturer of the course
     */
    private String courseLecturer;
    /**
     * Date of the course
     */
    private String courseDate;
    /**
     * Start time of the course
     */
    private String startTime;
    /**
     * End time of the course
     */
    private String endTime;
    /**
     * Capacity of the course
     */
    private int capacity;
    /**
     * Student that take this course
     */
    private ArrayList<Student> studentInTheCourse=new ArrayList<>();
    /**
     * Constructor
     */
    public Course(String courseID, String courseName, String courseLecturer, String courseDate,
                  String startTime, String endTime, int capacity) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseLecturer = courseLecturer;
        this.courseDate = courseDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    };
    // getters
    /**
     * @return the courseID
     */
    public String getCourseID() {
        return courseID;
    }
    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }
    /**
     * @return the lecturer
     */
    public String getLecturer() {
        return courseLecturer;
    }
    /**
     * @return the date
     */
    public String getDate() {
        return courseDate;
    }
    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }
    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }
    /**
     * @return the studentList
     */
    public ArrayList<Student> getStudentList() {
        return this.studentInTheCourse;
    }
    /**
     * Check if the course has time conflict with any course in a arrayList
     * @param givenCourse
     * @param courseList
     * @return true if has time conflict,else return false
     * @throws ParseException
     */
    public boolean checkCourseTimeConflict(Course givenCourse,ArrayList<Course> courseList) throws ParseException {
        //record the conflict status,default to false
        boolean flag =false;
        SimpleDateFormat s=new SimpleDateFormat("hh:mm");
        Date thisCourseStart=null;
        Date thisCourseEnd=null;
        Date givenCourseStart=s.parse(givenCourse.getStartTime());
        Date givenCourseEnd=s.parse(givenCourse.getEndTime());
        for(Course c: courseList) {
            thisCourseStart=s.parse(c.getStartTime());
            thisCourseEnd=s.parse(c.getEndTime());
            //Check if the given course and this course has same day of class
            //if do not have the same day of class,return false
            if(c.getDate().contains(givenCourse.getDate())==false && givenCourse.getDate().contains(c.getDate())==false) {
                continue;
            }
            //if have the same day of class,check start and end time
            else {
                //if the end time of this course is before the start time of given course
                //or the start time of this course is after the end time of give course, do not conflict
                if(thisCourseEnd.before(givenCourseStart)|| thisCourseStart.after(givenCourseEnd)) {
                    continue;
                }
                else {
                    System.out.println("The course you selected has time conflict with " + c.getCourseID() + c.getCourseName());
                    flag=true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * add a student
     */
    public void addAStudent(Student student) {
        this.studentInTheCourse.add(student);
    }
    /**
     * remove a student
     */
    public void removeAStudent(Student student) {
        this.studentInTheCourse.remove(student);
    }

    public static Course getCourseById(String id, ArrayList<Course> courses) {
        //iterate over courses
        for (Course c: courses) {
            //if this name equals given name
            if (c.getCourseID().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Student> setAndGetStudentInTheCourse(String cID, ArrayList<Student> students) {
        cID = cID.trim();
        ArrayList<Student> studentsInCourse = new ArrayList<>(0);
        for(int i = 0; i < students.size(); i++)
        {
            if(students.get(i).getCoursesTake().contains(cID))
            {
                studentsInCourse.add(students.get(i));
                this.studentInTheCourse = studentsInCourse;
            }
        }
        return studentsInCourse;
    }
        /**
         * check if this course is full
         * @return true if it is not full
         */
        public boolean checkCapacity() {
            //check size of the arrayList of students who take this course
            if (this.studentInTheCourse.size()<this.capacity) {
                return true;
            } else {
                return false;
            }
        }

    public ArrayList<Student> getStudentInCourse() {
        return this.studentInTheCourse;
    }
    /**
     * check if this course exists with given ID is in system
     * @param newCourseId
     * @param courses arrayList of all courses
     * @return true if the course exists
     */
    public static boolean checkExistById (String newCourseId,ArrayList<Course> courses) {
        //iterate over courses
        for(Course c: courses) {
            //if this id equals given id
            if(c.getCourseID().equals(newCourseId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    /**
     * Override toString method
     */
    public String toString() {
        return (courseID+"|"+courseName+", "+startTime+"-"+endTime+" on "+courseDate+", with course capacity: "+capacity
                +", student: "+studentInTheCourse.size()+", lecturer: Professor "+courseLecturer);
    }

}