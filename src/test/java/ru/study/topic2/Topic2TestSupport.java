package ru.study.topic2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

public abstract class Topic2TestSupport {

    protected static final Random RANDOM = new Random();

    @BeforeEach
    void beforeTest() {
        System.out.println("========================Test method start");
    }

    @AfterEach
    void afterTest() {
        System.out.println("Test method end");
    }

}
