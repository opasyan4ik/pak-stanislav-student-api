package kz.pak.studentapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class ControlledFailureTest {
    @Test
    void demonstratesJenkinsStoppingOnTestFailure() {
        fail("Controlled failure for the Jenkins midterm demonstration");
    }
}
