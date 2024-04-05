package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class TextEditor extends Application {
    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        textArea = new TextArea();
        textArea.setPrefSize(600, 300);
        root.setCenter(textArea);

        VBox buttonContainer = new VBox(10);
        buttonContainer.setPadding(new Insets(10));
        buttonContainer.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(120);
        saveButton.setStyle("-fx-border-color: blue;");
        saveButton.setCursor(Cursor.HAND);
        saveButton.setOnMouseEntered(event -> saveButton.setCursor(Cursor.HAND));
        saveButton.setOnMouseEntered(event -> saveButton.setStyle("-fx-font-weight: bold; -fx-border-color: purple;"));
        saveButton.setOnMouseExited(event -> saveButton.setStyle("-fx-border-color: blue;"));
        saveButton.setOnAction(event -> saveDocument(primaryStage));

        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(120);
        loadButton.setStyle("-fx-border-color: green;");
        loadButton.setCursor(Cursor.HAND);
        loadButton.setOnMouseEntered(event -> loadButton.setCursor(Cursor.HAND));
        loadButton.setOnMouseEntered(event -> loadButton.setStyle("-fx-font-weight: bold; -fx-border-color: yellow;"));
        loadButton.setOnMouseExited(event -> loadButton.setStyle("-fx-border-color: green;"));
        loadButton.setOnAction(event -> loadDocument(primaryStage));

        Button browseButton = new Button("Browse Folder");
        browseButton.setPrefWidth(120);
        browseButton.setStyle("-fx-border-color: red;");
        browseButton.setCursor(Cursor.HAND);
        browseButton.setOnMouseEntered(event -> browseButton.setCursor(Cursor.HAND));
        browseButton.setOnMouseEntered(event -> browseButton.setStyle("-fx-font-weight: bold; -fx-border-color: orange;"));
        browseButton.setOnMouseExited(event -> browseButton.setStyle("-fx-border-color: red;"));
        browseButton.setOnAction(event -> browseFolder(primaryStage));

        buttonContainer.getChildren().addAll(saveButton, loadButton, browseButton);
        root.setBottom(buttonContainer);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Text Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveDocument(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Document");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                String text = textArea.getText();
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDocument(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Document");
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                String text = stringBuilder.toString();
                textArea.setText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void browseFolder(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Browse Folder");
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            StringBuilder stringBuilder = new StringBuilder();
            browseFolderRecursive(selectedDirectory, stringBuilder);
            String text = stringBuilder.toString();
            textArea.setText(text);
        }
    }

    private void browseFolderRecursive(File directory, StringBuilder stringBuilder) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    browseFolderRecursive(file, stringBuilder);
                } else {
                    stringBuilder.append(file.getAbsolutePath()).append("\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
