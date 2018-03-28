package pl.karolinakwiecien.models.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class SaveToFile {
    private File file;

    public void saveToFile(String text) {
        file = new File("C:\\Users\\Karolina\\Desktop\\weather.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.write(file.toPath(), (text + "\r\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
