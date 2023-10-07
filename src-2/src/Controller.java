import courses.Course;
import files.FileInfoReader;
import roles.Admin;
import roles.Professor;
import roles.Student;

import java.text.ParseException;
import java.util.*;

/**
 *  @author Qiwen Xiao 39823555 &Siyuan Hong 23793670
 */

public class Controller {

    public static void main(String[] args) throws ParseException {
        FileInfoReader fr = new FileInfoReader();
        fr.setUpStudent("studentInfo.txt");
        ArrayList<Student> students = fr.getStudentsInfo();
        fr.setUpAdmin("adminInfo.txt");
        ArrayList<Admin> admins = fr.getAdminsInfo();
        fr.setUpProfessor("profInfo.txt");
        ArrayList<Professor> professors = fr.getProfessorsInfo();
        fr.setUpCourse("courseInfo.txt");
        ArrayList<Course> courses = fr.getCoursesInfo();



        oo:
        while (true) {
            boolean returnToPrevious2 = true;
            while (returnToPrevious2) {
                System.out.println("--------------------------------");
                System.out.println("Student Management System");
                System.out.println("--------------------------------");
                System.out.println("1 -- Login as a student");
                System.out.println("2 -- Login as a professor");
                System.out.println("3 -- Login as an admin");
                System.out.println("4 -- Quit the system");
                System.out.println();
                System.out.println("Please enter your option: eg.\'1\'.");
                Scanner scan = new Scanner(System.in);
                Integer input = scan.nextInt();
                String password;
                String username;

                mm:
                if (input == 1) {
                    System.out.println("Please enter your user name, or type \'q\' to quit");
                    username = scan.next().trim();
                    if (username.equals("q")) {
                        continue;
                    }
                    System.out.println("Please enter your user password, or type \'q\' to quit");
                    password = scan.next().trim();
                    if (password.equals("q")) {
                        continue;
                    }
                    Student s = Student.getStudentByUserName(username, students);
                    //hash map check
                    System.out.println(s.getCoursesTakenAndGrade());
                    boolean returnToPrevious3 = true;
                    while (returnToPrevious3) {
                        if (password.equals(s.getPassword())) {
                            System.out.println("--------------------------------");
                            System.out.println("Welcome, " + s.getName());
                            System.out.println("--------------------------------");
                            System.out.println("1 -- View all courses");
                            System.out.println("2 -- Add courses to your list");
                            System.out.println("3 -- View enrolled courses");
                            System.out.println("4 -- Drop courses in your list");
                            System.out.println("5 -- View grades");
                            System.out.println("6 -- Return to previous menu");
                            System.out.println();
                            System.out.println("Please enter your option: eg.\"1\".");
                            int choice = scan.nextInt();
                            if (choice == 1) {
                                for (Course c : courses) {
                                    c.setAndGetStudentInTheCourse(c.getCourseID(), students);
                                    System.out.println(c);
                                }

                            } else if (choice == 2) {
                                boolean addCourse = true;
                                while (addCourse) {
                                    //show all courses the student s take
                                    System.out.println("The courses in your list:");
                                    ArrayList<String> coursesTake = s.getCoursesTake();
                                    for (String course : coursesTake) {
                                        Course c = Course.getCourseById(course, courses);
                                        System.out.println(c);
                                    }
                                    System.out.println("Please select the course ID you want to add to your list: eg.\"CIT590\".");
                                    System.out.println("Or enter 'q' to return to previous menu.");
                                    String cID = scan.next();
                                    if (!cID.equals("q")) {
                                        if (coursesTake.contains(cID)) {
                                            System.out.println("The course you selected is already in your list");
                                        } else {
                                            //get course of cID
                                            Course course1 = Course.getCourseById(cID, courses);
                                            ArrayList<Course> courseList = new ArrayList<>();
                                            //get the list of courses the student take: <String> to <Course>
                                            for (String c : coursesTake) {
                                                Course co = Course.getCourseById(c, courses);
                                                courseList.add(co);
                                            }
                                            //check if the time has conflicted
                                            boolean t = course1.checkCourseTimeConflict(course1, courseList);
                                            //if the course's time don't conflict with other courses time,add
                                            if (!t) {
                                                //add new course to student course list
                                                s.addNewCourse(cID);
                                                //add new student who took this course
                                                course1.addAStudent(s);
                                                System.out.println("course added succesfully");
                                                System.out.println(s.getCoursesTake());
                                            }
                                        }
                                    } else if (cID.equals("q")) {
                                        addCourse = false;
                                    }
                                }


                            } else if (choice == 3) {
                                System.out.println("The courses in your list:");
                                ArrayList<String> coursesTake = s.getCoursesTake();
                                if (coursesTake.size() != 0) {
                                    for (String course : coursesTake) {
                                        Course c = Course.getCourseById(course, courses);
                                        System.out.println(c);
                                    }
                                }


                            } else if (choice == 4) {
                                boolean dropCourse = true;
                                while (dropCourse) {
                                    System.out.println("The courses in your list:");
                                    ArrayList<String> coursesTake = s.getCoursesTake();
                                    for (String course : coursesTake) {
                                        Course c = Course.getCourseById(course, courses);
                                        System.out.println(c);
                                    }

                                    System.out.println("Please enter the course ID which you want to drop: eg.\"CIT591\".");
                                    System.out.println("Or enter 'q' to return to previous menu.");
                                    String cID = scan.next();
                                    if (!cID.equals("q")) {
                                        if (!s.getCoursesTake().contains(cID)) {
                                            System.out.println("The course isn't in your list");
                                        } else {
                                            Course course1 = Course.getCourseById(cID, courses);
                                            //remove this course of student course list
                                            s.dropCourse(cID);
                                            //remove this student from the list who took this course
                                            course1.removeAStudent(s);
                                            System.out.println("course dropped succesfully");
                                        }
                                    } else {
                                        dropCourse = false;
                                    }
                                }

                            } else if (choice == 5) {
                                System.out.println("Here are the courses you already taken, with your grade in a letter format");
                                HashMap<String, String> courseAndrade = s.getCoursesTakenAndGrade();
                                Set<String> kc = courseAndrade.keySet();
                                for (Iterator<String> it = kc.iterator(); it.hasNext(); ) {
                                    String c = it.next();
                                    Course co1 = Course.getCourseById(c, courses);
                                    String grade = courseAndrade.get(c);
                                    System.out.println("Grade of " + co1.getCourseID() + co1.getCourseName() + ":" + grade);
                                }
                            } else if (choice == 6) {
                                returnToPrevious3 = false;
                            }
                        } else {
                            break mm;
                        }

                    }
                }
                //login as professor
                else if (input == 2) {
                    zz:
                    while (true) {
                        System.out.println("Please enter your user name, or type \'q\' to quit");
                        username = scan.next();
                        if (username.equals("q")) {
                            break zz;
                        }
                        System.out.println("Please enter your user password, or type \'q\' to quit");
                        password = scan.next();
                        if (password.equals("q")) {
                            break zz;
                        }
                        Professor f = Professor.getProfessorByUsername(username, professors);
                        boolean returnToPrevious3 = true;
                        while (returnToPrevious3) {
                            if (password.equals(f.getPassword())) {
                                System.out.println("--------------------------------");
                                System.out.println("Welcome, " + f.getName());
                                System.out.println("--------------------------------");
                                System.out.println("1 -- View given courses");
                                System.out.println("2 -- View student list of the given course");
                                System.out.println("3 -- Return to previous menu");
                                System.out.println();
                                System.out.println("Please enter your option: eg.\"1\".");
                                int choice = scan.nextInt();
                                if (choice == 1) {
                                    System.out.println("----------The course list----------");
                                    f.setCoursesTeach(f.getName(), courses);
                                    for (Course c : f.getGivenCourses()) {
                                        System.out.println(c);
                                    }

                                } else if (choice == 2) {
                                    System.out.println("----------The course list----------");
                                    f.setCoursesTeach(f.getName(), courses);
                                    for (Course c : f.getGivenCourses()) {
                                        System.out.println(c);
                                    }

                                    System.out.println("Please enter the course ID: eg.\"CIT519\".");
                                    System.out.println("Or enter 'q' to quit.");
                                    String cID = scan.next();
                                    if (cID.equals("q")) {
                                        continue;
                                    } else {
                                        System.out.println("Students in your course " + cID + ":");
                                        Course c = Course.getCourseById(cID, courses);
                                        c.setAndGetStudentInTheCourse(cID, students);
                                        ArrayList<Student> slist = c.getStudentList();
                                        for (Student s : slist) {
                                            System.out.println(s.getId() + " " + s.getName());
                                        }
                                    }


                                } else if (choice == 3) {
                                    break zz;

                                }
                            } else {
                                returnToPrevious3 = false;
                            }
                        }

                    }
                } else if (input == 3) {
                    yy:
                    while (true) {
                        System.out.println("Please enter your user name, or type \'q\' to quit");
                        username = scan.next().trim();
                        if (username.equals("q")) {
                            break yy;
                        }
                        System.out.println("Please enter your user password, or type \'q\' to quit");
                        password = scan.next().trim();
                        if (password.equals("q")) {
                            break yy;
                        }
                        Admin a = Admin.getAdminByUsername(username, admins);

                        boolean returnToPrevious3 = true;
                        ff:
                        while (returnToPrevious3) {
                            if (password.equals(a.getPassword())) {
                                System.out.println("--------------------------------");
                                System.out.println("Welcome, " + a.getName());
                                System.out.println("--------------------------------");
                                System.out.println("1 -- View all courses");
                                System.out.println("2 -- Add new courses");
                                System.out.println("3 -- Delete courses");
                                System.out.println("4 -- Add new professor");
                                System.out.println("5 -- Delete professor");
                                System.out.println("6 -- Add new students");
                                System.out.println("7 -- Delete student");
                                System.out.println("8 -- Return to previous menu");
                                System.out.println();
                                System.out.println("Please enter your option: eg.\"1\".");
                                int choice = scan.nextInt();
                                if (choice == 1) {
                                    for (Course c : courses) {
                                        c.setAndGetStudentInTheCourse(c.getCourseID(), students);
                                        System.out.println(c);
                                    }
                                    //admin want to add a course
                                } else if (choice == 2) {
                                    System.out.println("Please enter the course ID, or type 'q' to end.");
                                    String cID = scan.next();
                                    if (cID.equals("q")) {
                                        continue;
                                    }
                                    //check if the course already exist

                                    while (Course.checkExistById(cID, courses)) {
                                        System.out.println("The course already exist");
                                        System.out.println("Please enter the course ID, or type 'q' to end.");
                                        cID = scan.next();
                                        if (cID.equals("q")) {
                                            continue ff;
                                        }
                                    }
                                    System.out.println("Please enter the course name, or type 'q' to end.");
                                    String cName = scan.next();
                                    if (cName.equals("q")) {
                                        continue;
                                    }
                                    System.out.println("Please enter the course start time, or type 'q' to end. eg. '19:00'");
                                    String st = scan.next();
                                    if (st.equals("q")) {
                                        continue;
                                    }
                                    System.out.println("Please enter the course end time, or type 'q' to end. eg. '20:00'");
                                    String et = scan.next();
                                    if (et.equals("q")) {
                                        continue;
                                    }
                                    System.out.println("Please enter the course date, or type 'q' to end. eg. 'MW'");
                                    String date = scan.next();
                                    if (date.equals("q")) {
                                        continue;
                                    }
                                    System.out.println("Please enter the course capacity, or type 'q' to end. eg. '72'");
                                    String ca = scan.next();
                                    if (ca.equals("q")) {
                                        continue;
                                    }
                                    int c = Integer.parseInt(ca);
                                    System.out.println("Please enter the course lecturer'id, or type 'q' to end. eg. '001'");
                                    String lID = scan.next();
                                    if (lID.equals("q")) {
                                        continue;
                                    }
                                    Professor newProfessor;
                                    //Check if the professor already exist
                                    if (!Professor.checkExistById(lID, professors)) {
                                        System.out.println("This professor isn't in the system, please add this professor first");
                                        System.out.println("Please enter the professor's ID, or type \'q\' to quit");
                                        String pID = scan.next();
                                        if (pID.equals("q")) {
                                            continue ff;
                                        }
                                        System.out.println("Please enter the professor's name, or type 'q' to end.");
                                        String pName = scan.next();
                                        if (pName.equals("q")) {
                                            continue ff;
                                        }
                                        System.out.println("Please enter a username");
                                        String pUsername = scan.next();
                                        System.out.println("Please enter a password");
                                        String pPass = scan.next();
                                        newProfessor = new Professor(pName, pID, pPass, pUsername);
                                        professors.add(newProfessor);
                                        System.out.println("Succesfully added the new professor: " + pID + pName);
                                    }else{
                                        newProfessor = Professor.getProfessorById(lID, professors);
                                    }
                                    Course co1 = new Course(cID, cName, newProfessor.getName(), date, st, et, c);
                                    boolean tf = co1.checkCourseTimeConflict(co1, newProfessor.getGivenCourses());
                                    if (!tf) {
                                        Admin.addNewCourse(co1, professors, courses);
                                    } else {
                                        System.out.println("Unable to add new course: " + co1);
                                    }

                                    //admin want to delete a course
                                } else if (choice == 3) {
                                    System.out.println("Please enter the course ID, or type 'q' to end.");
                                    String cID1 = scan.next();
                                    if (cID1.equals("q")) {
                                        continue;
                                    }
                                    //keep ask for the course id, if it is not in the course list
                                    while (!Course.checkExistById(cID1, courses)) {
                                        System.out.println("The course ID you type in is not in the list, please type again.");
                                        cID1 = scan.next();
                                    }
                                    Admin.deleteCourse(cID1, courses, students, professors);
                                    //admin want to add a new professor
                                } else if (choice == 4) {
                                    System.out.println("Please enter the professor's ID, or type \'q\' to quit");
                                    String pID = scan.next();
                                    if (pID.equals("q")) {
                                        continue;
                                    }
                                    //keep ask for the professor id, if it is in the professor list
                                    while (Professor.checkExistById(pID, professors)) {
                                        System.out.println("The professor ID you type in is in the list, please type again.");
                                        pID = scan.next();
                                    }
                                    System.out.println("Please enter the professor's name, or type 'q' to end.");
                                    String pName = scan.next();
                                    if (pName.equals("q")) {
                                        continue;
                                    }
                                    System.out.println("Please enter a username");
                                    String pUsername = scan.next();
                                    //keep ask for the professor username, if it is  in the professor list
                                    while (Professor.checkExistByUsername(pUsername, professors)) {
                                        System.out.println("The professor username you type in is in the list, please type again.");
                                        pUsername = scan.next();
                                    }
                                    System.out.println("Please enter a password");
                                    String pPassword = scan.next();
                                    Admin.addNewProfessor(pName, pID, pUsername, pPassword, professors);

                                    //admin want to delete a professor
                                } else if (choice == 5) {
                                    System.out.println("Please enter the professor's ID, or type 'q' to quit");
                                    String pID = scan.next();
                                    if (pID.equals("q")) {
                                        continue;
                                    }
                                    //keep ask for the professor id, if it is not in the course list
                                    while (!Professor.checkExistById(pID, professors)) {
                                        pID = scan.next();
                                        System.out.println("The professor ID you type in is not in the list, please type again.");
                                    }
                                    Admin.deleteProfessor(pID, professors);

                                    //admin want to add a new student
                                } else if (choice == 6) {
                                    System.out.println("Please enter the student's ID, or type 'q' to quit");
                                    String sID = scan.next();
                                    if (sID.equals("q")) {
                                        continue;
                                    }
                                    //keep ask for the student id, if it is in the student list
                                    while (Student.checkExistById(sID, students)) {
                                        System.out.println("The student ID you type in is in the list, please type again.");
                                        sID = scan.next();
                                    }
                                    System.out.println("Please enter the student's name, or type 'q' to end.");
                                    String sName = scan.next();
                                    if (sName.equals("q")) {
                                        continue;
                                    }
                                    System.out.println("Please enter a username");
                                    String sUsername = scan.next();
                                    //keep ask for the student username, if it is in the student list
                                    while (Student.checkExistByUsername(sUsername, students)) {
                                        System.out.println("The student username you type in is in the list, please type again.");
                                        sUsername = scan.next();
                                    }
                                    System.out.println("Please enter a password");
                                    String sPassword = scan.next();
                                    boolean ac = true;
                                    Student newStudent = new Student(sName, sID, sPassword, sUsername);
                                    dd:
                                    while (ac) {
                                        System.out.println("Please enter ID of a course which this student already took,one in a time");
                                        System.out.println("Type 'q' to quit, type 'n' to stop adding.");
                                        String sCourseId = scan.next();
                                        if (sCourseId.equals("q")) {
                                            System.out.println("The student didn't be added");
                                            continue ff;
                                        }
                                        if (sCourseId.equals("n")) {
                                            students.add(newStudent);
                                            System.out.println("Successfully added the new student: "+newStudent.getId()+" "+newStudent.getName()+newStudent.getCoursesTakenAndGrade());
                                            break dd;
                                        }
                                        System.out.println("Please enter the grade, eg. 'A'");
                                        String sGrade = scan.next();
                                        //set the new student grade
                                        Admin.addNewStudentGrade(newStudent, sCourseId, sGrade);
                                    }


                                    //admin want to delete a student
                                } else if (choice == 7) {
                                    System.out.println("Please enter the student's ID, or type \'q\' to quit");
                                    //keep ask for the student id, if it is not in the student list
                                    String sID = scan.next();
                                    if (sID.equals("q")) {
                                        continue;
                                    }
                                    while (!Student.checkExistById(sID, students)) {
                                        System.out.println("The student ID you type in is not in the list, please type again.");
                                        sID = scan.next();
                                    }
                                    Admin.deleteStudent(sID, students, courses);
                                    //admin want to return to previous menu
                                } else if (choice == 8) {
                                    break yy;
                                }
                            } else {
                                break ff;
                            }
                        }
                    }
                }
            else if (input == 4) {
                returnToPrevious2 = false;
            }
            }

        }
    }

}

