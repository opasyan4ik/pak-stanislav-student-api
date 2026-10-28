package kz.pak.studentapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlledFailureTest {
    @Test
    void demonstratesFixedTestAfterJenkinsFailure() {
        assertEquals(2, 1 + 1);
    }
}
