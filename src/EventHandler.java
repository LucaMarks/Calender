import Dates.Date;
import Dates.DateCode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandler {

    Panel panel;

    ActionListener fileButtonListener;//page 2
    ActionListener addClassPageListener;//page 1
    ActionListener viewCalenderListener; //page 0

//page 0 listeners
    ActionListener leftButtonListener;
    ActionListener rightButtonListener;

//page 1 listeners
    ActionListener addClassListener;
    ActionListener saveClassListener;
    ActionListener saveAssignmentListener;
    ActionListener classDropDownListener;

    public EventHandler(Panel panel) {
        this.panel = panel;
        this.setupActionListeners();
    }

    public void setupActionListeners() {
        //header listeners
        addClassPageListener = (ActionEvent _) -> {panel.updatePage(1);System.out.println("Switching to page 1");};
        fileButtonListener = (ActionEvent _) -> System.out.println("This isn't set up yet");
        viewCalenderListener = (ActionEvent _) -> panel.updatePage(0);

//page 0 listeners
        leftButtonListener = (ActionEvent _) -> {
            if(panel.currMonth - 1 < 0){panel.currMonth = 11;}
            else{panel.currMonth--;}
            panel.updatePage();
        };
        rightButtonListener = (ActionEvent _) -> {
            if(panel.currMonth + 1 > panel.months.length -1){panel.currMonth = 0;}
            else{panel.currMonth ++;}
            panel.updatePage();
        };
//page 1 listeners
        addClassListener = (ActionEvent _) -> {
            panel.addClassButton.setVisible(false);
            panel.addClassField.setVisible(true);
            panel.saveNewClassButton.setVisible(true);
//            panel.addAssignmentComponentsVisibility = -1;
        };

        saveClassListener = (ActionEvent _) -> {
            panel.addAssignmentComponentsVisibility = 0;
            panel.addClassButton.setVisible(true);
            panel.addClassField.setVisible(false);
            panel.saveNewClassButton.setVisible(false);
            Miscellaneous.saveSubject(panel.addClassField.getText());
            System.out.println("adding: " + panel.addClassField.getText() + " (eh 62)");
//            panel.currClassDropDown = panel.addClassField.getText();
//            System.out.println(panel.addClassField.getText());
            panel.currClassDropDown = panel.addClassField.getText();//this might lead to issues where the selected class is not actually the one stored in memory
            panel.updateClassDropDown();
            panel.updatePage();
        };

        saveAssignmentListener = (ActionEvent _) -> {
            try{int subjectIndex = Miscellaneous.getSubjectIndex(panel.currClassDropDown);

                String[] dueDateString = panel.assignmentDueDateField.getText().split("/");
                Date dueDate = new Date(new DateCode(dueDateString[0]), new DateCode(dueDateString[1]));
                if(panel.assignmentStartField.getText() == null){
                    panel.classList.get(subjectIndex).addAssessment(panel.assignmentNameField.getText(),dueDate);
                }

                else{
                    String[] startDateString = panel.assignmentStartField.getText().split("/");
                    Date startDate = new Date(new DateCode(startDateString[0]), new DateCode(startDateString[1]));
                    panel.classList.get(subjectIndex).addAssessment(panel.assignmentNameField.getText(), startDate, dueDate);}
            }
            catch(Exception _){}
            panel.addAssignmentComponentsVisibility = -1;
            panel.updatePage();
        };

        classDropDownListener = (ActionEvent _) -> {
            Object selected = panel.classDropDown.getSelectedItem();
            panel.currClassDropDown = selected != null ? selected.toString():null;
            System.out.println("Switching to " + panel.currClassDropDown + " (eh 89)");
            //we want to update page since viewAssignmentsPanel (comboBox) would change
            panel.addAssignmentComponentsVisibility = 0;
            panel.updatePage();
        };

    }

}

