package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    @GetMapping
    public ResponseEntity<List<Student>> getStudentInfo() {
        List<Student> foundStudent = studentService.findAll();
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student foundStudent = studentService.findStudent(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.createStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sort")
    public List<Student> getStudentSortedList(@RequestParam(value = "age") int age) {
        return studentService.sortStudentByAge(age);
    }

    @GetMapping("/sortBy")
    public List<Student> getStudentSortedInAgeRangeList(@RequestParam(value = "min") int min,
                                                        @RequestParam(value = "max") int max) {
        return studentService.findByAgeBetween(min, max);
    }
    @GetMapping("/{id}/faculty")
    public Faculty getFacultyByStudent(@PathVariable long id){
        return studentService.getFacultyByStudent(id);
    }
    @GetMapping("/overall")
    public Long overallStudentAmount(){
        return studentService.overallStudentAmount();
    }
    @GetMapping("/averagestudentage")
    public Long getAverageStudentAge(){
        return studentService.getAverageStudentAge();
    }
    @GetMapping("/lastfivestudents")
    public List<Student> getLastFiveStudentsByID() {
        return studentService.getLastFiveStudentsByID();
    }
}
