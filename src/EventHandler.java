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
    }
}

