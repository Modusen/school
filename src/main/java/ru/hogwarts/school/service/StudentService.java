package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        logger.warn("Запущен метод createStudent!!!");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.warn("Запущен метод findStudent!!!");
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.warn("Запущен метод editStudent!!!");
        Student foundStudent = studentRepository.findById(student.getId()).orElse(null);
        if (foundStudent != null) {
            return studentRepository.save(student);
        }
        return null;
    }

    public void deleteStudent(long id) {
        logger.warn("Запущен метод deleteStudent!!!");
        studentRepository.deleteById(id);
    }

    public List<Student> sortStudentByAge(int age) {
        logger.warn("Запущен метод sortStudentByAge!!!");
        return studentRepository.findByAge(age);
    }

    public List<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.warn("Запущен метод findByAgeBetween!!!");
        return studentRepository.findByAgeBetween(ageMin, ageMax);
    }

    public List<Student> findAll() {
        logger.warn("Запущен метод findAll!!!");
        return studentRepository.findAll();
    }

    public Faculty getFacultyByStudent(long id) {
        logger.warn("Запущен метод getFacultyByStudent!!!");
        return findStudent(id).getFaculty();
    }

    public Long overallStudentAmount() {
        logger.warn("Запущен метод overallStudentAmount!!!");
        return studentRepository.overallStudentAmount();
    }

    public Long getAverageStudentAge() {
        logger.warn("Запущен метод getAverageStudentAge!!!");
        return studentRepository.getAverageStudentAge();
    }

    public double anotherGetAverageStudentAge() {
        return studentRepository.findAll().stream()
                .mapToInt(student->student.getAge())
                .average()
                .orElse(0);
    }

    public List<Student> getLastFiveStudentsByID() {
        logger.warn("Запущен метод getLastFiveStudentsByID!!!");
        return studentRepository.getLastFiveStudentsByID();
    }

    public List<String> getAllNamesStartsWithA() {
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .filter(i -> i.startsWith("A"))
                .sorted((s1,s2) -> s1.compareTo(s2))
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    public double countMethod() {
        double start = System.currentTimeMillis();
        int result = Stream.iterate(1, a -> a +1).limit(1_000_000_00)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        double finish = System.currentTimeMillis();
        double timeResult = finish-start;
        return  timeResult;
    }
}
