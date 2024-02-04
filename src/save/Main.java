package save;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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

        //Удаляем файлы с сохранениями
        for (String way_bye : list) {
            File file = new File(way_bye);
            file.delete();
        }

        //Чтение архива
        openZip("C://Games//savegames//zip.zip", "C://Games//savegames//");
        //Метод десериализации объекта
        openProgress(list);
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
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(way));
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

    public static void openZip(String way_files, String way_open) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(way_files))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); //Получаем название файла
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openProgress(String[] way) {
        //Deserialization
        GameProgress gameProgress = null;
        for (String way_files : way) {
            try (FileInputStream fis = new FileInputStream(way_files);
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                //Десериализуем объект и скастим его в класс
                gameProgress = (GameProgress) ois.readObject();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(gameProgress);
        }
    }
}