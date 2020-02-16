package ru.ArtemKritov.MSVLibrery.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ArtemKritov.MSVLibrery.Main;
import ru.ArtemKritov.MSVLibrery.data.URLList;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class MainController {


    public ListView list;
    public Button dowenButton;
    public Text stat;

    ArrayList<URLList> urlLists = Main.urlLists;
    ArrayList<Thread> threads = new ArrayList<>();
    ObservableList<CheckBox> checkBoxObservableList = FXCollections.observableArrayList();
    int size = 0;

    public static final Logger logger = LogManager.getLogger();

    public void initialize() {
        dowenButton.setDisable(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < urlLists.size(); i++) {
                    String s = "доступен";
                    try {
                        InputStream input = new URL(urlLists.get(i).getUrl()).openStream();

                    } catch (IOException e) {
                        s = "не доступен";
                    }
                    logger.info("initialize:" + urlLists.get(i).getName());
                    CheckBox checkBox = new CheckBox("(" + s + ") " + urlLists.get(i).getName());
                    int finalI = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stat.setText("обработка данных " + (finalI + 1 + "/") + urlLists.size());
                            checkBoxObservableList.add(checkBox);
                            list.setItems(checkBoxObservableList);
                            if (finalI + 1 == urlLists.size()) {
                                stat.setText("готов");
                                dowenButton.setDisable(false);
                            }
                        }
                    });

                }

            }
        });
        thread.start();

    }

    public void dowelledButton(ActionEvent actionEvent) {
        threads.clear();
        size = 0;
        dowenButton.setDisable(true);
        for (int i = 0; i < urlLists.size(); i++) {
            if (checkBoxObservableList.get(i).isSelected()) {
                int finalI = i;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dowelled(urlLists.get(finalI).getName(), urlLists.get(finalI).getUrl());
                    }
                });
                threads.add(thread);
            }
        }
        stat.setText("загрузка " + size + "/" + threads.size());
        for (Thread t :
                threads) {
            t.start();
        }

        if (size == threads.size()) {
            dowenButton.setDisable(false);
        }
    }


    void dowelled(String name, String url) {
        try {
            URL link = new URL(url);
            String[] nameURL = url.split("/");
            String home = System.getProperty("user.home");
            home = home + "\\Downloads\\MSVlibrary";
            File file = new File(home);
            if (!file.exists()) {
                file.mkdir();
                logger.trace("!mkdir");
            } else {
                logger.trace("mkdir");
            }
            logger.trace(home);
            String fileName = home + "\\(" + name + ") " + nameURL[nameURL.length - 1];

            InputStream in = new BufferedInputStream(link.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[999999];

            int n;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(response);
            fos.close();
            logger.info("завершено" + fileName);
        } catch (Exception e) {
            logger.error("load: ", e);
        }
        size++;
        stat.setText("загрузка " + size + "/" + threads.size());

        if (size == threads.size()) {
            dowenButton.setDisable(false);
            stat.setText("загрузка завершина");
        }

    }
}
