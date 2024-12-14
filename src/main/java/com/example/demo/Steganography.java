package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Класс Steganography предоставляет методы для сокрытия сообщений в изображениях
 * с использованием стеганографии.
 */
public class Steganography {

    /**
     * Кодирование длины сообщения в первых 8 пикселях изображения.
     *
     * @param length длина сообщения в битах
     * @param image  изображение, в которое будет закодирована длина сообщения
     * @throws IOException если длина сообщения превышает размер изображения
     */
    private static void encodeMessageLength(int length, BufferedImage image) throws IOException {
        LogUtil.logInfo("Длина кодирования сообщения: " + length);
        if (length > image.getWidth() * image.getHeight()) {
            LogUtil.logError("Длина сообщения превышает допустимый размер.", new IOException("Длина сообщения превышает допустимый размер."));
            throw new IOException("Длина сообщения превышает допустимый размер.");
        }
        String binaryLength = Integer.toBinaryString(length);
        while (binaryLength.length() < 8) {
            binaryLength = '0' + binaryLength;
        }

        for (int i = 0; i < 8; i++) {
            int bit = Character.getNumericValue(binaryLength.charAt(i));
            encodeBitInPixel(image, 0, i, bit);
        }
        LogUtil.logInfo("Длина сообщения успешно закодирована.");
    }

    /**
     * Кодирование одного бита в указанном пикселе изображения.
     *
     * @param image изображение для кодирования
     * @param x     координата X пикселя
     * @param y     координата Y пикселя
     * @param bit   значение бита для кодирования (0 или 1)
     */
    private static void encodeBitInPixel(BufferedImage image, int x, int y, int bit) {
        int pixel = image.getRGB(x, y);

        // Извлечение значений цветовых каналов
        int alpha = (pixel >> 24) & 255;
        int red = (pixel >> 16) & 255;
        int green = (pixel >> 8) & 255;
        int blue = pixel & 255;

        // Модификация синего канала
        blue = (blue & 0b11111110) | bit;

        // Сборка нового пикселя
        int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
        image.setRGB(x, y, newPixel);
    }

    /**
     * Кодирование сообщения в массиве битов в пикселях изображения.
     *
     * @param bits  массив битов сообщения
     * @param image изображение для кодирования
     * @throws IOException если возникает ошибка при кодировании
     */
    private static void encodeMessageBits(int[] bits, BufferedImage image) throws IOException {
        LogUtil.logInfo("Кодирование битов сообщения в изображение.");
        int width = image.getWidth();
        int height = image.getHeight();

        int bitIndex = 0;
        int bitCount = bits.length;

        for (int x = 0; x < width && bitIndex < bitCount; x++) {
            for (int y = 0; y < height && bitIndex < bitCount; y++) {
                if (x == 0 && y < 8) continue; // Пропустить первые 8 пикселей
                encodeBitInPixel(image, x, y, bits[bitIndex]);
                bitIndex++;
            }
        }
        LogUtil.logInfo("Биты сообщения успешно закодированы.");
    }

    /**
     * Скрытие сообщения в изображении.
     *
     * @param bits     массив битов сообщения
     * @param theImage изображение, в которое будет скрыто сообщение
     * @throws IOException если сообщение или изображение недействительны
     */
    public static void hideTheMessage(int[] bits, BufferedImage theImage) throws IOException {
        LogUtil.logInfo("Скрытие сообщения на изображении.");
        if (bits == null || bits.length == 0) {
            LogUtil.logError("Биты сообщения нул или пустые.", new IOException("Биты сообщения не могут быть нулем или пустыми."));
            throw new IOException("Биты сообщения не могут быть нулем или пустыми.");
        }
        if (theImage == null) {
            LogUtil.logError("Изображение не может быть нулем.", new IOException("Изображение не может быть нулем."));
            throw new IOException("Изображение не может быть нулем.");
        }

        int messageLength = bits.length / 8;

        // Кодирования длины сообщения
        encodeMessageLength(messageLength, theImage);

        // Кодирование сообщения
        encodeMessageBits(bits, theImage);

        LogUtil.logInfo("Сообщение успешно скрыто.");
    }
}
