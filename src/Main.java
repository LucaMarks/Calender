import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {

        //clear the file for now, delete in final version
//        try{FileWriter writer = new FileWriter("./Info.txt"); writer.write("");}catch(Exception e){e.printStackTrace();}
        Miscellaneous.loadDate();
        new Panel();
    }
}

