package save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[]args){
        GameProgress game_one = new GameProgress(100, 90, 80, 70);
        GameProgress game_two = new GameProgress(90, 80, 70, 60);
        GameProgress game_three = new GameProgress(50, 40, 30, 20);

        saveGame("C://Games//savegames//save1.dat", game_one);
        saveGame("C://Games//savegames//save2.dat", game_two);
        saveGame("C://Games//savegames//save3.dat", game_three);

        String[] list = {"C://Games//savegames//save1.dat",
                        "C://Games//savegames//save2.dat",
                        "C://Games//savegames//save3.dat"};

        zipFiles("C://Games//savegames//zip.zip", list);

        File dir = new File("C://Games//savegames");

        //Удаляем файлы с сохранениями
        for (String way_bye : list) {
            File file = new File(way_bye);
            file.delete();
        }
    }

    public static void saveGame(String way, GameProgress game) {
        try (FileOutputStream save_progress = new FileOutputStream(way);
             ObjectOutputStream oos = new ObjectOutputStream(save_progress)) {
            //Произведем запись в файл
            oos.writeObject(game);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String way, String[] files) {
        for (String way_files : files) {

            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("C://Games//savegames//zip.zip"));
                 FileInputStream fis = new FileInputStream(way_files)) {
                ZipEntry entry = new ZipEntry("packed_save.txt");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
}