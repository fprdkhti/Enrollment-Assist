package ir.proprog.enrollassist.controller.student;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.controller.section.SectionView;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.major.Major;
import ir.proprog.enrollassist.domain.program.Program;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import ir.proprog.enrollassist.domain.student.StudentNumber;
import ir.proprog.enrollassist.domain.user.User;
import ir.proprog.enrollassist.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class StudentControllerTest {

    private StudentController sc;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private SectionRepository sectionRepository;
    @Mock
    private EnrollmentListRepository enrollmentListRepository;
    @Mock
    private UserRepository userRepository;

    private List< Student> students = new ArrayList();
    private List<StudentView> studentsView = new ArrayList();

    private List<Section> sections = new ArrayList();
    private List<SectionView> sectionViews = new ArrayList();



    private User user;


    @BeforeEach
    void setup() throws Exception {
        studentRepository = mock(StudentRepository.class);
        userRepository = mock(UserRepository.class);
        sectionRepository = mock(SectionRepository.class);
        sc = new StudentController(studentRepository, courseRepository, sectionRepository, enrollmentListRepository, userRepository);
        students.add(new Student("810197471"));
        students.add(new Student("810197472"));
        students.add(new Student("810197473", "Undergraduate"));

        studentsView.add(new StudentView(students.get(0)));
        studentsView.add(new StudentView(students.get(1)));
        studentsView.add(new StudentView(students.get(2)));

        studentsView.get(2).setUserId("1234");
        User user = new User("ali", "1234");
        Course course = new Course("1234567", "Test", 3, "Undergraduate");
        sections.add(new Section(course, "01"));
        sectionViews.add(new SectionView(sections.get(0)));

        Major m = new Major("10", "CS", "Engineering");
        Program p = new Program(m, "Undergraduate", 1, 20, "Major");
        p.addCourse(course);
        students.get(2).addProgram(p);


    }

    @Test
    public void all_test_with_student_view(){
        when(studentRepository.findAll()).thenReturn(students);
        assertThat(studentsView).usingRecursiveComparison().isEqualTo(sc.all());
    }


    @Test
    public void finding_student_with_id(){
        when(studentRepository.findByStudentNumber(new StudentNumber("810197471"))).thenReturn(Optional.ofNullable(students.get(0)));
        assertThat(studentsView.get(0)).usingRecursiveComparison().isEqualTo(sc.one("810197471"));
    }

    @Test
    public void not_finding_student_with_id(){
        Throwable exception = assertThrows(ResponseStatusException.class, () -> sc.one("810190000"));
        assertEquals("404 NOT_FOUND \"Student not found\"", exception.getMessage());
    }

    @Test
    public void add_student_with_user_not_found(){
        studentsView.get(0).setUserId("fate");
        Throwable exception = assertThrows(ResponseStatusException.class, () -> sc.addStudent(studentsView.get(0)));
        assertEquals(HttpStatus.NOT_FOUND + " \"User with id: " + studentsView.get(0).getUserId() +  " was not found.\"", exception.getMessage());
    }

    @Test
    public void add_student_with_user_found_and_student_exist(){
        studentsView.get(1).setUserId("1234");
        User user = new User("ali", "1234");
        when(userRepository.findByUserId(studentsView.get(1).getUserId())).thenReturn(Optional.ofNullable(user));
        when(studentRepository.findByStudentNumber(studentsView.get(1).getStudentNo())).thenReturn(Optional.ofNullable(students.get(1)));
        Throwable exception = assertThrows(ResponseStatusException.class, () -> sc.addStudent(studentsView.get(1)));
        assertEquals(HttpStatus.BAD_REQUEST + " \"This student already exists.\"", exception.getMessage());

    }

    @Test
    public void add_student_with_user_found_and_student_not_exist(){
        when(userRepository.findByUserId(studentsView.get(2).getUserId())).thenReturn(Optional.ofNullable(user));
        when(studentRepository.findByStudentNumber(studentsView.get(2).getStudentNo())).thenReturn(Optional.ofNullable(null));
        assertThat(studentsView.get(2)).usingRecursiveComparison().isEqualTo(sc.addStudent(studentsView.get(2)));

    }

    @Test
    public void find_takeable_sections_by_major_with_student_not_found(){
        when(studentRepository.findByStudentNumber(studentsView.get(2).getStudentNo())).thenReturn(Optional.ofNullable(null));
        Throwable exception = assertThrows(ResponseStatusException.class, () -> sc.findTakeableSectionsByMajor(studentsView.get(2).getStudentNo().toString()));
        assertEquals(HttpStatus.NOT_FOUND+ " \"Student not found.\"", exception.getMessage());
    }

    @Test
    public void find_takeable_sections_by_major_with_student_found(){
        when(studentRepository.findByStudentNumber(studentsView.get(2).getStudentNo())).thenReturn(Optional.ofNullable(students.get(2)));
        when(sectionRepository.findAll()).thenReturn(sections);
        assertThat(sectionViews).usingRecursiveComparison().isEqualTo(sc.findTakeableSectionsByMajor("810197473"));
    }




}