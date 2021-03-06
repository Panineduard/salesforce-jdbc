package com.ascendix.jdbc.salesforce.delegates;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PartnerResultToCrtesianTableTest {

    @Test
    public void testExpandSimple() {
        List schema = Arrays.asList(new Object(), new Object(), new Object(), new Object());

        List<List> expected = Arrays.asList(
                (List) Arrays.asList(1, 2, 3, 4)
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(expected, schema);

        assertEquals(actual, expected);
    }

    @Test
    public void testExpandWhenNothingToExpand() {
        List schema = Arrays.asList(new Object(), new Object(), new Object(), new Object());

        List<List> expected = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList("1", "2", "3", "4"),
                Arrays.asList("11", "12", "13", "14"),
                Arrays.asList("21", "22", "23", "24")
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(expected, schema);

        assertEquals(actual, expected);
    }

    @Test
    public void testExpandWhenOneNestedList() {
        List schema = Arrays.asList(new Object(), Arrays.asList(new Object(), new Object(), new Object()), new Object(), new Object());

        List<List> list = Arrays.asList(
                (List) Arrays.asList("1", Arrays.asList("21", "22", "23"), "3", "4")
        );

        List<List> expected = Arrays.asList(
                Arrays.asList("1", "21", "3", "4"),
                Arrays.asList("1", "22", "3", "4"),
                Arrays.asList("1", "23", "3", "4")
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(list, schema);

        assertEquals(actual, expected);
    }

    @Test
    public void testExpandWhenTwoNestedListAndOneRow() {
        List schema = Arrays.asList(new Object(), Arrays.asList(new Object(), new Object()), new Object(), Arrays.asList(new Object(), new Object()));

        List<List> list = Arrays.asList(
                (List) Arrays.asList(11, Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4)), 12, Arrays.asList(Arrays.asList(5, 6), Arrays.asList(7, 8)))
        );

        List<List> expected = Arrays.asList(
                Arrays.asList(11, 1, 2, 12, 5, 6),
                Arrays.asList(11, 3, 4, 12, 5, 6),
                Arrays.asList(11, 1, 2, 12, 7, 8),
                Arrays.asList(11, 3, 4, 12, 7, 8)
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(list, schema);

        assertEquals(expected.size(), actual.size());
        for (List l : expected) {
            assertTrue(actual.contains(l));
        }
    }

    @Test
    public void testExpandWhenOneNestedListAndTwoRows() {
        List schema = Arrays.asList(new Object(), Arrays.asList(new Object(), new Object()), new Object(), new Object());

        List<List> list = Arrays.asList(
                Arrays.asList(11, Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4)), 12, 13),
                Arrays.asList(20, Arrays.asList(Arrays.asList(21, 22), Arrays.asList(23, 24), Arrays.asList(25, 26)), 41, 42)
        );

        List<List> expected = Arrays.asList(
                Arrays.asList(11, 1, 2, 12, 13),
                Arrays.asList(11, 3, 4, 12, 13),
                Arrays.asList(20, 21, 22, 41, 42),
                Arrays.asList(20, 23, 24, 41, 42),
                Arrays.asList(20, 25, 26, 41, 42)
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(list, schema);

        assertEquals(actual, expected);
    }

    @Test
    public void testExpandWhenOneNestedListIsEmpty() {
        List schema = Arrays.asList(new Object(), Arrays.asList(new Object(), new Object()), new Object(), new Object());

        List<List> list = Arrays.asList(
                (List) Arrays.asList(11, new ArrayList(), 12, 13)
        );

        List<List> expected = Arrays.asList(
                (List) Arrays.asList(11, null, null, 12, 13)
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(list, schema);

        assertEquals(actual, expected);
    }

    @Test
    public void testExpandWhenNestedListIsEmpty() {
        List schema = Arrays.asList(new Object(), Arrays.asList(new Object(), new Object()));

        List<List> list = Arrays.asList(
                (List) Arrays.asList(11, new Object())
        );

        List<List> expected = Arrays.asList(
                (List) Arrays.asList(11, null, null)
        );

        List<List> actual = PartnerResultToCrtesianTable.expand(list, schema);

        assertEquals(actual, expected);
    }

}
