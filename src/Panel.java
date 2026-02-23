
import Dates.Date;
import Dates.DateCode;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Panel {
    EventHandler eh;
    JFrame window;
    JPanel mainPanel;
    JPanel bodyPanel;
    JPanel headerPanel;

    public static ArrayList<Subject> classList = new ArrayList<>();
    public static String currClassDropDown;


// page 1 components
    public ArrayList<JComponent> addAssignmentComponents;
    public int addAssignmentComponentsVisibility = -1;

    JButton addClassButton;
    public JTextField addClassField;
    JButton saveNewClassButton;

    public JComboBox<String> classDropDown;
    JTextField assignmentNameField;
    JFormattedTextField assignmentDueDateField;
    JFormattedTextField assignmentStartField;

    public JComboBox<String> viewAssignmentsComboBox;

    int currPage = 0;
    int currMonth = 0;
    int width = 1200;
    int height = 750;
    public static final int regLineWidth = 2;

    Month[] months = new Month[12];
    Page calender = () -> drawCalenderPage();
    Page editClasses = () -> {
        try {drawAddClassPage();} catch (Exception e) {throw new RuntimeException(e);}
    };
    Page[] pages;

    public static final int headerMargin = 42;
    public static final int sectionWidth = 130;

    static Color[] dmList= new Color[]{Color.DARK_GRAY, Color.BLACK, Color.ORANGE, Color.getHSBColor(296.0F, 100.0F, 35.0F), Color.getHSBColor(242, 51, 91), Color.getHSBColor(351, 100, 89)};
    static Color[] lmList= new Color[]{Color.WHITE, Color.GRAY, Color.GREEN, Color.getHSBColor(296.0F, 100.0F, 35.0F), Color.getHSBColor(242, 51, 91), Color.getHSBColor(351, 100, 89)};
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
        this.pages[currPage].drawPage();
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
        window = new JFrame();
        mainPanel = new JPanel();
        window.setDefaultCloseOperation(3);

        window.setSize(this.width, this.height);
        window.setLocationRelativeTo(null);
        window.setBackground(Color.GRAY);
        window.setLayout(null);
        window.setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(colourPallet[0]);
        mainPanel.setBounds(0, 0, this.width, this.height);

        window.add(this.mainPanel);
        window.setVisible(true);

        bodyPanel = new JPanel();
        bodyPanel.setLayout(null);
        bodyPanel.setBounds(0, 45, this.width, this.height - 42);
        bodyPanel.setBackground(colourPallet[0]);
        mainPanel.add(this.bodyPanel);
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

    private void drawCalenderPage(){

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

        JPanel calenderPanel = new JPanel();
        calenderPanel.setLayout(null);
        calenderPanel.setBackground(colourPallet[3]);

        int calX = (int)(width * (1.0/16));
        int calY = 42;
        int calWidth = (int)(width - width * (1.0/8));
        int calHeight = height - (headerMargin * 3) - regLineWidth;
        calenderPanel.setBounds(calX, calY, calWidth, calHeight);

        int dayWidth = calWidth / 7;
        int dayHeight = calHeight / 6;

        //get starting day
        int startingDay = months[currMonth].startingDay;

        int prevMonthIndex = currMonth -1 > 0? currMonth-1:months.length -1;
        int nextMonthIndex = currMonth +1 < 12 ?currMonth+1:0;
        int currMonthDays = 1;
        int nextMonthStartDays = 1;

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                JButton dayButton = new JButton();
                dayButton.setLayout(null);
                dayButton.setBackground(colourPallet[1]);
                dayButton.setBounds(dayWidth * j, dayHeight * i, dayWidth, dayHeight);

                JLabel date = new JLabel();
                date.setBounds(5, 5, 15, 15);
                date.setForeground(colourPallet[3]);

                int calenderPos =(j+1) + (i*7);
                if(calenderPos < startingDay +1){
                    date.setText(String.valueOf(months[prevMonthIndex].days - ((startingDay +1) - calenderPos)));
                    dayButton.setBackground(colourPallet[0]);
                }
                else if(calenderPos > months[currMonth].days + months[currMonth].startingDay){
                    date.setText(String.valueOf(nextMonthStartDays++));
                    dayButton.setBackground(colourPallet[0]);
                }else{
                    date.setText(String.valueOf(currMonthDays++));

                    JLabel[] dates = showAssignments(new Date(new DateCode(String.valueOf(currMonth+1)), new DateCode(String.valueOf(currMonthDays-1))));
                    int x = 25;
                    int y = 5;
                    int width = dayWidth - 6;
                    int height = 15;
                    for(JLabel label: dates){
//                        System.out.println(label.toString());
                        label.setBounds(x, y, width, height);
                        x += height + 5;
                        dayButton.add(label);
                    }

                }

                dayButton.add(date);
                calenderPanel.add(dayButton);
            }
        }

        int overFlow = 42 - (months[currMonth].startingDay + months[currMonth].days);
//        System.out.println(overFlow);

        months[nextMonthIndex].startingDay = overFlow > 7? 14-overFlow:7-overFlow;

        overFlow = 42 - ((7 -startingDay) + months[prevMonthIndex].days);
        months[prevMonthIndex].startingDay = overFlow > 7? overFlow -7: overFlow;

        bodyPanel.add(calenderPanel);

    }
    public JLabel[] showAssignments(Date currDate){

        ArrayList<JLabel> labels = new ArrayList<>();
        for(int i = 0; i < classList.size(); i++){
            for(int j = 0; j < classList.get(i).assessmentNames.size(); j++){
//                System.out.println(classList.get(i).startDates.get(j).toString() + " = " + currDate.toString());
                if(classList.get(i).startDates.get(j).toString().equals(currDate.toString())){
                    labels.add(new JLabel("Start: " + classList.get(i).name + " -> " + classList.get(i).assessmentNames.get(j)));
                    labels.get(labels.size()-1).setForeground(colourPallet[4]);
                }

                if(classList.get(i).dueDates.get(j).toString().equals(currDate.toString())){
                    labels.add(new JLabel("Due: " + classList.get(i).name + " -> " + classList.get(i).assessmentNames.get(j)));
                    labels.get(labels.size()-1).setForeground(colourPallet[5]);
                }
            }
        }
        return labels.toArray(new JLabel[0]);
    }


    private void drawAddClassPage() throws Exception {
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



        JButton saveButton = new JButton("Save*");
        saveButton.setBackground(colourPallet[2]);
        saveButton.setBounds((int)(width * (1.0/16)), headerMargin * 12, 100, 50);
        saveButton.addActionListener(eh.saveButtonListener);
        bodyPanel.add(saveButton);

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

        if(classDropDown == null){updateClassDropDown();}
//        JComboBox<String> classDropDown = updateClassDropDown();
        addAssignmentComponents.add(classDropDown);

        boolean fieldVisibility = addAssignmentComponentsVisibility != -1 ? convertVisibility(addAssignmentComponentsVisibility) : false;

        JLabel assignmentNameLabel = new JLabel("Assignment Name");
        assignmentNameLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentNameLabel);

        assignmentNameField = new JTextField();
        assignmentNameField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentNameField);

        JLabel assignmentDueDateLabel = new JLabel("Due Date");
        assignmentDueDateLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentDueDateLabel);

//        assignmentDueDateField = new JTextField();
//        assignmentDueDateField.setVisible(fieldVisibility);

        MaskFormatter dueDateMask;
        try {dueDateMask = new MaskFormatter("##/##");} catch (ParseException ex) {throw new RuntimeException(ex);}
        dueDateMask.setPlaceholder("_");
        assignmentDueDateField = new JFormattedTextField(dueDateMask);
        assignmentDueDateField.setColumns(10);
        assignmentDueDateField.setVisible(fieldVisibility);

        addAssignmentComponents.add(assignmentDueDateField);



        JLabel assignmentStartDate = new JLabel("Start Date");
        assignmentStartDate.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentStartDate);

//        assignmentStartField = new JTextField();
        MaskFormatter startDateMask;
        try {startDateMask = new MaskFormatter("##/##");}catch(ParseException ex) {throw new RuntimeException(ex);}
        assignmentStartField = new JFormattedTextField(startDateMask);
        assignmentStartField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentStartField);

        JButton assignmentSaveButton = new JButton("Save");
        assignmentSaveButton.setVisible(fieldVisibility);
        assignmentSaveButton.addActionListener(eh.saveAssignmentListener);
        addAssignmentComponents.add(assignmentSaveButton);


        widths = new int[]{200, 125, 100, 125, 100, 125, 100, 125};

        JPanel addAssignmentPanel_ = Miscellaneous.populateVerticalForm(addAssignmentPanel, fieldX, fieldY, fieldYBuff, fieldHeight, widths, (JComponent[])addAssignmentComponents.toArray(new JComponent[0]));

        bodyPanel.add(addAssignmentPanel_);

        JPanel viewAssignmentsPanel = new JPanel();
        viewAssignmentsPanel.setLayout(null);
        viewAssignmentsPanel.setBackground(colourPallet[3]);
        viewAssignmentsPanel.setBounds((int)(width * (1.0/8)), headerMargin, 250, 350);



        System.out.println("Checking for " + currClassDropDown);
        int currClassIndex = Miscellaneous.getSubjectIndex(currClassDropDown);

        if(currClassIndex != -1) {
            viewAssignmentsComboBox = Miscellaneous.drawDropdown(5, 5, 200, 100, (classList.get(currClassIndex).assessmentNames.toArray(new String[0])));
            viewAssignmentsComboBox.addActionListener(eh.assignmentDropDownListener);
            viewAssignmentsPanel.add(viewAssignmentsComboBox);

            if(classList.get(currClassIndex).assessmentNames.size() > 0) {
                int assignmentIndex = classList.get(currClassIndex).currAssignmentIndex;


                Subject currSubject = classList.get(currClassIndex);
                ArrayList<JComponent> viewAssignmentComponents = new ArrayList<>();

                JLabel showAssignmentName = new JLabel(currSubject.assessmentNames.get(assignmentIndex));
                viewAssignmentComponents.add(showAssignmentName);

                JLabel showAssignmentDueDate = new JLabel("Due Date: " + currSubject.dueDates.get(assignmentIndex).toString());
                viewAssignmentComponents.add(showAssignmentDueDate);

                JLabel showAssignmentStartDates = new JLabel("Due Date: " + currSubject.startDates.get(assignmentIndex).toString());
                viewAssignmentComponents.add(showAssignmentStartDates);

                widths = new int[]{100, 100, 100};
                JPanel viewAssignmentsPanel_ = Miscellaneous.populateVerticalForm(viewAssignmentsPanel, 10, 110, 30, 25, widths, viewAssignmentComponents.toArray(new JComponent[0]));
                bodyPanel.add(viewAssignmentsPanel_);
            }
        }else{bodyPanel.add(viewAssignmentsPanel);}
    }
    private boolean convertVisibility(int input){
        switch(input){
//            case -1: return null;
            case 0: return true;
            case 1: return false;
            default: return false;
        }
    }
    //this orriginally returned JComboBox<String>
    public void updateClassDropDown(){

        String[] classNames = new String[classList.size()];
        for(int i = 0; i < classList.size(); i++){
            classNames[i] = classList.get(i).name;
        }

//        if(classDropDown != null){
//            System.out.println("this ran");
//            Object selected = classDropDown.getSelectedItem();
//            currClassDropDown = selected != null ? selected.toString():null;
//        }


        classDropDown = new JComboBox<>(classNames);
        classDropDown.setBackground(colourPallet[2]);
        classDropDown.addActionListener(eh.classDropDownListener);

        //i think you would run into issues cuz this is only run once, and is not in a listener

//        if(Panel.classList.size() == 1){System.out.println(curr)
    }


}

