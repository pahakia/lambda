package pahakia.fault.lambda;

import static junit.framework.TestCase.fail;
import static pahakia.fault.Fault.tri;

import org.junit.Test;

import pahakia.fault.FaultCode;
import pahakia.fault.FaultCodes;

public class InvalidCasesTest {
    @Test
    public void nullCode() {
        tri(() -> {
            new FaultCode(null, 0, null);
            fail("should thow " + FaultCodes.Mandatory);
            return null;
        }).ignore(FaultCodes.Mandatory).finale();
    }

    @Test
    public void numArgsNegative() {
        tri(() -> {
            new FaultCode("abc", -1, null);
            fail("should thow " + FaultCodes.NumArgsNegative);
            return null;
        }).ignore(FaultCodes.NumArgsNegative).finale();
    }

    @Test
    public void InsufficientArgs() {
        tri(() -> {
            new FaultCode("abc", 1, "hello world");
            fail("should thow " + FaultCodes.InsufficientArgs);
            return null;
        }).ignore(FaultCodes.InsufficientArgs).finale();

        tri(() -> {
            new FaultCode("abc", 2, "hello world {0}");
            fail("should thow " + FaultCodes.InsufficientArgs);
            return null;
        }).ignore(FaultCodes.InsufficientArgs).finale();

        tri(() -> {
            new FaultCode("abc", 2, "hello world {0} {2}");
            fail("should thow " + FaultCodes.InsufficientArgs);
            return null;
        }).ignore(FaultCodes.InsufficientArgs).finale();
    }

    @Test
    public void nullMessageTemplate() {
        tri(() -> {
            new FaultCode("abc", 0, null);
            fail("should thow " + FaultCodes.EmptyMessageTemplate);
            return null;
        }).ignore(FaultCodes.EmptyMessageTemplate).finale();
    }

}
