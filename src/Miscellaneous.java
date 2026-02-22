import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Miscellaneous {

    public static JComboBox<String> drawDropdown(int x, int y, int width, int height, String[] dropDownList) {

        JComboBox<String> dropDown = new JComboBox<>(dropDownList);
        dropDown.setBackground(Panel.colourPallet[2]);
        dropDown.setBounds(x, y, width, height);

        return dropDown;
    }

    public static JPanel populateVerticalForm(JPanel fieldPanel, int fieldX, int fieldY, int fieldBuffY, int fieldHeight, int[] widths, JComponent[] components) {
        fieldPanel.setLayout(null);

        for (int i = 0; i < components.length; i++) {
            components[i].setBackground(Panel.colourPallet[2]);
            try {
                components[i].setBounds(fieldX, fieldY, widths[i], fieldHeight);
            } catch (Exception e) {
                String message = e.toString();
                message += "\n";
                message += widths.length < components.length ? "width #" + i + " does not exist. Need " + i + " width values. Only given " + (widths.length - 1) :
                        "Error on line 54 Miscellaneous\nCheck that values have been inputted properly with equal indexes";
                throw new IllegalArgumentException(message);
            }
            fieldPanel.add(components[i]);
            fieldY += fieldBuffY;
        }
        return fieldPanel;
    }

    public static void saveSubject(String name) {
        Panel.classList.add(new Subject(name));
    }

    public static int getSubjectIndex(String subjectName) throws Exception {
        if (Panel.classList.size() > 0) {
            for (int i = 0; i < Panel.classList.size(); i++) {
                if (Panel.classList.get(i).name.equals(subjectName)) {
                    //we have a match
                    return i;
                }
            }
            throw new Exception("Name provided: " + subjectName + " is not in Panel.classList");
        }
        return -1;
    }

    public static void storeNewClass(String className) {
        try {
            Scanner scanner = new Scanner(new File("Info.txt"));

            BufferedWriter myWriter = new BufferedWriter(new FileWriter("Info.txt", true));
            if ((scanner.hasNext() && scanner.nextLine().equals(""))) {
                myWriter.newLine();
            }
            myWriter.write(className);
            scanner.close();
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./Info.txt"));
            for (Subject subject : Panel.classList) {
                writer.write(subject.name);
                writer.newLine();
                String[][] assessments = subject.getAssessments();
                for (int i = 0; i < subject.assessmentNames.size(); i++) {
                    writer.write("  " + assessments[i][0]);
                    writer.newLine();//name
                    writer.write("      " + assessments[i][1].toString());
                    writer.newLine();//due date
                    writer.write("      " + assessments[i][2].toString());
                    writer.newLine();//start date
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadData(){
//        Scanner scanner = new Scanner()
    }

}
