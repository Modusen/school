package ru.hogwarts.school;

import static org.assertj.core.api.Assertions.*;

import com.github.javafaker.Faker;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Stream;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    private final Faker faker = new Faker();

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }


    @Test
    public void createTest() {
        addStudent(generateStudent(addFaculty(generateFaculty())));
    }


    private Faculty addFaculty(Faculty faculty) {
        ResponseEntity<Faculty> facultyResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody()).isNotNull();
        assertThat(facultyResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(faculty);
        return facultyResponseEntity.getBody();
    }

    private Student addStudent(Student student) {
        ResponseEntity<Student> studentResponseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponseEntity.getBody()).isNotNull();
        assertThat(studentResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(student);
        return studentResponseEntity.getBody();
    }

    @Test
    public void putTest(){
        Faculty faculty1 = addFaculty(generateFaculty());
        System.out.println(faculty1);
        Faculty faculty2 = addFaculty(generateFaculty());
        System.out.println(faculty2);
        Student student = addStudent(generateStudent(faculty1));
        System.out.println(student);
        ResponseEntity<Student> getForEntityResponse = testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/" + student.getId(), Student.class);
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody()).isNotNull();
        assertThat(getForEntityResponse.getBody()).usingRecursiveComparison().isEqualTo(student);
        assertThat(getForEntityResponse.getBody().getFaculty()).usingRecursiveComparison().isEqualTo(faculty1);

        student.setFaculty(faculty2);
        System.out.println(student);
        ResponseEntity<Student> recordResponseEntity = testRestTemplate
                .exchange("http://localhost:" + port + "/student/", HttpMethod.POST, new HttpEntity<>(student), Student.class);
        assertThat(recordResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recordResponseEntity.getBody()).isNotNull();
        assertThat(recordResponseEntity.getBody()).usingRecursiveComparison().isEqualTo(student);
        assertThat(recordResponseEntity.getBody().getFaculty()).usingRecursiveComparison().isEqualTo(faculty2);
    }

    @Test
    public void findByAgeBetween() {
        List<Faculty> faculties = Stream.generate(this::generateFaculty)
                .limit(5)
                .map(this::addFaculty)
                .toList();
        List<Student> students = Stream.generate(()->generateStudent(faculties.get(faker.random().nextInt(faculties.size()))))
                .limit(5)
                .map(this::addStudent)
                .toList();
        int minAge = 11;
        int maxAge = 25;

        List<Student> expectedStudents = students.stream()
                .filter(student -> student.getAge() >= minAge && student.getAge() <= maxAge)
                .toList();

        ResponseEntity<List<Student>> getForEntityResponse = testRestTemplate
                .exchange("http://localhost:" + port + "/student?min={minAge}&max={maxAge}", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Student>>() {
                }, minAge, maxAge);
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody()).hasSize(expectedStudents.size()).usingRecursiveFieldByFieldElementComparator();
    }


    private Faculty generateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(0);
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }

    private Student generateStudent(Faculty faculty) {
        Student student = new Student();
        student.setId(0);
        student.setName(faker.harryPotter().character());
        student.setAge(faker.random().nextInt(11, 18));
        if (faculty != null) {
            student.setFaculty(faculty);
        }
        return student;
    }
}
