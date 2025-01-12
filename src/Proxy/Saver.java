package Proxy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Saver {
    private final String path = "/Users/annasheremet/IdeaProjects/Lesson8/src/Proxy/ObjectBox";
    Cache cache;

    public Saver(){}

    public Saver(Cache cache){
        this.cache = cache;
    }

    public void chackAndSave(Object object){
        if (cache.cacheType() == CacheType.FILE) {//если указано сохранить в файл
            Path dir = Paths.get(this.path);
            String fileName = cache.fileNamePrefix() + ".txt";
            try {
                Files.createDirectories(dir);
                Files.createFile(dir.resolve(fileName));//создаем файл в директории
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            saveObjectInFile(object, fileName); //записываем объект в файл
             if (cache.zip()){ //если указано архивирование
                 zipFile(fileName);
             }
        }
    }

    public void saveObjectInFile(Object object, String fileName){
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //архивируем файл
    public void zipFile(String fileName){
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(fileName + ".zip"));
            FileInputStream fis= new FileInputStream(fileName);)
        {
            ZipEntry entry1 = new ZipEntry(fileName);
            zout.putNextEntry(entry1);
            // считываем содержимое файла в массив byte
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);// добавляем содержимое к архиву
            zout.write(buffer);// закрываем текущую запись для новой записи
            zout.closeEntry();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

}
