package installation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        StringBuilder history = new StringBuilder();

        //Создаем объект File для каталогов
        File src = new File("C://Games", "src");
        File res = new File("C://Games", "res");
        File save_games = new File("C://Games", "savegames");
        File temp = new File("C://Games", "temp");

        //Создаем каталоги
        directories(src, history);
        directories(res, history);
        directories(save_games, history);
        directories(temp, history);

        File main = new File("C://Games//src", "main");
        File test = new File("C://Games//src", "test");
        directories(main, history);
        directories(test, history);

        File Main = new File("C://Games//src//main", "Main.java");
        File Utils = new File("C://Games//src//main", "Utils.java");
        file_creation(Main, history);
        file_creation(Utils, history);

        File drawables = new File("C://Games//res", "drawables");
        File vectors = new File("C://Games//res", "vectors");
        File icons = new File("C://Games//res", "icons");
        directories(drawables, history);
        directories(vectors, history);
        directories(icons, history);

        File temp_file = new File("C://Games//temp", "temp.txt");
        file_creation(temp_file, history);

        try (FileOutputStream fos = new FileOutputStream("C://Games//temp//temp.txt")) {
            //byte[] bytes = history.toString().getBytes();
            fos.write(history.toString().getBytes());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void directories(File dir, StringBuilder log) {
        //Метод создания директории
        if (dir.mkdir())
            log.append("Каталог ").append(dir).append(" создан!"+ "\n");
        else log.append("Создание каталога ").append(dir).append(" не увенчалось успехом =(" + "\n");
    }

    public static void file_creation(File file, StringBuilder log) {
        //метод создания файлов
        try {
            if(file.createNewFile())
                log.append("Файл ").append(file).append(" успешно создан!" + "\n");
            else log.append("Файл ").append(file).append(" потерпел фиаско!" + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}