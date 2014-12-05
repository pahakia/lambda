package pahakia.fault.lambda;

import static junit.framework.TestCase.assertEquals;
import static pahakia.fault.Fault.tri;

import org.junit.Test;

public class KatchMultiExceptionClassTest {
    @Test
    public void test1() {
        int num = string2Int("abc");
        assertEquals(100, num);
    }

    @Test
    public void test2() {
        int num = string2Int(null);
        assertEquals(200, num);
    }

    @Test
    public void test3() {
        int num = string2Int("301");
        assertEquals(301, num);
    }

    private int string2Int(final String str) {
        @SuppressWarnings("unchecked")
        int tmp = tri(() -> {
            str.length();
            return Integer.valueOf(str);
        }).katch(ex -> {
            System.out.println("processing NumberFormatException/NullPointerException: " + ex);
            if (ex.getCause() instanceof NullPointerException) {
                return 200;
            }
            return 100;
        }, NumberFormatException.class, NullPointerException.class).finale(() -> {
            System.out.println("hello");
        });
        System.out.println(tmp);
        return tmp;
    }
}
