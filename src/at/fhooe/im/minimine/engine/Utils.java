package at.fhooe.im.minimine.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Utils {

    public static String loadResource(String fileName) throws Exception {
        String result = "";
        File file = new File("resources",fileName);
        System.out.println(file.getAbsolutePath());
        try (InputStream in = new FileInputStream(file)) {
            result = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        }
        return result;
    }

}