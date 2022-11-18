package ir.proprog.enrollassist.domain.enrollmentList;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.EnrollmentRules.EnrollmentRuleViolation;
import ir.proprog.enrollassist.domain.course.Course;
import ir.proprog.enrollassist.domain.major.Major;
import ir.proprog.enrollassist.domain.program.Program;
import ir.proprog.enrollassist.domain.section.ExamTime;
import ir.proprog.enrollassist.domain.section.PresentationSchedule;
import ir.proprog.enrollassist.domain.section.Section;
import ir.proprog.enrollassist.domain.student.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class CheckEnrollmentRulesTest {
    public EnrollmentList enrollmentList;
    public int expectedViolations;
    public String violationMessage;

    public CheckEnrollmentRulesTest (EnrollmentList e, int v, String s) {
        this.enrollmentList = e;
        this.expectedViolations = v;
        this.violationMessage = s;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws Exception {

        Major m = new Major("10", "CS", "Engineering");
        Program p = new Program(m, "Masters", 1, 20, "Major");

        Course ai = new Course("1234567", "AI", 4, "Masters");
        Course os = new Course("2345678", "OS", 4, "Masters");
        Course ml = new Course("4567890", "ML", 4, "Masters");
        Course nlp = new Course("3456789", "NLP", 4, "Masters").withPre(ml);
        Course cps = new Course("9012345", "CPS", 4, "Masters").withPre(os);
        Course ie = new Course("5678901", "IE", 4, "Masters");
        Course dm = new Course("6789012", "DM", 4, "Masters");
        Course cad = new Course("7890123", "CAD", 4, "Masters");
        Course se = new Course("8901234", "SE", 4, "Masters");
        Course da = new Course("0123456", "DA", 4, "Masters");
        p.addCourse(ai, os, ml, nlp, ie, se, cps, da, dm, cad);





        Section Cps = new Section(cps, "1", new ExamTime("2021-01-01T08:00", "2021-01-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Se = new Section(se, "2", new ExamTime("2021-05-01T08:00", "2021-05-01T09:30"), Collections.singleton(new PresentationSchedule("Sunday", "09:00", "10:30")));
        Section Nlp = new Section(nlp, "3", new ExamTime("2021-07-01T08:00", "2021-07-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Os = new Section(os, "4", new ExamTime("2021-08-01T08:00", "2021-08-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Dm = new Section(dm, "5", new ExamTime("2021-09-01T08:00", "2021-09-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Cad = new Section(cad, "6", new ExamTime("2021-09-01T09:00", "2021-09-01T10:30"), Collections.singleton(new PresentationSchedule("Saturday", "13:00", "14:30")));
        Section Ie = new Section(ie, "7", new ExamTime("2021-10-01T08:00", "2021-10-01T09:30"), Collections.singleton(new PresentationSchedule("Tuesday", "09:00", "10:30")));
        Section Da = new Section(da, "8", new ExamTime("2021-11-01T08:00", "2021-11-01T09:30"), Collections.singleton(new PresentationSchedule("Tuesday", "09:00", "10:30")));

        Student s = new Student("810197474", "Masters");
        s.addProgram(p);
        s.setGrade("00001", os, 19);


        EnrollmentList without_error = new EnrollmentList("without_error", s);
        without_error.addSections(Cps);
        without_error.addSections(Se);

        EnrollmentList has_not_passed_all_prerequisites = new EnrollmentList("has_not_passed_all_prerequisites", s);
        has_not_passed_all_prerequisites.addSections(Nlp);

        EnrollmentList has_already_passed_courses = new EnrollmentList("has_already_passed_courses", s);
        has_already_passed_courses.addSections(Os);

        EnrollmentList course_has_requested_twice = new EnrollmentList("course_has_requested_twice", s);
        course_has_requested_twice.addSections(Se);
        course_has_requested_twice.addSection(Se);


        EnrollmentList exam_time_conflicts = new EnrollmentList("exam_time_conflicts", s);
        exam_time_conflicts.addSection(Dm);
        exam_time_conflicts.addSection(Cad);

        EnrollmentList section_schedule_conflicts = new EnrollmentList("section_schedule_conflicts", s);
        section_schedule_conflicts.addSection(Ie);
        section_schedule_conflicts.addSection(Da);

        return Arrays.asList(new Object[][]{
                {without_error, 0, null},
                {has_not_passed_all_prerequisites, 1, "[4567890] ML is not passed as a prerequisite of [3456789] NLP"},
                {has_already_passed_courses, 1, "[2345678] OS has been already passed"},
                {course_has_requested_twice, 1, "[8901234] SE is requested to be taken twice"},
                {exam_time_conflicts , 1, "ir.proprog.enrollassist.domain.section.Section@a9bf6808 and ir.proprog.enrollassist.domain.section.Section@47bd4d7e have conflict in exam time."},
                {section_schedule_conflicts , 1, "ir.proprog.enrollassist.domain.section.Section@b392341 course and ir.proprog.enrollassist.domain.section.Section@f2833787 course have conflict in schedule."},
        });


    }

    @Test
    public void checkEnrollmentRulesTest() {
        List<EnrollmentRuleViolation> violations = enrollmentList.checkEnrollmentRules();
        if(violations.size() == 0)
            assertEquals(this.expectedViolations, violations.size());
        else if (violations.size() == 1)
            assertEquals(this.violationMessage, violations.get(0).toString());
    }


}