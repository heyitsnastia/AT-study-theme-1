package ru.study.topic2;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.study.tasks.Topic1;

public class RandomMethodsTest extends Topic2TestSupport {

    @Test
    void testIsEven() {
        int number = RANDOM.nextInt(100) + 1;
        boolean result = Topic1.isEven(number);
        System.out.println("isEven(" + number + ") = " + result);
    }

    @RepeatedTest(20)
    void testCheckAccess() {
        int age = RANDOM.nextInt(100);
        String result = Topic1.checkAccess(age);
        System.out.println("checkAccess(" + age + ") = " + result);
    }

    @ParameterizedTest
    @MethodSource("randomScores")
    void testGetGrade(int score) {
        String result = Topic1.getGrade(score);
        System.out.println("getGrade(" + score + ") = " + result);
    }

    static Integer[] randomScores() {
        Integer[] scores = new Integer[10];

        for (int i = 0; i < scores.length; i++) {
            scores[i] = RANDOM.nextInt(101);
        }

        return scores;
    }
}
