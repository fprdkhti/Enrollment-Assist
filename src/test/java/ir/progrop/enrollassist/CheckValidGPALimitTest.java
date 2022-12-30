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

public class CheckValidGPALimitTest {

    public EnrollmentList enrollmentList;
    public int expectedViolations;
    public String violationMessage;

    public CheckValidGPALimitTest (EnrollmentList e, int v, String s) {
        this.enrollmentList = e;
        this.expectedViolations = v;
        this.violationMessage = s;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws Exception {

        Major m = new Major("10", "CS", "Engineering");
        Program p = new Program(m, "Undergraduate", 1, 20, "Major");

        Course ai = new Course("1234567", "AI", 4, "Undergraduate");
        Course os = new Course("2345678", "OS", 4, "Undergraduate");
        Course ml = new Course("4567890", "ML", 4, "Undergraduate");
        Course nlp = new Course("3456789", "NLP", 4, "Undergraduate").withPre(ml);
        Course cps = new Course("9012345", "CPS", 4, "Undergraduate").withPre(os);
        Course ie = new Course("5678901", "IE", 4, "Undergraduate");
        Course dm = new Course("6789012", "DM", 4, "Undergraduate");
        Course cad = new Course("7890123", "CAD", 4, "Undergraduate");
        Course se = new Course("8901234", "SE", 4, "Undergraduate");
        Course da = new Course("0123456", "DA", 4, "Undergraduate");
        p.addCourse(ai, os, ml, nlp, ie, se, cps, da, dm, cad);

        Section Cps = new Section(cps, "1", new ExamTime("2021-01-01T08:00", "2021-01-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Se = new Section(se, "2", new ExamTime("2021-05-01T08:00", "2021-05-01T09:30"), Collections.singleton(new PresentationSchedule("Sunday", "09:00", "10:30")));
        Section Nlp = new Section(nlp, "3", new ExamTime("2021-07-01T08:00", "2021-07-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Os = new Section(os, "4", new ExamTime("2021-08-01T08:00", "2021-08-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Dm = new Section(dm, "5", new ExamTime("2021-09-01T08:00", "2021-09-01T09:30"), Collections.singleton(new PresentationSchedule("Saturday", "09:00", "10:30")));
        Section Cad = new Section(cad, "6", new ExamTime("2021-09-01T09:00", "2021-09-01T10:30"), Collections.singleton(new PresentationSchedule("Saturday", "13:00", "14:30")));
        Section Ie = new Section(ie, "7", new ExamTime("2021-10-01T08:00", "2021-10-01T09:30"), Collections.singleton(new PresentationSchedule("Tuesday", "09:00", "10:30")));
        Section Da = new Section(da, "8", new ExamTime("2021-11-01T08:00", "2021-11-01T09:30"), Collections.singleton(new PresentationSchedule("Tuesday", "09:00", "10:30")));

        Student s = new Student("810197474", "Undergraduate");
        s.addProgram(p);
        s.setGrade("00001", os, 6);

        Student s2 = new Student("810197478", "Undergraduate");
        s2.addProgram(p);
        s2.setGrade("00001", dm, 9);
        s2.setGrade("00001", da, 7);
        s2.setGrade("00001", cad, 2);
        s2.setGrade("00001", ie, 5);

        Student s3 = new Student("810197473", "Undergraduate");
        s3.addProgram(p);
        s3.setGrade("00001", dm, 19);
        s3.setGrade("00001", da, 17);
        s3.setGrade("00001", cad, 2);
        s3.setGrade("00001", ie, 15);
        s3.setGrade("00001", nlp, 15);
        s3.setGrade("00001", os, 15);

        Student s4 = new Student("810197476", "Undergraduate");
        s4.addProgram(p);

        EnrollmentList not_met_min_valid_gpa_limit = new EnrollmentList("not_met_min_valid_gpa_limit", s);
        not_met_min_valid_gpa_limit.addSection(Dm);

        EnrollmentList max_14_exceed_gpa_limit = new EnrollmentList("max_14_exceed_gpa_limit", s2);
        max_14_exceed_gpa_limit.addSection(Dm);
        max_14_exceed_gpa_limit.addSection(Cad);
        max_14_exceed_gpa_limit.addSection(Da);
        max_14_exceed_gpa_limit.addSection(Ie);

        EnrollmentList max_20_exceed_gpa_limit = new EnrollmentList("max_20_exceed_gpa_limit", s3);
        max_20_exceed_gpa_limit.addSection(Dm);
        max_20_exceed_gpa_limit.addSection(Cad);
        max_20_exceed_gpa_limit.addSection(Da);
        max_20_exceed_gpa_limit.addSection(Ie);
        max_20_exceed_gpa_limit.addSection(Nlp);
        max_20_exceed_gpa_limit.addSection(Os);
        max_20_exceed_gpa_limit.addSection(Cps);

        EnrollmentList first_max_20_exceed_gpa_limit = new EnrollmentList("max_20_exceed_gpa_limit", s4);
        first_max_20_exceed_gpa_limit.addSection(Dm);
        first_max_20_exceed_gpa_limit.addSection(Cad);
        first_max_20_exceed_gpa_limit.addSection(Da);
        first_max_20_exceed_gpa_limit.addSection(Ie);
        first_max_20_exceed_gpa_limit.addSection(Nlp);
        first_max_20_exceed_gpa_limit.addSection(Os);

        return Arrays.asList(new Object[][]{
                {not_met_min_valid_gpa_limit, 1, "Minimum number of credits(12) is not met."},
                {max_14_exceed_gpa_limit, 1, "Maximum number of credits(14) exceeded."},
                {max_20_exceed_gpa_limit, 1, "Maximum number of credits(20) exceeded."},
                {first_max_20_exceed_gpa_limit, 1, "Maximum number of credits(20) exceeded."},
        });


    }


    @Test
    public void CheckValidGPALimitTest() {
        List<EnrollmentRuleViolation> violations = enrollmentList.checkValidGPALimit();
        System.out.print(violations.get(0).toString());
        assertEquals(this.violationMessage, violations.get(0).toString());
    }
}
