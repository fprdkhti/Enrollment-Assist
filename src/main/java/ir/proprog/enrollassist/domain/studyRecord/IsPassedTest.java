package ir.proprog.enrollassist.domain.studyRecord;

import ir.proprog.enrollassist.Exception.ExceptionList;
import ir.proprog.enrollassist.domain.GraduateLevel;
import ir.proprog.enrollassist.domain.course.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith( Parameterized.class )
public class IsPassedTest {
    public int grade;
    public boolean expected;
    public GraduateLevel graduate_level;

    public StudyRecord sr;
    public IsPassedTest(int grade, String gra_level, boolean expected) throws Exception {
        this.grade = grade;
        this.expected = expected;
        this.graduate_level = GraduateLevel.valueOf(gra_level);
        this.sr = new StudyRecord("00001", new Course("1234567", "Test", 4, gra_level), this.grade);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parameters() throws Exception {
        return Arrays.asList(new Object[][]{
                {0, "Undergraduate", false}, {10, "Undergraduate", true}, {15, "Undergraduate", true},
                {0, "Masters", false}, {10, "Masters", false}, {12, "Masters", true}, {16, "Masters", true},
                {0,"PHD",false}, {12,"PHD",false}, {14,"PHD",true}, {20,"PHD", true}
        });
    }

    @Test
    public void isPassedTest() {
        assertEquals(sr.isPassed(graduate_level), this.expected);
    }



}