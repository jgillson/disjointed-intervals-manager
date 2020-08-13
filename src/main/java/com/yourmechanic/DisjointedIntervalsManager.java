package com.yourmechanic;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author John Gillson
 * @since 08/2020
 */
class DisjointedIntervalsManager {

    static final List<String> allowedCommands = Arrays.asList("add", "remove");

    private static List<List<Integer>> intervals = new ArrayList<>();

    /**
     * This method returns the List of Lists of disjointed intervals after adding an interval
     *
     * @param start the start of the interval
     * @param end the end of the interval
     * @return List of the Lists of disjointed intervals
     */
    static List<List<Integer>> add(int start, int end) {
        boolean hasNewIntervalBeenAdded = false;
        List<List<Integer>> newIntervalList = new ArrayList<>();

        // When adding an interval for the first time, add it to the newIntervalList
        // Example: add(1, 5)
        //          newIntervalList = [[1, 5]]
        if (intervals.isEmpty()) {
            newIntervalList.add(Arrays.asList(start, end));
        }
        else {
            for (List<Integer> interval : intervals) {
                int min = interval.get(0);
                int max = interval.get(1);

                // If the new start interval is greater than the current max interval,
                // add the current interval to the newIntervalList followed by the new interval
                // Example: interval = [1, 5]; add(6, 8)
                //          newIntervalList = [[1, 5], [6, 8]]
                if (start > max) {
                    newIntervalList.add(interval);
                }
                // If the new end interval is less than the current min interval,
                // add the new interval to the newIntervalList, followed by the current interval
                // Example: interval = [6, 8]; add(1, 5)
                //          newIntervalList = [[1, 5], [6, 8]]
                if (end < min) {
                    if (!hasNewIntervalBeenAdded) {
                        newIntervalList.add(Arrays.asList(start, end));
                    }
                    newIntervalList.add(interval);
                    hasNewIntervalBeenAdded = true;
                }
                // If there's a union with the new interval and the current min or max interval,
                // take the lowest start interval and highest end interval and add it to the newIntervalList
                // Example: interval = [[1, 2], [3, 4], [7, 8]; add(2, 7)
                //          newIntervalList = [[1, 8]]
                if ((start >= min && start <= max) || (end >= min && end <= max)) {
                    start = Math.min(start, min);
                    end = Math.max(end, max);
                }
            }
            // Add the new interval to the newIntervalList if not matching any previous conditions
            if (!hasNewIntervalBeenAdded) {
                newIntervalList.add(Arrays.asList(start, end));
            }
        }
        intervals = newIntervalList;
        System.out.println(intervals);

        return intervals;
    }

    /**
     * This method returns the List of Lists of disjointed intervals after removing an interval
     *
     * @param start the start of the interval
     * @param end the end of the interval
     * @return List of the Lists of disjointed intervals
     */
    static List<List<Integer>> remove(int start, int end) {
        List<List<Integer>> newIntervalList = new ArrayList<>();
        List<Integer> newInterval;

        for (List<Integer> interval : intervals) {
            int min = interval.get(0);
            int max = interval.get(1);

            // If the new start and end interval is inside the current interval,
            // the current interval is split and a new one is created and added to the newIntervalList
            // Example: interval = [[1, 5]]; remove(2, 3)
            //          newIntervalList = [[1, 2], [3, 5]]
            if ((start > min && start < max) && (end > min && end < max)) {
                newInterval = Arrays.asList(end, max);
                interval.set(1, start);

                newIntervalList.add(interval);
                newIntervalList.add(newInterval);
            // If the new start interval is inside the current interval,
            // the current interval end value is updated with the new value
            // and change the new start value with the current interval end value
            // Example: interval = [[1, 2], [3, 5], [6, 8]; remove(4, 7)
            //          newIntervalList = [[1, 2], [3, 4], [7, 8]]
            }
            else if (min < start && start < max) {
                interval.set(1, start);
                start = max;
                newIntervalList.add(interval);
            // If the new end interval is inside the current interval,
            // the current interval start value is updated with the new value
            // and change the new end value with the current interval start value
            // Example: interval = [[1, 2], [3, 5], [6, 8]; remove(4, 7)
            //          newIntervalList = [[1, 2], [3, 4], [7, 8]]
            }
            else if (min < end && end < max) {
                newIntervalList.add(Arrays.asList(end, max));
            // If the current interval is inside the interval to remove, we don't add it
            // If not, we add it
            // Example: interval = [[6, 13], [15, 16], [17, 18]]; remove(14, 17)
            //          newIntervalList = [[6, 13], [17, 18]]
            }
            else if (!(start <= min && max <= end)) {
                newIntervalList.add(interval);
            }
        }
        intervals = newIntervalList;
        System.out.println(intervals);

        return intervals;
    }

    /**
     * This method parses a passed in action command
     *
     * @param input the action to be taken on the interval
     * @return Valid action or IllegalArgumentException if invalid
     */
    static String parseActionCommand(String input) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        input = input.trim().toLowerCase();

        if (!DisjointedIntervalsManager.allowedCommands.contains(input)) {
            throw new IllegalArgumentException("Invalid command: " + input);
        }

        return input;
    }

    /**
     * This method parses passed in start and end values
     *
     * @param start the start interval
     * @param end the end interval
     * @return Array of two valid values or IllegalArgumentException if one or both invalid
     */
    static int[] parseOperationCommand(String start, String end) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher startMatcher = pattern.matcher(start);
        Matcher endMatcher = pattern.matcher(end);

        if (!startMatcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid input for start value: " + start);
        }
        if (!endMatcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid input for end value: " + end);
        }

        int startVal = Integer.parseInt(start);
        int endVal = Integer.parseInt(end);

        if (startVal > endVal) {
            throw new IllegalArgumentException(
                    "The first number, " + start + ", must be less than the second number, " + end);
        }
        if (startVal <= 0) {
            throw new IllegalArgumentException("Both numbers must be positive: " + start + ", " + end);
        }

        return new int[]{startVal, endVal};
    }
}
