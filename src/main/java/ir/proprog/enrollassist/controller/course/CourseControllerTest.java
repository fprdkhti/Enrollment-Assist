package ir.proprog.enrollassist.controller.course;

import com.google.gson.Gson;
import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.course.AddCourseService;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.repository.CourseRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CourseController.class)
@ContextConfiguration(classes=CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddCourseService addCourseService;

    private Course cps;
    private Course ai;
    private Course os;
    private CourseRepository courseRepository;

    @BeforeEach
    void setup() throws ExceptionList {
        cps = new Course("9012345", "CPS", 4, "Masters");
        ai = new Course("1234567", "AI", 4, "Masters");
        os = new Course("2345678", "OS", 4, "Masters");
        courseRepository = mock(CourseRepository.class);
    }

    @Test
    public void NotFoundException() throws Exception {
        when(courseRepository.findById(any(Long.class))).thenReturn(java.util.Optional.ofNullable(null));
        mockMvc.perform(get("/courses/{id}", 2L))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Course not found"));
    }

    @Test
    public void OneValidCourse() throws Exception {
        when(courseRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(cps));
        mockMvc.perform(get("/courses/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.courseNumber.courseNumber").value("9012345"))
                .andExpect(jsonPath("$.courseTitle").value("CPS"));
    }

    @Test
    public void MultiValidCourse() throws Exception {
        when(courseRepository.findAll()).thenReturn(new ArrayList<Course>(Arrays.asList(cps, os, ai)));
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].courseNumber.courseNumber").value("9012345"))
                .andExpect(jsonPath("$[0].courseTitle").value("cps"))
                .andExpect(jsonPath("$[1].courseNumber.courseNumber").value("2345678"))
                .andExpect(jsonPath("$[1].courseTitle").value("os"))
                .andExpect(jsonPath("$[2].courseNumber.courseNumber").value("1234567"))
                .andExpect(jsonPath("$[2].courseTitle").value("ai"));
    }

    @Test
    public void AddNewCourseSuccess() throws Exception {
        when(addCourseService.addCourse(any(CourseMajorView.class))).thenReturn(cps);

        Gson gson = new Gson();
        Set<Long> prerequisites = Collections.<Long>emptySet();
        Set<Long> programs = Collections.<Long>emptySet();
        CourseMajorView courseMajorView= new CourseMajorView(cps, prerequisites, programs);
        String courseMajorViewJson = gson.toJson(courseMajorView);

        mockMvc.perform(post("/courses")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(courseMajorViewJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.courseNumber.courseNumber").value("9012345"))
                .andExpect(jsonPath("$.courseTitle").value("cps"));
    }

}