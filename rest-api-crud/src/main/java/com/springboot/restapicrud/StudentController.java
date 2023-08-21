package com.springboot.restapicrud;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    //@PostMapping
    //public Student createStudent(@RequestBody Student student) {
     //   return studentRepository.save(student);
    //}
    @PostMapping
    public List<Student> createStudents(@RequestBody List<Student> students) {
   
    	return studentRepository.saveAll(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/multiple")
    public ResponseEntity<List<Student>> getCoursesByIds(@RequestParam List<Long> ids) {
        List<Student> students = studentRepository.findAllById(ids);

        if (students.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(students);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentData) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
        	Student student = optionalStudent.get();
        	student.setName(studentData.getName());
        	student.setMajor(studentData.getMajor());
            return ResponseEntity.ok(studentRepository.save(student));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    	studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
