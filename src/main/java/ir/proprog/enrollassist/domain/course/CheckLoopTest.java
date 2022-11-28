package ir.proprog.enrollassist.domain.course;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.controller.course.CourseMajorView;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.repository.CourseRepository;
import ir.proprog.enrollassist.repository.ProgramRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckLoopTest {

    private AddCourseService acs;

    private Course c1;
    private Course c2;
    private Course c3;
    private CourseRepository courseRepository;

    private ProgramRepository programRepository;

    private CourseMajorView cmv;

    @BeforeEach
    void setup(){
        c1 = mock(Course.class);
        c2 = mock(Course.class);
        c3 = mock(Course.class);
        courseRepository = mock(CourseRepository.class);
        programRepository = mock(ProgramRepository.class);
        acs = new AddCourseService(courseRepository, programRepository);
    }

    @Test
    public void without_loop() throws ExceptionList {
        Set<Long> pres = new HashSet<Long>(); ;
        Set<Long> programs = new HashSet<Long> ();
        when(c1.getId()).thenReturn(1L);
        when(c1.getCourseNumber()).thenReturn(new CourseNumber());
        when(c1.getTitle()).thenReturn("Test");
        when(c1.getGraduateLevel()).thenReturn(GraduateLevel.Undergraduate);
        cmv = new CourseMajorView(c1, pres, programs );
        acs.addCourse(cmv);
    }

    @Test
    public void with_loop() throws ExceptionList {

        when(c2.getId()).thenReturn(2L);
        when(c2.getCourseNumber()).thenReturn(new CourseNumber());
        when(c2.getTitle()).thenReturn("IE");
        when(c2.getGraduateLevel()).thenReturn(GraduateLevel.Undergraduate);

        when(c3.getId()).thenReturn(3L);
        when(c3.getTitle()).thenReturn("AI");

        when(courseRepository.findById(3L)).thenReturn(Optional.ofNullable(c3));
        when(courseRepository.findById(2L)).thenReturn(Optional.ofNullable(c2));

        Set<Long> pres = new HashSet<Long>();
        pres.add(c3.getId());
        Set<Long> programs = new HashSet<Long> ();

        Set<Course> pres_c3 = new HashSet<Course>();
        pres_c3.add(c2);
        Set<Course> pres_c2 = new HashSet<Course>();
        pres_c2.add(c3);

        when(c3.getPrerequisites()).thenReturn(pres_c3);
        when(c2.getPrerequisites()).thenReturn(pres_c2);

        ExceptionList expected_exception = new ExceptionList();
        List<Exception> es = new ArrayList<>(){{
            add(new Exception(String.format("%s has made a loop in prerequisites.", "AI")));
        }};
        expected_exception.addExceptions(es);

        cmv = new CourseMajorView(c2, pres, programs);

        ExceptionList exception = assertThrows(ExceptionList.class, () -> acs.addCourse(cmv));
        Assert.assertEquals(expected_exception.getMessage(), exception.getMessage());
    }

}