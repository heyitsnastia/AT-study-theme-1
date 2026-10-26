# Домашнее задание

12 задач по базовой Java. Используются Java 25, Gradle и JUnit.

Решения находятся в `Topic1.java`, примеры вызовов — в `Main.java`, тесты — в `Topic1Test.java`.

Запуск программы:

```bash
gradle run
```

Запуск всех тестов, кроме намеренно падающего примера:

```bash
gradle test
```

Тема 2, задача 1 — запуск трёх методов со случайными данными (`RandomMethodsTest`):

```bash
gradle test --tests 'ru.study.topic2.RandomMethodsTest' --rerun-tasks
```

Тема 2, задача 2 — проверка всех 12 методов (`AllMethodsTest`):

```bash
gradle test --tests 'ru.study.topic2.AllMethodsTest' --rerun-tasks
```

Тема 3, задача 1 — четыре теста с ассертами:

```bash
gradle topic3Task1 --rerun-tasks
```

В этой задаче один тест специально падает: метод `incorrectSum(2, 3)` возвращает `6` вместо `5`.
Поэтому `BUILD FAILED` здесь ожидаем. Остальные три теста проходят.

Тема 3, задача 2 — тесты из `AllMethodsTest`, в которых проверки через `if` заменены на ассерты:

```bash
gradle topic3Task2 --rerun-tasks
```

`--rerun-tasks` запускает тесты заново, даже если код не менялся.
После каждого прогона выводится `Test run is over`.

Если Gradle не установлен, вместо `gradle` можно использовать `./gradlew`.
