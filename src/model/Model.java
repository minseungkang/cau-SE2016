package model;

import java.io.File;
import java.util.Scanner;

/**
 * Created by Elliad on 2016-05-08.
 */
public class Model {
    private static Model instance;
    public static File fileA;
    public static File fileB;

    private Model() {

    }
    public static Model getModel() {
       if(instance == null)
           instance = new Model();
        return instance;
    }

    public void readFiles(String fileAPath, String fileBPath) {
        this.fileA = new File(fileAPath);
        this.fileB = new File(fileBPath);
    }

}