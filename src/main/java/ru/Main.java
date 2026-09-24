package ru;

import ru.study.tasks.Topic1;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int number = 4;
        boolean even = Topic1.isEven(number);
        System.out.println("Число чётное: " + even);

        int age = 19;
        String access = Topic1.checkAccess(age);
        System.out.println("Доступ: " + access);

        int positiveNumber = 5;
        boolean positive = Topic1.isPositive(positiveNumber);
        System.out.println("Число больше или равно нулю: " + positive);

        int score = 85;
        String grade = Topic1.getGrade(score);
        System.out.println("Оценка: " + grade);

        int countdownStart = 5;
        String countdown = Topic1.blastOff(countdownStart);
        System.out.println("Обратный отсчёт: " + countdown);

        int n = 5;
        int sum = Topic1.sumToN(n);
        System.out.println("Сумма: " + sum);

        String[] messages = {"Info", "Bug", "Done"};
        boolean bugFound = Topic1.hasBug(messages);
        System.out.println("Найден Bug: " + bugFound);

        int start = 2;
        int end = 5;
        String evenNumbers = Topic1.getEvenInRange(start, end);
        System.out.println("Чётные числа: " + evenNumbers);

        int[] numbers = {3, 9, 1};
        int max = Topic1.findMax(numbers);
        System.out.println("Максимальное число: " + max);

        String[] words = {"One", "Two", "Zero"};
        String[] reversed = Topic1.reverse(words);
        System.out.println("Обратный порядок: " + Arrays.toString(reversed));

        List<Integer> values = List.of(1, 2, 3);
        double average = Topic1.calcAverage(values);
        System.out.println("Среднее арифметическое: " + average);

        List<String> names = List.of("Анна", "Иван", "Анна", "Олег");
        String nameToRemove = "Анна";
        List<String> remainingNames = Topic1.removeSpecificName(names, nameToRemove);
        System.out.println("Оставшиеся имена: " + remainingNames);
    }
}
