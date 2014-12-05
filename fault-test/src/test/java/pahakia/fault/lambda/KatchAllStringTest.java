package pahakia.fault.lambda;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import pahakia.fault.Fault;

public class KatchAllStringTest {
    @Test
    public void test1() {
        int num = string2Int("abc");
        assertEquals(100, num);
    }

    @Test
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
        int tmp = Fault.tri(() -> {
            str.length();
            return Integer.valueOf(str);
        }).katchAll(ex -> {
            System.out.println("processing: " + ex);
            if (ex.getCause() instanceof NumberFormatException) {
                return 100;
            } else if (ex.getCause() instanceof NullPointerException) {
                return 500;
            }
            throw ex;
        }).finale(() -> {
            System.out.println("hello");
        });
        System.out.println(tmp);
        return tmp;
    }
}
