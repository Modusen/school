package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    List<Student> findByAgeBetween(int ageMin, int ageMax);
    Collection<Student> findAllByFaculty_Id(long facultyId);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    Long overallStudentAmount();
    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    Long getAverageStudentAge();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudentsByID();
}
