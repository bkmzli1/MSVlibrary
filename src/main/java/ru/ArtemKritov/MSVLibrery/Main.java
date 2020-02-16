package ru.ArtemKritov.MSVLibrery;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ArtemKritov.MSVLibrery.data.URLList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {
    public static final Logger logger = LogManager.getLogger();
    public static ArrayList<URLList> urlLists = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        logger.info("start:launch");
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("start:init");
        add("Microsoft Visual C++ 2010 (x86)", "https://download.microsoft.com/download/5/B/C/5BC5DBB3-652D-4DCE-B14A-475AB85EEF6E/vcredist_x86.exe");
        add("Обновление для Windows 7 для 64-разрядных (x64) систем (KB2264107)", "https://download.microsoft.com/download/D/E/8/DE82FBE9-AED7-44F9-BEB1-4D1812E4EE5F/Windows6.1-KB2264107-v2-x64.msu");
        add("Обновление для системы безопасности Windows 7 для систем на базе 64-разрядных (x64) процессоров (KB3035132)", "https://download.microsoft.com/download/F/7/E/F7E65399-210C-4C34-A9FB-EE9DBEB75308/Windows6.1-KB3035132-x64.msu");
        add("Обновление для системы безопасности Windows 7 для систем на базе 64-разрядных (x64) процессоров (KB3061518)", "https://download.microsoft.com/download/3/0/D/30DA5A7C-7C4C-4129-8AF3-4CB86CCB0799/Windows6.1-KB3061518-x64.msu");
        add("Обновление для системы безопасности Windows 7 для систем на базе 64-разрядных (x64) процессоров (KB3109560)", "https://download.microsoft.com/download/3/D/E/3DE7E986-2BF8-4306-B593-B3C640ED600F/Windows6.1-KB3109560-x64.msu");
        add("Веб-установщик исполняемых библиотек DirectX для конечного пользователя", "https://download.microsoft.com/download/1/7/1/1718CCC4-6315-4D8E-9543-8E28A4E18C4C/dxwebsetup.exe");
        logger.info("stop:init");
    }

    void add(String name, String url) {
        urlLists.add(new URLList(name, url));
    }

    @Override
    public void start(Stage stage) {
        logger.info("start loader FXML");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/main.fxml")));
        try {
            loader.load();
        } catch (IOException e) {
            logger.warn("load fxml", e);
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/main.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("MSVlibrary");
        InputStream inputStream = ClassLoader.class.getResourceAsStream("/img/icon.png");
        try {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } catch (NullPointerException e) {
            logger.warn("img null");
        }
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });
        stage.show();
        //this.follScren = stage.isMaximized();
        stage.setResizable(false);
        logger.info("stop loader FXML");
    }
}
