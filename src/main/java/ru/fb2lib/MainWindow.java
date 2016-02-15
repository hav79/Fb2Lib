package ru.fb2lib;
/**
 * Created by hav on 09.01.16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.fb2lib.db.HibernateUtil;
import ru.fb2lib.db.Library;
import ru.fb2lib.gui.CenterViewController;
import ru.fb2lib.gui.MainWindowController;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

public class MainWindow extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private static Logger log = Logger.getLogger(MainWindow.class.getName());

    private Library library;

    public MainWindow() {
        library = new Library();
        for (Map.Entry<String, String> entries : System.getenv().entrySet()) {
            System.out.print(entries.getKey() + ": ");
            System.out.println(entries.getValue());
        }

//        System.out.println(System.getProperties());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FictionBook Library");

        initRootLayout();
        showCenterView();
        System.out.println(this.getParameters().getNamed().toString());
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindow.class.getResource("/forms/MainWindow.fxml"));

            rootLayout = loader.load();
            primaryStage.setScene(new Scene(rootLayout, 600, 400));
            MainWindowController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("Error!!!" + e);
        }
    }

    private void showCenterView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainWindow.class.getResource("/forms/CenterView.fxml"));
            rootLayout.setCenter(loader.load());

            CenterViewController controller = loader.getController();
            controller.setLibrary(this.library);
            controller.setResourceDir(MainWindow.class.getResource("/templates").getPath());
        } catch (IOException e) {
            System.out.println("Error!!!" + e);
        }
    }

    @Override
    public void stop() throws Exception {
//        HibernateUtil.getSessionFactory().close();
//        HibernateUtil.getEntityManagerFactory().
        HibernateUtil.getEntityManagerFactory().close();
        super.stop();
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
