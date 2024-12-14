package com.example.demo;

import java.io.IOException;

/**
 * Класс для преобразования текстового сообщения в массив бит.
 */
public class BitConverter {

    /**
     * Преобразование заданного сообщения в массив бит.
     * Каждый символ сообщения преобразуется в его двоичное представление длиной 8 бит.
     *
     * @param message сообщение для преобразования в биты.
     * @return массив целых чисел, представляющий биты сообщения.
     * @throws IOException если сообщение равно null или пустое.
     */
    public static int[] convertMessageToBits(String message) throws IOException {
        LogUtil.logInfo("Преобразование сообщения в биты.");

        if (message == null || message.isEmpty()) {
            LogUtil.logError("Сообщение не должно быть пустым.",
                    new IOException("Сообщение не должно быть пустым."));
            throw new IOException("Сообщение не должно быть пустым.");
        }

        // Инициализируем массив для битов
        int[] messageBits = new int[message.length() * 8];
        int index = 0;

        for (char character : message.toCharArray()) {
            // Преобразуем символ в двоичную строку
            String binaryString = Integer.toBinaryString(character);

            // Дополняем двоичную строку до 8 бит
            StringBuilder binaryString8 = new StringBuilder(binaryString);
            while (binaryString8.length() < 8) {
                binaryString8.insert(0, '0'); // Добавляем ведущие нули
            }

            // Преобразуем двоичную строку в массив целых чисел
            for (char bit : binaryString8.toString().toCharArray()) {
                messageBits[index++] = Character.getNumericValue(bit);
            }
        }

        LogUtil.logInfo("Сообщение успешно преобразовано в биты.");
        return messageBits;
    }
}
