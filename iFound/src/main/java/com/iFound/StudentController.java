package com.iFound;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import com.iFound.model.Student;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/students") // base path
public class StudentController {

    private final List<Student> students = new ArrayList<>(List.of(
        new Student(1, "Seth", 60),
        new Student(2, "Kevin", 70)
    ));

    // ✅ Get all students (explicitly returning JSON)
    @GetMapping(produces = "application/json")
    public List<Student> getStudents() {
        return students;
    }

    // ✅ CSRF token (if needed)
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    // ✅ Add a student
    @PostMapping(consumes = "application/json", produces = "application/json")
    public Student addStudent(@RequestBody Student student) {
        students.add(student);
        return student;
    }
}
