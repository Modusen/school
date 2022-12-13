package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        Faculty foundFaculty = facultyRepository.findById(faculty.getId()).orElse(null);
        if (foundFaculty != null) {
            return facultyRepository.save(faculty);
        }
        return null;
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> sortFacultyByColor(String color) {
        return facultyRepository.findByColor(color);
    }
    public List<Faculty> findByNameIgnoreCaseAndColorIgnoreCase(String name, String color){
        return facultyRepository.findByNameIgnoreCaseAndColorIgnoreCase(name, color);
    }

    public Collection<Student> getStudentByFaculty(long id) {
        return studentRepository.findAllByFaculty_Id(id);
    }
}
