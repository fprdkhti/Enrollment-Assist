package ir.proprog.enrollassist.domain.enrollmentList;

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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(Parameterized.class)
public class CheckExamTimeConflictsTest {
    public EnrollmentList enrollmentList;
    public int expectedViolations;
    public String violationMessage;

    public CheckExamTimeConflictsTest (EnrollmentList e, int v, String s) {
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
        Section Dm = new Section(dm, "5", new ExamTime("2021-09-01T08:00", "2021-09-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Cad = new Section(cad, "6", new ExamTime("2021-09-01T09:00", "2021-09-01T10:30"), Collections.singleton(new PresentationSchedule("Saturday", "13:00", "14:30")));

        Student s = new Student("810197474", "Masters");
        s.addProgram(p);
        s.setGrade("00001", os, 19);

        EnrollmentList exam_time_conflicts = new EnrollmentList("exam_time_conflicts", s);
        exam_time_conflicts.addSection(Dm);
        exam_time_conflicts.addSection(Cad);
        exam_time_conflicts.addSection(Cps);
        exam_time_conflicts.addSection(Se);

        return Arrays.asList(new Object[][]{
                {exam_time_conflicts, 1, "ir.proprog.enrollassist.domain.section.Section@a9bf6808 and ir.proprog.enrollassist.domain.section.Section@47bd4d7e have conflict in exam time."},
        });


    }


    @Test
    public void CheckExamTimeConflictsTest() {
        List<EnrollmentRuleViolation> violations = enrollmentList.checkExamTimeConflicts();
        assertEquals(this.violationMessage, violations.get(0).toString());
    }
}
