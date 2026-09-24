package ru.study.tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.study.tasks.Topic1.*;

public class Topic1Test {

    @Test
    void testIsEven() {
        int number = 4;
        boolean even = isEven(number);

        assertTrue(even);
        assertFalse(isEven(7));
        assertTrue(isEven(0));
        assertTrue(isEven(-4));
        assertFalse(isEven(-3));
        assertTrue(isEven(Integer.MIN_VALUE));
        assertFalse(isEven(Integer.MAX_VALUE));
    }

    @Test
    void testCheckAccess() {
        int age = 19;
        String access = checkAccess(age);

        assertEquals("Allowed", access);
        assertEquals("Denied", checkAccess(18));
        assertEquals("Denied", checkAccess(17));
    }

    @Test
    void testIsPositive() {
        int number = 5;
        boolean positive = isPositive(number);

        assertTrue(positive);
        assertTrue(isPositive(0));
        assertFalse(isPositive(-5));
    }

    @Test
    void testGetGrade() {
        int score = 85;
        String grade = getGrade(score);

        assertEquals("A", grade);
        assertEquals("Error", getGrade(-1));
        assertEquals("E", getGrade(0));
        assertEquals("E", getGrade(20));
        assertEquals("D", getGrade(21));
        assertEquals("D", getGrade(40));
        assertEquals("C", getGrade(41));
        assertEquals("C", getGrade(60));
        assertEquals("B", getGrade(61));
        assertEquals("B", getGrade(80));
        assertEquals("A", getGrade(81));
        assertEquals("A", getGrade(100));
        assertEquals("Error", getGrade(101));
    }

    @Test
    void testBlastOff() {
        int start = 5;
        String countdown = blastOff(start);

        assertEquals("5 4 3 2 1 Поехали!", countdown);
        assertEquals("1 Поехали!", blastOff(1));
        assertEquals("Поехали!", blastOff(0));
        assertEquals("Поехали!", blastOff(-3));
    }

    @Test
    void testSumToN() {
        int number = 5;
        int sum = sumToN(number);

        assertEquals(15, sum);
        assertEquals(1, sumToN(1));
        assertEquals(0, sumToN(0));
        assertEquals(0, sumToN(-3));
        assertEquals(2147450880, sumToN(65535));
        assertThrows(ArithmeticException.class, () -> sumToN(65536));
    }

    @Test
    void testHasBug() {
        String[] messages = {"Info", "Bug", "Done"};
        boolean bugFound = hasBug(messages);

        assertTrue(bugFound);
        assertTrue(hasBug(new String[]{"BUG"}));
        assertTrue(hasBug(new String[]{"bug"}));
        assertFalse(hasBug(new String[]{"Info", "Debug", "Bug report"}));
        assertFalse(hasBug(new String[]{}));
        assertFalse(hasBug(new String[]{null}));
    }

    @Test
    void testGetEvenInRange() {
        int start = 2;
        int end = 5;
        String evenNumbers = getEvenInRange(start, end);

        assertEquals("2 4", evenNumbers);
        assertEquals("2 4 6", getEvenInRange(2, 6));
        assertEquals("-4 -2 0 2", getEvenInRange(-4, 3));
        assertEquals("2", getEvenInRange(2, 2));
        assertEquals("", getEvenInRange(3, 3));
        assertEquals("", getEvenInRange(5, 2));
        assertEquals("2147483646", getEvenInRange(2147483646, 2147483647));
    }

    @Test
    void testFindMax() {
        int[] numbers = {3, 9, 1};
        int max = findMax(numbers);

        assertEquals(9, max);
        assertEquals(-2, findMax(new int[]{-8, -2, -10}));
        assertEquals(7, findMax(new int[]{7}));
        assertEquals(5, findMax(new int[]{5, 5, 5}));
        assertThrows(IllegalArgumentException.class, () -> findMax(new int[]{}));
    }

    @Test
    void testReverse() {
        String[] original = {"One", "Two", "Zero"};
        String[] result = reverse(original);

        assertArrayEquals(new String[]{"Zero", "Two", "One"}, result);
        assertArrayEquals(new String[]{"One", "Two", "Zero"}, original);
        assertNotSame(original, result);
        assertArrayEquals(new String[]{}, reverse(new String[]{}));
        assertArrayEquals(new String[]{"One"}, reverse(new String[]{"One"}));
        assertArrayEquals(new String[]{null, "One"}, reverse(new String[]{"One", null}));
    }

    @Test
    void testCalcAverage() {
        List<Integer> numbers = List.of(1, 2, 3);
        double average = calcAverage(numbers);

        assertEquals(2.0, average, 0.000001);
        assertEquals(1.5, calcAverage(List.of(1, 2)), 0.000001);
        assertEquals(2.5, calcAverage(List.of(1.5, 3.5)), 0.000001);
        assertEquals(0.0, calcAverage(List.of(-3, 0, 3)), 0.000001);
        assertEquals(7.0, calcAverage(List.of(7)), 0.000001);
        assertThrows(IllegalArgumentException.class, () -> calcAverage(List.of()));
    }

    @Test
    void testRemoveSpecificName() {
        List<String> original = new ArrayList<>(List.of("Анна", "Иван", "Анна", "Олег"));
        String nameToRemove = "Анна";
        List<String> result = removeSpecificName(original, nameToRemove);

        assertEquals(List.of("Иван", "Олег"), result);
        assertEquals(List.of("Анна", "Иван", "Анна", "Олег"), original);
        assertNotSame(original, result);
        assertEquals(List.of(), removeSpecificName(List.of("Анна", "Анна"), "Анна"));
        assertEquals(List.of("Иван"), removeSpecificName(List.of("Иван"), "Анна"));
        assertEquals(List.of("анна"), removeSpecificName(List.of("Анна", "анна"), "Анна"));
        assertEquals(List.of(), removeSpecificName(List.of(), "Анна"));
        assertEquals(List.of("Анна"), removeSpecificName(Arrays.asList(null, "Анна"), null));
    }
}
