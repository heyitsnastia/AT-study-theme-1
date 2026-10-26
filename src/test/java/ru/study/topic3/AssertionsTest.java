package ru.study.topic3;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.study.tasks.Topic1;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("topic3-task1")
public class AssertionsTest {

    @Test
    void testIsEven() {
        int number = 8;
        boolean expected = true;
        boolean actual = Topic1.isEven(number);

        assertEquals(expected, actual, "Число " + number + " должно быть чётным");
    }

    @Test
    void testRemoveSpecificName() {
        List<String> names = List.of("Анна", "Иван", "Анна", "Олег");
        List<String> expected = List.of("Иван", "Олег");
        List<String> actual = Topic1.removeSpecificName(names, "Анна");

        assertIterableEquals(expected, actual, "Из списка должны быть удалены все имена Анна");
    }

    @Test
    void testGetGrade() {
        int score = 85;
        String expected = "A";
        String actual = Topic1.getGrade(score);

        assertEquals(expected, actual, "Оценка для " + score + " баллов");
    }

    @Test
    @Tag("intentional-failure")
    void testIncorrectSum() {
        int first = 2;
        int second = 3;
        int expected = 5;
        int actual = incorrectSum(first, second);

        assertEquals(expected, actual, "Учебный пример ошибки: сумма " + first + " и " + second);
    }

    private static int incorrectSum(int first, int second) {
        return first + second + 1;
    }
}
