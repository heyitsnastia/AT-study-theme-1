package ru.study.topic6.support;

public final class UiConfig {
    public static final String BASE_URL = System.getProperty("ui.baseUrl", "http://localhost:8080")
            .replaceAll("/+$", "");
    public static final String USER = System.getProperty("ui.user", "admin");
    public static final String PASSWORD = System.getProperty("ui.password", "secret123");
    public static final boolean HEADLESS = Boolean.parseBoolean(System.getProperty("ui.headless", "true"));
    public static final long TIMEOUT_SECONDS = Long.parseLong(System.getProperty("ui.timeoutSeconds", "10"));

    private UiConfig() {
    }
}
