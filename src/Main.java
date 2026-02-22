import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {

        //clear the file for now, delete in final version
//        try{FileWriter writer = new FileWriter("./Info.txt"); writer.write("");}catch(Exception e){e.printStackTrace();}
        Miscellaneous.loadData();
        System.out.println();
        new Panel();
    }
}

/*
    Two bugs that need to be fixed in add class screen
    Adding a new class causes the assignments panel to disapear even when it shoulden't
        -> this is cuz the page updates as a whole
        -> Consider making a method like we did with updateClassDropDown()
    The view assignments drop down dosen't change because the page reloads every time the user selects a new drop down item
        -> We fixed this with the other drop down (see the listener for the other drop down)
        -> this issue happens bceause the user selects a page resets as a whole
    -> adding a nother method to update the drop down menu would probably be useful
        -> we only want to update the drop down menu when a new assignment is added, or the user changes the class from the 1st drop down
 */