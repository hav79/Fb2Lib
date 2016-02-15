package ru.fb2lib.gui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ru.fb2lib.MainWindow;
import ru.fb2lib.db.Library;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by hav on 09.01.16.
 */
public class MainWindowController {

    private Library library;
    private MainWindow mainApp;

    @FXML
    MenuItem addBook;
    @FXML
    MenuItem addFolder;

    @FXML
    Label statusText;
    @FXML
    ProgressBar progressBar;

    @FXML
    private void initialize() {
    }


    public void setMainApp(MainWindow mainApp) {
        this.mainApp = mainApp;
        this.library = mainApp.getLibrary();
    }

    @FXML
    private void handleAddBook() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FictionBook files (*.fb2)", "*.fb2");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if (file != null) {
            library.addBookFromFile(file.getAbsolutePath());
        }
    }

    @FXML
    private void handleAddFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(mainApp.getPrimaryStage());
        if (dir == null)
            return;

        try {
            final List<Path> files = Files.walk(dir.toPath())
                    .filter(f -> f.toString().endsWith(".fb2"))
                    .collect(Collectors.toList());
            Task<Void> addBooksTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    progressBar.setVisible(true);
                    updateMessage("Adding books...");
                    for (int i = 0; i < files.size(); i++) {
                        library.addBookFromFile(files.get(i).toString());
                        updateProgress(i, files.size());
                    }
                    updateProgress(0, 0);
                    updateMessage("Ready");
                    progressBar.setVisible(false);
                    return null;
                }
            };
            progressBar.progressProperty().bind(addBooksTask.progressProperty());
            statusText.textProperty().bind(addBooksTask.messageProperty());
            new Thread(addBooksTask).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
