package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        students.put(lastId, student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student editFStudent(Student student) {
        return students.put(student.getId(), student);
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public List<Student> sortStudentByAge(int age) {
        return students.values()
                .stream()
                .filter(student -> student.getAge() == age).toList();
    }
}
