package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/")
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
        course1.setStudents(students);
        courseRepository.save(course1);

        Course course2 = new Course();
        course2.setName("Bootstrap");
        course2.setCode("2360");
       courseRepository.save(course2);

        // create set that holds list of courses
        Set<Course> courses=new HashSet<>();
        courses.add(course1);
        courses.add(course2);

        // add list of courses to each student
        student1.setCourses(courses);
        student2.setCourses(courses);

        // bring all the student1 from the database and to the template
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("courses",courseRepository.findAll());

        return  "index";

    }
}
