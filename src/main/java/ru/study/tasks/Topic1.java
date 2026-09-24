package ru.study.tasks;

import java.util.ArrayList;
import java.util.List;

public class Topic1 {

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    public static boolean isPositive(int n) {
        return n >= 0 ? true : false;
    }

    public static String getGrade(int score) {
        if (score < 0 || score > 100) {
            return "Error";
        } else if (score <= 20) {
            return "E";
        } else if (score <= 40) {
            return "D";
        } else if (score <= 60) {
            return "C";
        } else if (score <= 80) {
            return "B";
        } else {
            return "A";
        }
    }

    public static String blastOff(int start) {
        StringBuilder result = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            result.append(i).append(" ");
        }

        result.append("Поехали!");
        return result.toString();
    }

    public static int sumToN(int n) {
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum = Math.addExact(sum, i);
        }

        return sum;
    }

    public static boolean hasBug(String[] messages) {
        for (String message : messages) {
            if ("Bug".equalsIgnoreCase(message)) {
                return true;
            }
        }

        return false;
    }

    public static String getEvenInRange(int start, int end) {
        StringBuilder result = new StringBuilder();

        for (long i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(i);
            }
        }

        return result.toString();
    }

    public static int findMax(int[] arr) {
        if (arr.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }

        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    public static String[] reverse(String[] arr) {
        String[] result = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - 1 - i];
        }

        return result;
    }

    public static double calcAverage(List<? extends Number> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Список не должен быть пустым");
        }

        double sum = 0;

        for (Number number : list) {
            sum = sum + number.doubleValue();
        }

        return sum / list.size();
    }

    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> result = new ArrayList<>();

        for (String name : list) {
            boolean matches = nameToRemove == null ? name == null : nameToRemove.equals(name);
            if (!matches) {
                result.add(name);
            }
        }

        return result;
    }

}
