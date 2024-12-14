package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс для логирования сообщений с использованием библиотеки Log4j.
 * Предоставляет методы для логирования информационных сообщений, предупреждений и ошибок.
 */
public class LogUtil {

    // Инициализация логгера
    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    /**
     * Логирование информационного сообщения.
     *
     * @param message Сообщение для логирования. Может содержать любую информацию,
     *                полезную для мониторинга состояния приложения.
     */
    public static void logInfo(String message) {
        logger.info(message);
    }

    /**
     * Логирование предупреждения.
     *
     * @param message Сообщение для логирования. Используется для указания на возможные
     *                проблемы, которые не влияют критически на выполнение программы.
     */
    public static void logWarning(String message) {
        logger.warn(message);
    }

    /**
     * Логирование ошибки с указанием исключения.
     *
     * @param message   Сообщение для логирования. Используется для описания ошибки или
     *                  проблемы, возникшей в процессе выполнения программы.
     * @param throwable Исключение, связанное с ошибкой. Может быть использовано для
     *                  предоставления детальной информации о причине ошибки (например, стек-трейс).
     */
    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
