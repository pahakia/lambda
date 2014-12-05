package pahakia.fault.lambda;

import static junit.framework.TestCase.assertEquals;
import static pahakia.fault.Fault.tri;

import org.junit.Test;

import pahakia.fault.Fault;

public class KatchExceptionClassTest {
    @Test
    public void test1() {
        int num = string2Int("abc");
        assertEquals(100, num);
    }

    @Test(expected = Fault.class)
    public void test2() {
        string2Int(null);
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
        }).katch(NumberFormatException.class, ex -> {
            System.out.println("processing NumberFormatException: " + ex);
            return 100;
        }).finale(() -> {
            System.out.println("hello");
        });
        System.out.println(tmp);
        return tmp;
    }
}
