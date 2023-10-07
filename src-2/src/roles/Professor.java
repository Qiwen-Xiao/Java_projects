package roles;

/**
 * this class represent a professor
 * @author Qiwen Xiao 39823555 &Siyuan Hong 23793670
 */

import java.util.ArrayList;
import files.FileInfoReader;
import courses.Course;

public class Professor extends User{
    //every professor has an arrayList of all the courses he teach
    private ArrayList<Course> courseTeach = new ArrayList<>();


    /**
     * Constructor
     * create a new professor
     * @param Name
     * @param Id
     * @param PassWord
     * @param UserName
     */
    public Professor(String Name,String Id,String PassWord,String UserName) {
        super(Name, Id, PassWord, UserName);
    }
    /**
     * get a professor with given name
     * @param name
     * @return this professor
     */
    public static Professor getProfessorByName (String name,ArrayList<Professor> professors) {

        //iterate over professors
        for (Professor professor: professors) {
            //if this name equals given name
            if (professor.getName().equals(name)) {
                return professor;
            }
        }
        return null;
    }
    /**
     * get a professor with given username
     * @param username given username
     * @return
     */
    public static Professor getProfessorByUsername(String username,ArrayList<Professor> professors) {
        //iterate over professors
        for(Professor professor:professors) {
            //if this username equals given username
            if(professor.getUserName().equals(username)) {
                return professor;
            }
        }
        return null;
    }
    /**
     * get a professor with given name
     * @param id
     * @return this professor
     */
    public static Professor getProfessorById(String id, ArrayList<Professor> professors) {
        //iterate over professors
        for (Professor p: professors) {
            //if this name equals given name
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    public void setCoursesTeach(String pName,ArrayList<Course> courses) {
        pName = pName.trim();
        ArrayList<Course> coursesTeach = new ArrayList<>(0);
        for(int i = 0; i < courses.size(); i++)
        {
            if(courses.get(i).getLecturer().equals(pName))
            {
                coursesTeach.add(Course.getCourseById(courses.get(i).getCourseID(), courses));
                this.courseTeach = coursesTeach;
            }
        }
    }

    /**
     * check if this professor exists with given ID is in system
     * @param newProfId
     * @param professors arrrayList of all professors
     * @return true if he exists
     */
    public static boolean checkExistById (String newProfId,ArrayList<Professor> professors) {
        //iterate over professors
        for(Professor p: professors) {
            //if this id equals given id
            if(p.getId().equals(newProfId)) {
                return true;
            }
        }
        return false;
    }
    /**
     * check if this professor exists with given username is in system
     * @param newProfUsrename
     * @param professors
     * @return
     */
    public static boolean checkExistByUsername (String newProfUsrename,ArrayList<Professor> professors) {
        //iterate over professors
        for(Professor p: professors) {
            //if this id equals given id
            if(p.getUserName().equals(newProfUsrename)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Add a new course to all the course that the professor teach
     */
    public void addNewCourse(Course course) {
        courseTeach.add(course);
    }
    
//getters
    /**
     * return this professor's given courses
     * @return
     */
    public ArrayList<Course> getGivenCourses() {
        return this.courseTeach;
    }

   
    @Override
    public String toString(){
        return getId()+getName()+getUserName();
    }

}