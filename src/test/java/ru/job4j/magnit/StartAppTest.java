package ru.job4j.magnit;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test.
 *
 * @author Yury Doronin (doronin.ltd@gmail.com)
 * @version 2.0
 * @since 07.03.2020
 */
public class StartAppTest {

    private static int expected;
    private static int output;
    private static long time;

    private static int testSum(int size) {
        int expected = 0;
        for (int index = 1; index <= size; index++) {
            expected = expected + index;
        }
        return expected;
    }

    private static int outputSum(int size) throws Exception {
        new StoreSQL(new Config()).generate(size);
        new StoreXML("target.xml").save();
        return new ParseXML(
                new ConvertXSLT("target.xml", "output.xml", "scheme.xsl").convert()
        ).sumOfAllElements();
    }

    private static boolean comparingOfTwoSums(int size) throws Exception {
        boolean result = false;
        expected = StartAppTest.testSum(size);
        output = StartAppTest.outputSum(size);
        if (expected == output) {
            result = true;
        }
        return result;
    }

    /**
     * Testing of the sum of Entries' values.
     *
     * @throws Exception, .
     */
    @Test
    public void whenFiveEntriesThenSumIsFifteen() throws Exception {
        assertThat(StartAppTest.comparingOfTwoSums(5), is(Boolean.TRUE));
        System.out.println(String.format("The expected sum: %s", expected));
        System.out.println(String.format("The sum of all elements: %d", output));
    }

    private static boolean getTime(int size, int minutes) throws Exception {
        boolean result = false;
        long start = System.currentTimeMillis();
        StartAppTest.outputSum(size);
        long finish = System.currentTimeMillis();
        time = finish - start;
        if (TimeUnit.MILLISECONDS.toSeconds(time) < TimeUnit.MINUTES.toSeconds(minutes)) {
            result = true;
        }
        return result;
    }

    /**
     * Testing the time of the sum' calculations.
     * Under a million Entries the time of the sum' calculations should not be more than 5 seconds.
     */
    @Test
    public void whenMillionEntriesThenTimeIsLessThanFiveMinutes() throws Exception {
        assertThat(StartAppTest.getTime(1000000, 5), is(Boolean.TRUE));
        System.out.println(String.format("If there are a million Entries then the time is %d seconds",
                TimeUnit.MILLISECONDS.toSeconds(time)));
    }
}