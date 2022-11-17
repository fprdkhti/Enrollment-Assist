package ir.proprog.enrollassist.domain.section;

import ir.proprog.enrollassist.Exception.ExceptionList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class HasConflictTest {
    @Test
    void this_and_other_in_diff_days() throws ExceptionList {
        PresentationSchedule main = new PresentationSchedule("Sunday", "09:00", "10:30");
        PresentationSchedule other = new PresentationSchedule("Monday", "09:00", "10:30");
        assertFalse(main.hasConflict(other));
    }

    @Test
    void end_this_before_start_other() throws ExceptionList {
        PresentationSchedule main = new PresentationSchedule("Sunday", "09:00", "10:30");
        PresentationSchedule other = new PresentationSchedule("Sunday", "11:00", "12:30");
        assertFalse(main.hasConflict(other));
    }

    @Test
    void end_other_before_start_this() throws ExceptionList {
        PresentationSchedule other = new PresentationSchedule("Sunday", "11:00", "12:30");
        PresentationSchedule main = new PresentationSchedule("Sunday", "09:00", "10:30");
        assertFalse(main.hasConflict(other));
    }

    @Test
    void end_this_after_start_other() throws ExceptionList {
        PresentationSchedule main = new PresentationSchedule("Sunday", "09:00", "10:30");
        PresentationSchedule other = new PresentationSchedule("Sunday", "10:00", "11:30");
        assertTrue(main.hasConflict(other));
    }

    @Test
    void end_other_after_start_this() throws ExceptionList {
        PresentationSchedule other = new PresentationSchedule("Sunday", "10:00", "11:30");
        PresentationSchedule main = new PresentationSchedule("Sunday", "11:00", "12:30");
        assertTrue(main.hasConflict(other));
    }
}