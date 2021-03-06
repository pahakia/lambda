package pahakia.fault.lambda;

import static junit.framework.TestCase.assertEquals;
import static pahakia.fault.Fault.tri;

import org.junit.Test;

import pahakia.fault.JreFaultCodes;

public class KatchMultiCodeTest {
    @Test
    public void test1() {
        int num = string2Int("abc");
        assertEquals(100, num);
    }

    @Test
    public void test2() {
        int num = string2Int(null);
        assertEquals(100, num);
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
        }).katch(ex -> {
            System.out.println("processing NumberFormatException or NullPointerException: " + ex);
            return 100;
        }, JreFaultCodes.NumberFormatException_java_lang, JreFaultCodes.NullPointerException_java_lang)
          .finale(() -> {
            System.out.println("hello");
        });
        System.out.println(tmp);
        return tmp;
    }
}
