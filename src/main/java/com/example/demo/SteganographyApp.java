package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Класс SteganographyApp реализует JavaFX приложение для работы со стеганографией.
 * Позволяет пользователям выбирать изображение и текстовый файл, скрывать сообщение внутри изображения и сохранять результат.
 */
public class SteganographyApp extends Application {

    /**
     * Отображение оригинальное изображения.
     */
    private final ImageView originalImageView = new ImageView();

    /**
     * Отображение изображения с встроенным сообщением.
     */
    private final ImageView modifiedImageView = new ImageView();

    /**
     * Метка для отображения статуса операций.
     */
    private final Label statusLabel = new Label();

    /**
     * Файл оригинального изображения, выбранный пользователем.
     */
    private File originalImageFile;

    /**
     * Файл текстового сообщения, выбранный пользователем.
     */
    private File messageFile;

    /**
     * Точка входа для JavaFX приложения.
     * Инициализация графического интерфейса пользователя.
     *
     * @param primaryStage главный этап приложения.
     */
    @Override
    public void start(Stage primaryStage) {
        LogUtil.logInfo("Запуск приложения SteganographyApp.");
        primaryStage.setTitle("Steganography Tool");
        LogUtil.logInfo("Главное окно приложения открыто.");

        // Создание кнопок
        Button selectImageButton = new Button("Выбрать изображение");
        Button selectMessageButton = new Button("Выбрать текстовый файл");
        Button embedMessageButton = new Button("Встроить сообщение");

        // Создание макета и добавление кнопок
        GridPane grid = createLayout(selectImageButton, selectMessageButton, embedMessageButton);

        // Настройка действий кнопок
        configureButtons(primaryStage, selectImageButton, selectMessageButton, embedMessageButton);

        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            LogUtil.logInfo("Приложение закрыто пользователем.");
        });
    }

    /**
     * Запуск приложения.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Создание макет с элементами интерфейса.
     *
     * @param selectImageButton   кнопка для выбора изображения.
     * @param selectMessageButton кнопка для выбора текстового файла.
     * @param embedMessageButton  кнопка для встраивания сообщения.
     * @return объект GridPane с элементами интерфейса.
     */
    private GridPane createLayout(Button selectImageButton, Button selectMessageButton, Button embedMessageButton) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        originalImageView.setFitWidth(300);
        originalImageView.setPreserveRatio(true);
        modifiedImageView.setFitWidth(300);
        modifiedImageView.setPreserveRatio(true);

        grid.add(selectImageButton, 0, 0);
        grid.add(selectMessageButton, 1, 0);
        grid.add(embedMessageButton, 2, 0);
        grid.add(new Label("Оригинальное изображение:"), 0, 1);
        grid.add(originalImageView, 0, 2);
        grid.add(new Label("Изображение с сообщением:"), 1, 1);
        grid.add(modifiedImageView, 1, 2);
        grid.add(statusLabel, 0, 3, 3, 1);

        return grid;
    }

    /**
     * Настройка действия кнопок.
     *
     * @param primaryStage        главный этап приложения.
     * @param selectImageButton   кнопка для выбора изображения.
     * @param selectMessageButton кнопка для выбора текстового файла.
     * @param embedMessageButton  кнопка для встраивания сообщения.
     */
    private void configureButtons(Stage primaryStage, Button selectImageButton, Button selectMessageButton, Button embedMessageButton) {
        selectImageButton.setOnAction(e -> handleSelectImage(primaryStage));
        selectMessageButton.setOnAction(e -> handleSelectMessage(primaryStage));
        embedMessageButton.setOnAction(e -> handleEmbedMessage(primaryStage));
    }

    /**
     * Обработка выбора изображения пользователем.
     *
     * @param primaryStage главный этап приложения.
     */
    private void handleSelectImage(Stage primaryStage) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));
            originalImageFile = fileChooser.showOpenDialog(primaryStage);

            if (originalImageFile != null) {
                originalImageView.setImage(new Image(originalImageFile.toURI().toString()));
                statusLabel.setText("Изображение выбрано: " + originalImageFile.getName());
                LogUtil.logInfo("Изображение выбрано: " + originalImageFile.getName());
            } else {
                statusLabel.setText("Выбор изображения отменен.");
                LogUtil.logWarning("Выбор изображения отменен.");
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка при выборе изображения: " + e.getMessage());
            LogUtil.logError("Ошибка при выборе изображения: " + e.getMessage(), e);
        }
    }

    /**
     * Обработка выбора текстового файла пользователем.
     *
     * @param primaryStage главный этап приложения.
     */
    private void handleSelectMessage(Stage primaryStage) {
        LogUtil.logInfo("Пользователь открыл файловый менеджер для выбора текстового файла.");

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            messageFile = fileChooser.showOpenDialog(primaryStage);

            if (messageFile != null) {
                statusLabel.setText("Текстовый файл выбран: " + messageFile.getName());
                LogUtil.logInfo("Текстовый файл выбран: " + messageFile.getName());
            } else {
                statusLabel.setText("Выбор текстового файла отменен.");
                LogUtil.logWarning("Выбор текстового файла отменен.");
            }
        } catch (Exception e) {
            statusLabel.setText("Ошибка при выборе текстового файла: " + e.getMessage());
            LogUtil.logError("Ошибка при выборе текстового файла: " + e.getMessage(), e);
        }
    }

    /**
     * Обработка процесса встраивания сообщения в изображение.
     *
     * @param primaryStage главный этап приложения.
     */
    private void handleEmbedMessage(Stage primaryStage) {
        if (originalImageFile == null || messageFile == null) {
            statusLabel.setText("Ошибка: выберите изображение и текстовый файл.");
            LogUtil.logWarning("Ошибка: выберите изображение и текстовый файл.");
            return;
        }

        try {
            LogUtil.logInfo("Пользователь открыл файловый менеджер для выбора директории для сохранения изображения.");

            // Выбор директории для сохранения изображения
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory == null) {
                LogUtil.logWarning("Выбор директории отменен.");
                statusLabel.setText("Выбор директории отменен.");
                return;
            }

            // Загружаем изображение
            BufferedImage originalImage = ImageHandler.loadImage(originalImageFile.getAbsolutePath());

            // Читаем сообщение из текстового файла
            String message = MessageReader.readMessageFromFile(messageFile.getAbsolutePath());

            // Конвертируем сообщение в массив битов
            int[] messageBits = BitConverter.convertMessageToBits(message);

            // Скрываем сообщение в изображении
            Steganography.hideTheMessage(messageBits, originalImage);

            // Создаем изображение с младшими битами
            BufferedImage lsbImage = ImageHandler.createLSBImage(originalImage);

            // Сохраняем изображение с младшими битами
            File outputFile_lsb = new File(selectedDirectory, "output_lsb.bmp");
            ImageHandler.saveImage(lsbImage, outputFile_lsb.getAbsolutePath(), "bmp");

            // Сохраняем модифицированное изображение
            File outputFile = new File(selectedDirectory, "output.bmp");
            ImageHandler.saveImage(originalImage, outputFile.getAbsolutePath(), "bmp");

            // Обновляем интерфейс
            modifiedImageView.setImage(new Image(outputFile_lsb.toURI().toString()));

            statusLabel.setText("Сообщение успешно встроено. Файл сохранен в: " + outputFile.getAbsolutePath());
            LogUtil.logInfo("Сообщение успешно встроено и сохранено в: " + outputFile.getAbsolutePath());
        } catch (IOException ex) {
            statusLabel.setText("Ошибка: " + ex.getMessage());
            LogUtil.logError("Ошибка: " + ex.getMessage(), ex);
        }
    }
}