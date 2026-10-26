package ru.study.topic2;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.study.tasks.Topic1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("topic3-task2")
public class AllMethodsTest extends Topic2TestSupport {

    @RepeatedTest(10)
    void testIsEven() {
        boolean expected = RANDOM.nextBoolean();
        int number = RANDOM.nextInt(101) * 2;
        if (!expected) {
            number = number + 1;
        }

        boolean actual = Topic1.isEven(number);
        System.out.println("isEven(" + number + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Проверка чётности числа " + number);
    }

    @RepeatedTest(10)
    void testCheckAccess() {
        boolean allowed = RANDOM.nextBoolean();
        int age;
        String expected;
        if (allowed) {
            age = RANDOM.nextInt(81) + 19;
            expected = "Allowed";
        } else {
            age = RANDOM.nextInt(19);
            expected = "Denied";
        }

        String actual = Topic1.checkAccess(age);
        System.out.println("checkAccess(" + age + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Проверка доступа для возраста " + age);
    }

    @RepeatedTest(10)
    void testIsPositive() {
        boolean expected = RANDOM.nextBoolean();
        int number;
        if (expected) {
            number = RANDOM.nextInt(101);
        } else {
            number = -RANDOM.nextInt(100) - 1;
        }

        boolean actual = Topic1.isPositive(number);
        System.out.println("isPositive(" + number + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Проверка знака числа " + number);
    }

    @RepeatedTest(10)
    void testSumToN() {
        int number = RANDOM.nextInt(101);
        int expected = number * (number + 1) / 2;
        int actual = Topic1.sumToN(number);
        System.out.println("sumToN(" + number + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Сумма чисел от 1 до " + number);
    }

    @RepeatedTest(10)
    void testHasBug() {
        boolean expected = RANDOM.nextBoolean();
        String[] messages = {"Info", "Debug", "Done", "Bug report"};
        if (expected) {
            String[] variants = {"Bug", "BUG", "bug", "bUg"};
            messages[RANDOM.nextInt(messages.length)] = variants[RANDOM.nextInt(variants.length)];
        }

        boolean actual = Topic1.hasBug(messages);
        System.out.println("hasBug(" + Arrays.toString(messages) + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Поиск Bug в " + Arrays.toString(messages));
    }

    @RepeatedTest(10)
    void testFindMax() {
        int expected = RANDOM.nextInt(201) - 100;
        int[] numbers = new int[RANDOM.nextInt(5) + 1];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = expected - RANDOM.nextInt(100) - 1;
        }
        numbers[RANDOM.nextInt(numbers.length)] = expected;

        int actual = Topic1.findMax(numbers);
        System.out.println("findMax(" + Arrays.toString(numbers) + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Максимум в " + Arrays.toString(numbers));
    }

    @RepeatedTest(10)
    void testReverse() {
        String first = "One" + RANDOM.nextInt(100);
        String second = "Two" + RANDOM.nextInt(100);
        String third = "Three" + RANDOM.nextInt(100);
        String[] original = {first, second, third};
        String[] copy = original.clone();
        String[] expected = {third, second, first};

        String[] actual = Topic1.reverse(original);
        System.out.println("reverse(" + Arrays.toString(original) + "): " + Arrays.toString(actual));
        System.out.println("Ожидается: " + Arrays.toString(expected));
        assertArrayEquals(expected, actual, "Обратный порядок элементов массива");
        assertArrayEquals(copy, original, "Исходный массив должен остаться без изменений");
        assertNotSame(original, actual, "Метод должен вернуть новый массив, а не исходный");
    }

    @RepeatedTest(10)
    void testCalcAverage() {
        double expected = (RANDOM.nextInt(201) - 100) / 2.0;
        int distance = RANDOM.nextInt(20) + 1;
        List<Double> numbers = List.of(expected - distance, expected, expected + distance);

        double actual = Topic1.calcAverage(numbers);
        System.out.println("calcAverage(" + numbers + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, 0.000001, "Среднее арифметическое списка " + numbers);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/topic2/grades.csv", numLinesToSkip = 1)
    void testGetGrade(int score, String expected) {
        String actual = Topic1.getGrade(score);
        System.out.println("getGrade(" + score + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Оценка для количества баллов " + score);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/topic2/countdowns.csv", numLinesToSkip = 1)
    void testBlastOff(int start, String expected) {
        String actual = Topic1.blastOff(start);
        System.out.println("blastOff(" + start + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Обратный отсчёт от " + start);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/topic2/even-ranges.csv", numLinesToSkip = 1)
    void testGetEvenInRange(int start, int end, String expected) {
        String actual = Topic1.getEvenInRange(start, end);
        System.out.println("getEvenInRange(" + start + ", " + end + "): " + actual + ", ожидается " + expected);
        assertEquals(expected, actual, "Чётные числа в диапазоне от " + start + " до " + end);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/topic2/names.csv", numLinesToSkip = 1)
    void testRemoveSpecificName(String names, String nameToRemove, String expectedNames) {
        List<String> original = new ArrayList<>();
        if (!names.isEmpty()) {
            original.addAll(Arrays.asList(names.split(";")));
        }
        List<String> copy = new ArrayList<>(original);
        List<String> expected = new ArrayList<>();
        if (!expectedNames.isEmpty()) {
            expected.addAll(Arrays.asList(expectedNames.split(";")));
        }

        List<String> actual = Topic1.removeSpecificName(original, nameToRemove);
        System.out.println("removeSpecificName(" + original + ", " + nameToRemove + "): " + actual);
        System.out.println("Ожидается: " + expected);
        assertIterableEquals(expected, actual, "Удаление имени " + nameToRemove + " из " + copy);
        assertIterableEquals(copy, original, "Исходный список должен остаться без изменений");
        assertNotSame(original, actual, "Метод должен вернуть новый список, а не исходный");
    }
}
