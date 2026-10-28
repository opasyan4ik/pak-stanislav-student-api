package kz.pak.studentapi.service;

import java.util.List;
import kz.pak.studentapi.dto.StudentRequest;
import kz.pak.studentapi.dto.StudentResponse;
import kz.pak.studentapi.model.Student;
import kz.pak.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public StudentResponse create(StudentRequest request) {
        return StudentResponse.from(repository.save(new Student(request.name(), request.email())));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> findAll() {
        return repository.findAll().stream().map(StudentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public StudentResponse findById(Long id) {
        return StudentResponse.from(getStudent(id));
    }

    @Transactional
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = getStudent(id);
        student.setName(request.name());
        student.setEmail(request.email());
        return StudentResponse.from(repository.save(student));
    }

    @Transactional
    public void delete(Long id) {
        Student student = getStudent(id);
        repository.delete(student);
    }

    private Student getStudent(Long id) {
        return repository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
}
