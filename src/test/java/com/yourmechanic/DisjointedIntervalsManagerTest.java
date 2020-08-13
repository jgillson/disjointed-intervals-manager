package com.yourmechanic;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author John Gillson
 * @since 08/2020
 */
class DisjointedIntervalsManagerTest {

    private List<List<Integer>> expectedList;
    private List<List<Integer>> actualList;

    @BeforeEach
    void init() {
        expectedList = new ArrayList<>();
        actualList = new ArrayList<>();
    }

    @AfterEach
    void end() {
        expectedList.clear();
        actualList.clear();
    }

    @Test
    @DisplayName("Tests adding an interval for the first time")
    void testAddIntervalFirstTime() {
        // When adding an interval for the first time, add it to the newIntervalList
        // Example: add(1, 5)
        //          newIntervalList = [[1, 5]]
        expectedList.add(Arrays.asList(1, 5));
        actualList = DisjointedIntervalsManager.add(1, 5);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests adding if the new start interval is greater than the current max interval")
    void testAddStartIntervalGreaterThanMaxInterval() {
        // If the new start interval is greater than the current max interval,
        // add the current interval to the newIntervalList followed by the new interval
        // Example: interval = [1, 5]; add(6, 8)
        //          newIntervalList = [[1, 5], [6, 8]]
        expectedList.add(Arrays.asList(1, 5));
        expectedList.add(Arrays.asList(6, 8));
        actualList = DisjointedIntervalsManager.add(1, 5);
        actualList = DisjointedIntervalsManager.add(6, 8);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests adding if the new end interval is less than the current min interval")
    void testAddEndIntervalLessThanMinInterval() {
        // If the new end interval is less than the current min interval,
        // add the new interval to the newIntervalList, followed by the current interval
        // Example: interval = [6, 8]; add(1, 5)
        //          newIntervalList = [[1, 5], [6, 8]]
        expectedList.add(Arrays.asList(1, 5));
        expectedList.add(Arrays.asList(6, 8));
        actualList = DisjointedIntervalsManager.add(6, 8);
        actualList = DisjointedIntervalsManager.add(1, 5);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests adding if there's a union with the new interval and the current min or max interval")
    void testAddUnionIntervalWithMinOrMaxInterval() {
        // If there's a union with the new interval and the current min or max interval,
        // take the lowest start interval and highest end interval and add it to the newIntervalList
        // Example: interval = [[1, 2], [3, 4], [7, 8]; add(2, 7)
        //          newIntervalList = [[1, 8]]
        expectedList.add(Arrays.asList(1, 8));
        actualList = DisjointedIntervalsManager.add(1, 2);
        actualList = DisjointedIntervalsManager.add(3, 4);
        actualList = DisjointedIntervalsManager.add(7, 8);
        actualList = DisjointedIntervalsManager.add(2, 7);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests removing if the new start and end interval is inside the current interval")
    void testRemoveStartAndEndIntervalInsideCurrentInterval() {
        // If the new start and end interval is inside the current interval,
        // the current interval is split and a new one is created and added to the newIntervalList
        // Example: interval = [[1, 5]]; remove(2, 3)
        //          newIntervalList = [[1, 2], [3, 5]]
        expectedList.add(Arrays.asList(1, 2));
        expectedList.add(Arrays.asList(3, 5));
        actualList = DisjointedIntervalsManager.add(1, 5);
        actualList = DisjointedIntervalsManager.remove(2, 3);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests removing if the new start interval is inside the current interval")
    void testRemoveStartIntervalInsideCurrentInterval() {
        // If the new start interval is inside the current interval,
        // the current interval end value is updated with the new value
        // and change the new start value with the current interval end value
        // Example: interval = [[1, 2], [3, 5], [6, 8]; remove(4, 7)
        //          newIntervalList = [[1, 2], [3, 4], [7, 8]]
        expectedList.add(Arrays.asList(1, 2));
        expectedList.add(Arrays.asList(3, 4));
        expectedList.add(Arrays.asList(7, 8));
        actualList = DisjointedIntervalsManager.add(1, 2);
        actualList = DisjointedIntervalsManager.add(3, 5);
        actualList = DisjointedIntervalsManager.add(6, 8);
        actualList = DisjointedIntervalsManager.remove(4, 7);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests removing if the current interval is inside the interval to remove")
    void testRemoveEndIntervalInsideCurrentInterval() {
        // If the current interval is inside the interval to remove, we don't add it
        // If not, we add it
        // Example: interval = [[6, 13], [15, 16], [17, 18]]; remove(14, 17)
        //          newIntervalList = [[6, 13], [17, 18]]
        expectedList.add(Arrays.asList(6, 13));
        expectedList.add(Arrays.asList(17, 18));
        actualList = DisjointedIntervalsManager.add(6, 13);
        actualList = DisjointedIntervalsManager.add(15, 16);
        actualList = DisjointedIntervalsManager.add(17, 18);
        actualList = DisjointedIntervalsManager.remove(14, 17);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests the whole thing")
    void testWholeThing() {
        // add, 1, 5
        // remove, 2, 3
        // add, 6, 8
        // remove, 4, 7
        // add, 2, 7
        //
        // [[1, 5]]
        // [[1, 2], [3, 5]]
        // [[1, 2], [3, 5], [6, 8]]
        // [[1, 2], [3, 4], [7, 8]]
        // [[1, 8]]
        expectedList.add(Arrays.asList(1, 8));
        actualList = DisjointedIntervalsManager.add(1, 5);
        actualList = DisjointedIntervalsManager.remove(2, 3);
        actualList = DisjointedIntervalsManager.add(6, 8);
        actualList = DisjointedIntervalsManager.remove(4, 7);
        actualList = DisjointedIntervalsManager.add(2, 7);
        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Tests a valid input for an action")
    void testValidActionInput() {
        String action = DisjointedIntervalsManager.parseActionCommand("add");

        assertEquals("add", action);
    }

    @Test
    @DisplayName("Tests a valid input for an action with case sensitivity")
    void testValidActionInputWithCaseSensitivity() {
        String action = DisjointedIntervalsManager.parseActionCommand("AdD");

        assertEquals("add", action);
    }

    @Test
    @DisplayName("Tests an invalid input for an action")
    void testInvalidActionInput() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DisjointedIntervalsManager.parseActionCommand("");
        });

        String expectedMessage = "Invalid input: ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Tests an invalid command for an action")
    void testInvalidActionCommand() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DisjointedIntervalsManager.parseActionCommand("update");
        });

        String expectedMessage = "Invalid command: update";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Tests valid start and end interval values")
    void testValidStartAndEndIntervalValues() {
        int[] interval = DisjointedIntervalsManager.parseOperationCommand("1", "5");
        assertArrayEquals(new int[] {1, 5}, interval);
    }

    @Test
    @DisplayName("Tests valid start and end interval values")
    void testInvalidStartAndEndInput() {
        Exception exceptionStartValue = assertThrows(IllegalArgumentException.class, () -> {
            DisjointedIntervalsManager.parseOperationCommand("1.5", "2");
        });

        String expectedMessageStartValue = "Invalid input for start value: 1.5";
        String actualMessageStartValue = exceptionStartValue.getMessage();

        assertTrue(expectedMessageStartValue.contains(actualMessageStartValue));

        Exception exceptionEndValue = assertThrows(IllegalArgumentException.class, () -> {
            DisjointedIntervalsManager.parseOperationCommand("1", "2.5");
        });

        String expectedMessageEndValue = "Invalid input for end value: 2.5";
        String actualMessageEndValue = exceptionEndValue.getMessage();

        assertTrue(expectedMessageEndValue.contains(actualMessageEndValue));
    }

    @Test
    @DisplayName("Tests start value must be less than end value")
    void testStartValueLessThanEndValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DisjointedIntervalsManager.parseOperationCommand("3", "2");
        });

        String expectedMessage = "The first number, 3, must be less than the second number, 2";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Tests start value and end value must be positive")
    void testStartAndEndValueMustBePositive() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DisjointedIntervalsManager.parseOperationCommand("0", "0");
        });

        String expectedMessage = "Both numbers must be positive: 0, 0";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
