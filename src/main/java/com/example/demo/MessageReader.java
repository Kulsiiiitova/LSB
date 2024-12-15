package com.example.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для чтения сообщений из текстового файла.
 * Предназначен для извлечения текста из указанного файла и обработки ошибок, связанных с отсутствием файла, пустым содержимым и прочими ситуациями.
 */
public class MessageReader {

    /**
     * Чтение сообщения из указанного текстового файла.
     *
     * @param messageFile путь к файлу, из которого нужно прочитать сообщение.
     * @return строка, содержащая содержимое файла.
     * @throws IOException если файл не существует, не является файлом, пуст или возникает ошибка при чтении.
     */
    public static String readMessageFromFile(String messageFile) throws IOException {
        LogUtil.logInfo("Чтение сообщения из файла: " + messageFile);

        // Проверка на пустой путь к файлу
        if (messageFile == null) {
            LogUtil.logError("Путь к файлу сообщения не может быть пустым.", new IOException("Путь к файлу сообщения не может быть пустым."));
            throw new IOException("Путь к файлу сообщения не может быть пустым.");
        }

        StringBuilder message = new StringBuilder();

        File file = new File(messageFile);

        // Проверка существования файла и его корректности
        if (!file.exists() || !file.isFile()) {
            LogUtil.logError("Файл не найден или не является файлом: " + messageFile, new IOException("Файл не найден или не является файлом: " + messageFile));
            throw new IOException("Файл не найден или не является файлом: " + messageFile);
        }

        // Чтение содержимого файла
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                message.append(scanner.nextLine());
                if (scanner.hasNextLine()) {
                    message.append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            LogUtil.logError("Ошибка при открытии файла: " + messageFile, e);
            throw new IOException("Ошибка при открытии файла: " + messageFile, e);
        }

        // Проверка на пустое содержимое файла
        if (message.isEmpty()) {
            LogUtil.logError("Файл сообщения пуст.", new IOException("Файл сообщения пуст."));
            throw new IOException("Файл сообщения пуст.");
        }

        LogUtil.logInfo("Сообщение успешно прочитано.");
        return message.toString();
    }
}
