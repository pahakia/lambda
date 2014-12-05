package pahakia.fault.lambda;

import static junit.framework.TestCase.assertEquals;
import static pahakia.fault.Fault.tri;
import static pahakia.fault.JreFaultCodes.NumberFormatException_java_lang;

import org.junit.Test;

import pahakia.fault.Fault;

public class KatchCode1Test {
    @Test
    public void test1() {
        int num = string2Int("abc");
        assertEquals(100, num);
    }

    @Test(expected = Fault.class)
    public void test2() {
        int num = string2Int(null);
        assertEquals(500, num);
    }

    @Test
    public void test3() {
        int num = string2Int("301");
        assertEquals(301, num);
    }

    private int string2Int(final String str) {
        int tmp = tri(() -> {
            str.length();
            return Integer.valueOf(str);
        }).katch(NumberFormatException_java_lang, ex -> {
            System.out.println("processing NumberFormatException: " + ex);
            return 100;
        }).finale(() -> System.out.println("hello"));

        System.out.println(tmp);
        return tmp;
    }
}
