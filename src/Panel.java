
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import javax.swing.*;

public class Panel {
    EventHandler eh;
    JFrame window;
    JPanel mainPanel;
    JPanel bodyPanel;
    JPanel headerPanel;

    public static ArrayList<Subject> classList = new ArrayList<>();

// page 1 components
    public ArrayList<JComponent> addAssignmentComponents;
    public int addAssignmentComponentsVisibility = -1;

    JButton addClassButton;
    public JTextField addClassField;
    JButton saveNewClassButton;

    int currPage = 0;
    int currMonth = 0;
    int width = 1200;
    int height = 750;
    public static final int regLineWidth = 2;

    Month[] months = new Month[12];
    Page calender = () -> this.drawCalenderPage();
    Page editClasses = () -> this.drawAddClassPage();
    Page[] pages;

    public static final int headerMargin = 42;
    public static final int sectionWidth = 130;

    static Color[] dmList= new Color[]{Color.DARK_GRAY, Color.BLACK, Color.ORANGE, Color.getHSBColor(296.0F, 100.0F, 35.0F)};
    static Color[] lmList= new Color[]{Color.WHITE, Color.GRAY, Color.GREEN, Color.getHSBColor(296.0F, 100.0F, 35.0F)};
    public static Color[] colourPallet = dmList;

    public Panel() {
        this.pages = new Page[]{this.calender, this.editClasses};
        this.eh = new EventHandler(this);
        this.setup();
        this.populateMonths();
        this.drawHeader();
        this.updatePage();
    }

    public void updatePage() {
        this.bodyPanel.removeAll();
        this.pages[this.currPage].drawPage();
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }
    public void updatePage(int pageTo) {
        this.currPage = pageTo;
        this.bodyPanel.removeAll();
        this.pages[this.currPage].drawPage();
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    public void populateMonths() {
        this.months[0] = new Month("January", 31, 4);
        this.months[1] = new Month("February", 28);
        this.months[2] = new Month("March", 31);
        this.months[3] = new Month("April", 30);
        this.months[4] = new Month("May", 31);
        this.months[5] = new Month("June", 30);
        this.months[6] = new Month("July", 31);
        this.months[7] = new Month("August", 31);
        this.months[8] = new Month("September", 30);
        this.months[9] = new Month("October", 31);
        this.months[10] = new Month("November", 30);
        this.months[11] = new Month("December", 31);
    }

    public void setup() {
        this.window = new JFrame();
        this.mainPanel = new JPanel();
        this.window.setDefaultCloseOperation(3);
        this.window.setSize(this.width, this.height);
        this.window.setLocationRelativeTo((Component)null);
        this.window.setBackground(Color.GRAY);
        this.window.setLayout((LayoutManager)null);
        this.window.setResizable(false);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout((LayoutManager)null);
        this.mainPanel.setBackground(colourPallet[0]);
        this.mainPanel.setBounds(0, 0, this.width, this.height);
        this.window.add(this.mainPanel);
        this.window.setVisible(true);
        this.bodyPanel = new JPanel();
        this.bodyPanel.setLayout((LayoutManager)null);
        this.bodyPanel.setBounds(0, 45, this.width, this.height - 42);
        this.bodyPanel.setBackground(colourPallet[0]);
        this.mainPanel.add(this.bodyPanel);
    }

    private void drawHeader() {
        headerPanel = new JPanel();
        headerPanel.setLayout((LayoutManager)null);
        headerPanel.setBackground(colourPallet[0]);
        headerPanel.setBounds(0, 0, this.width, headerMargin);

        JPanel headerLine = new JPanel();
        headerLine.setBackground(colourPallet[1]);
        headerLine.setBounds(0, headerMargin - regLineWidth, width, regLineWidth);
        headerPanel.add(headerLine);

        String[][] names = new String[][]{{"File", "2"}, {"Add/Edit", "1"}, {"View Calender", "0"}, {"", "4"}};
        for(int i = 0; i < names.length; ++i) {
            JPanel line = new JPanel();
            line.setBackground(colourPallet[1]);
            line.setBounds(130 * (i + 1), 0, 2, 42);
            this.headerPanel.add(line);
            JButton button = new JButton(names[i][0]);
            button.setBackground(colourPallet[2]);
            button.setBounds(130 * i, 0, 130, 42);
            switch (Integer.parseInt(names[i][1])) {
                case 0 -> button.addActionListener(this.eh.viewCalenderListener);
                case 1 -> button.addActionListener(this.eh.addClassPageListener);
                case 2 -> button.addActionListener(this.eh.fileButtonListener);
            }

            this.headerPanel.add(button);
        }

        this.mainPanel.add(this.headerPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    private void drawCalenderPage() {

        JPanel calenderPanel = new JPanel();
        calenderPanel.setLayout(null);
        calenderPanel.setBackground(colourPallet[3]);

        int calX = (int)(width * (1.0/16));
        int calY = 42;
        int calWidth = (int)(width - width * (1.0/8));
        int calHeight = height - (headerMargin * 2) - regLineWidth;
        calenderPanel.setBounds(calX, calY, calWidth, calHeight);

        JPanel monthChangePanel = new JPanel();
        monthChangePanel.setLayout(null);

        int monthChangePanelWidth = 250;
        monthChangePanel.setBounds((int)(width / 2), (regLineWidth * 2), monthChangePanelWidth, 30);

        JLabel month = new JLabel(months[currMonth].month);
        month.setBackground(colourPallet[0]);
        month.setBounds((monthChangePanelWidth / 2) - 15, 0, 50, 25);
        monthChangePanel.add(month);

        JButton left = new JButton("<");
        left.setBounds((int)(monthChangePanelWidth * (1.0/3)) - 25, 0, 50, 30);
        left.addActionListener(eh.leftButtonListener);
        monthChangePanel.add(left);

        JButton right = new JButton(">");
        right.setBounds((int)(monthChangePanelWidth * (2.0/3)), 0, 50, 30);
        right.addActionListener(eh.rightButtonListener);
        monthChangePanel.add(right);

        bodyPanel.add(monthChangePanel);
        /*
            Get the total # of days we need to have based on the starting day
            calculate the overFlow -> how many days we need to add at the end to get a 5x7 grid
         */
        int neededDays = months[currMonth].startingDay + months[currMonth].days;
        int overFlow = 35 - neededDays;
        //we need overflow + neededDays to add up to 35

        //update the starting day for the next month
        //remember overFlow is the days at the end that lead into the next month
        //overFlow is always going to be less then 7, will always be less than 5 since we are doing remainder 5
        System.out.println(months[currMonth].toString() + ", " + overFlow);
        if (currMonth + 1 < months.length - 1 && overFlow < 7 && overFlow > 0 && neededDays > 28) {
            months[currMonth + 1].startingDay = 7 - overFlow;
        }else if(currMonth + 1 > months.length){
                months[0].startingDay = 7 - overFlow;
        }else if(overFlow > 7){months[currMonth + 1].startingDay = 14 - overFlow;}
        else if(neededDays <= 28){months[currMonth + 1].startingDay = 0;}// if needed days <= 28 then the last week on a calender page is entirely for the next month, overflow is incorrectly calculated since it is divisor 5, but it this case only it would be divisor 4. There is very view cases where this happens
        else {
            months[currMonth + 1].startingDay = 0;
        }

        int totalDays = neededDays + overFlow;

        int currMonthCount = 1;
        int prevMonthOverFlow;
        if (this.currMonth - 1 > 0) {
            prevMonthOverFlow = this.months[this.currMonth - 1].days - this.months[this.currMonth].startingDay +1;
        } else {
            prevMonthOverFlow = this.months[this.months.length - 1].days - this.months[this.currMonth].startingDay +1;
        }

        System.out.println(prevMonthOverFlow);

        int dayWidth = calWidth / 7;
        int dayHeight = calHeight / 5;

        int nextMonthStart = 1;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 7; ++j) {
                JButton day = new JButton();
                day.setLayout(null);
                day.setBackground(colourPallet[1]);
                day.setBounds(dayWidth * j, dayHeight * i, dayWidth, dayHeight);

                int currCord = j + i * 7;

                JLabel date = new JLabel();
                date.setBackground(colourPallet[0]);
                date.setForeground(colourPallet[3]);
                date.setBounds(5, 5, 15, 15);

                if (currCord >= months[currMonth].startingDay && currCord <= months[currMonth].days + months[currMonth].startingDay -1) {//we need to do -1 because months[currMonth].days is not indexed from 0
                    date.setText(Integer.toString(currMonthCount++));
                } else if (currCord < this.months[this.currMonth].startingDay) {
                    date.setText(Integer.toString(prevMonthOverFlow++));
                } else {
                    date.setText(Integer.toString(nextMonthStart++));
                }

                day.add(date);
                calenderPanel.add(day);
            }
        }

        this.bodyPanel.add(calenderPanel);
        this.bodyPanel.revalidate();
        this.bodyPanel.repaint();
    }

    private void drawAddClassPage() {
        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(null);
        addClassPanel.setBackground(colourPallet[0]);
        addClassPanel.setBounds((int)(width * (3.0/4)), headerMargin, 250, 125);

        ArrayList<JComponent> addClassComponents = new ArrayList<>();

        int fieldX = 0;
        int fieldY = 0;
        int fieldYBuff = 0;
        int fieldHeight = 35;
        int[] widths = {100, 100};

        addClassButton = new JButton("Add Class");
        addClassButton.addActionListener(eh.addClassListener);
        addClassComponents.add(addClassButton);

        addClassField = new JTextField();
        addClassField.setVisible(false);
        addClassComponents.add(addClassField);


        JPanel addClassPanel_ = Miscellaneous.populateVerticalForm(addClassPanel, fieldX, fieldY, fieldYBuff, fieldHeight, widths, (JComponent[])addClassComponents.toArray(new JComponent[0]));


        saveNewClassButton = new JButton("Save");
        saveNewClassButton.setVisible(false);
        saveNewClassButton.setBackground(colourPallet[2]);
        saveNewClassButton.setBounds(0, 55, 100, 35);
        saveNewClassButton.addActionListener(eh.saveClassListener);
        addClassPanel_.add(saveNewClassButton);

        bodyPanel.add(addClassPanel_);


        JPanel addAssignmentPanel = new JPanel();
        addAssignmentPanel.setLayout(null);
        addAssignmentPanel.setBackground(colourPallet[3]);
        addAssignmentPanel.setBounds((int)(width * (1.0/2) - (sectionWidth / 2)), headerMargin, 250, 400);

         addAssignmentComponents = new ArrayList<>();

        fieldX = 10;
        fieldY = 10;
        fieldYBuff = 45;
        fieldHeight = 40;

        JComboBox<String> classDropDown = updateClassDropDown();
        addAssignmentComponents.add(classDropDown);

        boolean fieldVisibility = addAssignmentComponentsVisibility != -1 ? convertVisibility(addAssignmentComponentsVisibility) : false;

        JLabel assignmentNameLabel = new JLabel("Assignment Name");
        assignmentNameLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentNameLabel);

        JTextField assignmentNameField = new JTextField();
        assignmentNameField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentNameField);

        JLabel assignmentDueDateLabel = new JLabel("Due Date");
        assignmentDueDateLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentDueDateLabel);

        JTextField assignmentDueDateField = new JTextField();
        assignmentDueDateField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentDueDateField);

        JLabel asisgnmentStartLabel = new JLabel("Start Date");
        asisgnmentStartLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(asisgnmentStartLabel);

        JTextField assignmentStartField = new JTextField();
        assignmentStartField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentStartField);

        JButton assignmentSaveButton = new JButton("Save");
        assignmentSaveButton.setVisible(fieldVisibility);

        addAssignmentComponents.add(assignmentSaveButton);


        widths = new int[]{100, 75, 50, 75, 50, 75, 50, 70};

        JPanel addAssignmentPanel_ = Miscellaneous.populateVerticalForm(addAssignmentPanel, fieldX, fieldY, fieldYBuff, fieldHeight, widths, (JComponent[])addAssignmentComponents.toArray(new JComponent[0]));


        bodyPanel.add(addAssignmentPanel_);
    }
    private boolean convertVisibility(int input){
        switch(input){
//            case -1: return null;
            case 0: return true;
            case 1: return false;
            default: return false;
        }
    }
    private JComboBox<String> updateClassDropDown(){

        String[] classNames = new String[classList.size()];
        for(int i = 0; i < classList.size(); i++){
            classNames[i] = classList.get(i).name;
        }

        JComboBox<String> classDropDown = new JComboBox<>(classNames);
        classDropDown.setBackground(colourPallet[2]);
        return classDropDown;
    }


}

