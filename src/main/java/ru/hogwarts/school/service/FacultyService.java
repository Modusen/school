package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty addFaculty(Faculty faculty) {
        logger.warn("Был запущен метод addFaculty!!! Из " + this.getClass());
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        logger.warn("Был запущен метод findFaculty!!!");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.warn("Был запущен метод editFaculty!!!");
        Faculty foundFaculty = facultyRepository.findById(faculty.getId()).orElse(null);
        if (foundFaculty != null) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(long id) {
        logger.warn("Был запущен метод deleteFaculty!!!");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> sortFacultyByColor(String color) {
        logger.warn("Был запущен метод sortFacultyByColor!!!");
        return facultyRepository.findByColor(color);
    }
    public List<Faculty> findByNameIgnoreCaseAndColorIgnoreCase(String name, String color){
        logger.warn("Был запущен метод findByNameIgnoreCaseAndColorIgnoreCase!!!");
        return facultyRepository.findByNameIgnoreCaseAndColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentByFaculty(long id) {
        logger.warn("Был запущен метод getStudentByFaculty!!!");
        return studentRepository.findAllByFaculty_Id(id);
    }
    public Optional<String> getLongestFacultyName(){
        return facultyRepository.findAll()
                .stream()
                .map(f->f.getName())
                .max(Comparator.comparingInt(String::length));
    }
}
