import java.awt.event.ActionListener;

public class EventHandler {

    Panel panel;
    ActionListener fileButtonListener;
    ActionListener addClassPageListener;
    ActionListener viewCalenderListener;
    ActionListener addClassListener;
    ActionListener saveClassListener;

    public EventHandler(Panel panel) {
        this.panel = panel;
        this.setupActionListeners();
    }

    public void setupActionListeners() {
        this.addClassPageListener = (var1) -> {
            this.panel.updatePage(1);
            System.out.println("Switching to page 1");
        };
        this.fileButtonListener = (var0) -> System.out.println("This isn't set up yet");
        this.viewCalenderListener = (var1) -> this.panel.updatePage(0);
    }
}

