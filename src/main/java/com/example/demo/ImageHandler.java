package com.example.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Класс ImageHandler предоставляет методы для загрузки изображения из указанного пути
 * и сохранения модифицированного изображения в указанный путь.
 */
public class ImageHandler {

    /**
     * Загрузка изображения из указанного пути.
     *
     * @param originalImagePath путь к исходному изображению.
     * @return объект BufferedImage, представляющий загруженное изображение.
     * @throws IOException если файл не существует, не является файлом или не может быть прочитан.
     */
    public static BufferedImage loadImage(String originalImagePath) throws IOException {
        LogUtil.logInfo("Загрузка изображения: " + originalImagePath);

        if (originalImagePath == null) {
            LogUtil.logError("Путь к изображению введен неправильно.", new IOException("Путь к изображению введен неправильно."));
            throw new IOException("Путь к изображению введен неправильно.");
        }

        // Проверка существования файла
        File inputFile = new File(originalImagePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            LogUtil.logError("Файл не существует или не является файлом: " + originalImagePath, new IOException("Файл не существует или не является файлом: " + originalImagePath));
            throw new IOException("Файл не существует или не является файлом: " + originalImagePath);
        }

        // Загрузка изображения
        BufferedImage image = ImageIO.read(inputFile);
        if (image == null) {
            LogUtil.logError("Не удалось загрузить изображение: " + originalImagePath, new IOException("Не удалось загрузить изображение: " + originalImagePath));
            throw new IOException("Не удалось загрузить изображение: " + originalImagePath);
        }

        LogUtil.logInfo("Изображение успешно загружено: " + originalImagePath);
        return image;
    }

    /**
     * Сохранение изображения в указанный путь.
     *
     * @param image      объект BufferedImage, который нужно сохранить.
     * @param outputPath путь, куда будет сохранено изображение.
     * @param format     формат изображения (например, "bmp", "png", "jpg").
     * @throws IOException если файл не может быть сохранен.
     */
    public static void saveImage(BufferedImage image, String outputPath, String format) throws IOException {
        LogUtil.logInfo("Сохранение изображения в путь: " + outputPath + ", формат: " + format);

        if (image == null) {
            LogUtil.logError("Изображение не может быть пустым.", new IOException("Изображение не может быть пустым."));
            throw new IOException("Изображение не может быть пустым.");
        }

        if (outputPath == null) {
            LogUtil.logError("Путь для сохранения изображения введен неправильно.", new IOException("Путь для сохранения изображения введен неправильно."));
            throw new IOException("Путь для сохранения изображения введен неправильно.");
        }

        // Сохранение изображения
        File outputFile = new File(outputPath);
        ImageIO.write(image, format, outputFile);

        LogUtil.logInfo("Изображение сохранено успешно: " + outputPath);
    }

    /**
     * Создание изображения, отображающее младшие биты синего канала каждого пикселя.
     *
     * @param image исходное изображение, из которого извлекаются младшие биты.
     * @return новое изображение, где каждый пиксель белый (1) или черный (0) в зависимости от младшего бита.
     * @throws IOException если входное изображение равно null.
     */
    public static BufferedImage createLSBImage(BufferedImage image) throws IOException {
        LogUtil.logInfo("Создание изображения младших битов.");

        if (image == null) {
            LogUtil.logError("Входное изображение не может быть пустым.", new IOException("Входное изображение не может быть пустым."));
            throw new IOException("Входное изображение не может быть пустым.");
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage lsbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int blue = rgb & 0xFF; // Извлекаем синий канал
                int lsb = blue & 0x1; // Извлекаем младший бит
                int newColor = (lsb == 1) ? 0xFFFFFF : 0x000000; // Белый для 1, черный для 0
                lsbImage.setRGB(x, y, newColor);
            }
        }

        LogUtil.logInfo("Изображение младших битов успешно создано.");
        return lsbImage;
    }
}
