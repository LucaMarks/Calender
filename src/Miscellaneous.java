import javax.swing.*;

public class Miscellaneous {

    public static JPanel populateVerticalForm(JPanel fieldPanel, int fieldX, int fieldY, int fieldBuffY, int fieldHeight, int[] widths, JComponent[] components){
        fieldPanel.setLayout(null);

        for(int i = 0; i < components.length; i++){
            components[i].setBackground(Panel.colourPallet[2]);
            try{components[i].setBounds(fieldX, fieldY, widths[i], fieldHeight);}catch(Exception e){
                String message = e.toString();message += "\n";
                message += widths.length < components.length ? "width #" + i + " does not exist. Need " + i + " width values. Only given " + (widths.length - 1):
                        "Error on line 54 Miscellaneous\nCheck that values have been inputted properly with equal indexes";
                throw new IllegalArgumentException(message);
            }
            fieldPanel.add(components[i]);
            fieldY += fieldBuffY;
        }
        return fieldPanel;
    }
}
