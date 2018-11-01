package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

   /* @RequestMapping("/home")
    public String home(){
        return "home";
    }*/

   // Home page
    @RequestMapping("/")
    public String Home(){

        return "home";
    }
    // add a student to the database
    @RequestMapping("/addstudent")
    public String addStudent(Model model){
        model.addAttribute("student",new Student());
        return "studentform";
    }

    // save the student
    @PostMapping("/addstudent")
    public String saveStudent(@ModelAttribute("student") Student student, Course course, BindingResult result){
        if(result.hasErrors()){
            return "studentform";
        }
        student.addCourse(course);
        studentRepository.save(student);
        return "home";
    }

    // add a course
    @RequestMapping("/addcourse")
    public String addCourse(Model model){
        model.addAttribute("course", new Course());
        return "courseform";
    }

    //save the course to database
    @PostMapping("/addcourse")
    public String saveCourse(@ModelAttribute("course") Course course, Student stud,BindingResult result){

        if(result.hasErrors()){
            return "courseform";
        }
        course.addStudent(stud);

        courseRepository.save(course);
        return "home";
    }

    // display all students and courses
    @RequestMapping("/showstudent")
    public String showStudent(Model model){
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("courses",courseRepository.findAll());

        return "list";
    }

    @RequestMapping("/showcourse")
    public String listCourse(Model model){
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("courses",courseRepository.findAll());

        return "listcourse";

    }

    //


    @RequestMapping("/home")
    public String index(Model model){

        // create a student
        Student student1 = new Student();
        student1.setName("Melak");
        student1.setEmail("sememew@gmail.com");
        student1.setMajor("Engineering");
        student1.setAge(25);
        studentRepository.save(student1);

        Student student2 = new Student();
        student2.setName("Genzeb");
        student2.setEmail("genzeb@gmail.com");
        student2.setMajor("Maths");
        student2.setAge(30);
        studentRepository.save(student2);

        // create a set that holds list of students
        Set<Student> students=new HashSet<>();
        students.add(student1);
        students.add(student2);

        // create a course
        Course course1=new Course();
        course1.setName("Java Programming");
        course1.setCode("1002");
      //  course1.setStudents(students);
        course1.addStudent(student1);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Bootstrap");
        course2.setCode("2360");
        course2.addStudent(student2);
        course2.addStudent(student1);
       courseRepository.save(course2);

        // create set that holds list of courses
        Set<Course> courses=new HashSet<>();
        courses.add(course1);
        courses.add(course2);

        // add list of courses to each student
       // student1.setCourses(courses);
        student1.addCourse(course1);
        student2.addCourse(course2);
        student2.addCourse(course1);
       // student2.setCourses(courses);

        // bring all the student1 from the database and to the template
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("courses",courseRepository.findAll());

        return  "index";

    }
}
