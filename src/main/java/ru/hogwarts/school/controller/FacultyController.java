package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable long id) {
        Faculty foundFaculty = facultyService.findFaculty(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.status(HttpStatus.OK).body(facultyService.addFaculty(faculty));
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sort")
    public List<Faculty> getFacultySortedList(@RequestParam(value = "color") String color) {
        return facultyService.sortFacultyByColor(color);
    }

    @GetMapping("/sortBy")
    public List<Faculty> getFacultySortedByNameAndColorList(@RequestParam(value = "name") String name,
                                                            @RequestParam(value = "color") String color) {
        return facultyService.findByNameIgnoreCaseAndColorIgnoreCase(name, color);
    }

    @GetMapping("/{id}/student")
    public Collection<Student> getStudentByFaculty(@PathVariable long id) {
        return facultyService.getStudentByFaculty(id);
    }

    @GetMapping("/getLongestFacultyName")
    public Optional<String> getLongestFacultyName(){
        return facultyService.getLongestFacultyName();
    }
}
