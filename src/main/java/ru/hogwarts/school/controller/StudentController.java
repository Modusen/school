package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public Student editFaculty(@RequestBody Student student) {
        return studentService.editFStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteFaculty(@PathVariable long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/sort/{age}")
    public List<Student> getStudentSortedList(@PathVariable int age) {
        return studentService.sortStudentByAge(age);
    }
}
