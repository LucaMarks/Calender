
import Dates.Date;
import Dates.DateCode;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 11);

    static Color[] dmList= new Color[]{Theme.current.bg, Theme.current.border, Theme.current.accent, Theme.current.surface, Theme.current.start, Theme.current.due};
    static Color[] lmList= new Color[]{Theme.current.bg, Theme.current.border, Theme.current.accent, Theme.current.surface, Theme.current.start, Theme.current.due};
    public static Color[] colourPallet = lmList;

    public static Border INPUT_BORDER = new CompoundBorder(
            new LineBorder(Theme.current.border, 1, true),
            new EmptyBorder(4, 8, 4, 8)
    );

    private boolean isDarkMode = false;

    public Panel() {
        this.pages = new Page[]{this.calender, this.editClasses};
        this.eh = new EventHandler(this);
        this.applyTheme();
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
        window.setBackground(Theme.current.bg);
        window.setLayout(null);
        window.setResizable(false);
        window.setTitle("Calender");

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Theme.current.bg);
        mainPanel.setBounds(0, 0, this.width, this.height);

        window.add(this.mainPanel);
        window.setVisible(true);

        bodyPanel = new JPanel();
        bodyPanel.setLayout(null);
        bodyPanel.setBounds(0, 45, this.width, this.height - 42);
        bodyPanel.setBackground(Theme.current.bg);
        mainPanel.add(this.bodyPanel);
    }

    private void drawHeader() {
        if (headerPanel != null) {
            mainPanel.remove(headerPanel);
        }
        headerPanel = new JPanel();
        headerPanel.setLayout((LayoutManager)null);
        headerPanel.setBackground(Theme.current.surface);
        headerPanel.setBounds(0, 0, this.width, headerMargin);

        JPanel headerLine = new JPanel();
        headerLine.setBackground(Theme.current.accent);
        headerLine.setBounds(0, headerMargin - regLineWidth, width, regLineWidth);
        headerPanel.add(headerLine);

        String[][] names = new String[][]{{"File", "2"}, {"Add/Edit", "1"}, {"View Calendar", "0"}, {"", "4"}};
        for(int i = 0; i < names.length; ++i) {
            JPanel line = new JPanel();
            line.setBackground(Theme.current.border);
            line.setBounds(130 * (i + 1), 0, 2, 42);
            this.headerPanel.add(line);
            JButton button = new JButton(names[i][0]);
            styleHeaderButton(button);
            button.setBounds(130 * i, 0, 130, 42);
            switch (Integer.parseInt(names[i][1])) {
                case 0 -> button.addActionListener(this.eh.viewCalenderListener);
                case 1 -> button.addActionListener(this.eh.addClassPageListener);
                case 2 -> button.addActionListener(this.eh.fileButtonListener);
            }

            this.headerPanel.add(button);
        }

        JButton themeButton = new JButton(isDarkMode ? "Light Mode" : "Dark Mode");
        styleSecondaryButton(themeButton);
        themeButton.setBounds(width - 140, 6, 120, 30);
        themeButton.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            applyTheme();
            drawHeader();
            updatePage();
        });
        headerPanel.add(themeButton);

        this.mainPanel.add(this.headerPanel);
        this.mainPanel.revalidate();
        this.mainPanel.repaint();
    }

    private void drawCalenderPage(){

        JPanel monthChangePanel = new JPanel();
        monthChangePanel.setLayout(null);
        monthChangePanel.setBackground(Theme.current.bg);

        int monthChangePanelWidth = 320;
        monthChangePanel.setBounds((width - monthChangePanelWidth) / 2, (regLineWidth * 2), monthChangePanelWidth, 30);

        JLabel month = new JLabel(months[currMonth].month);
        month.setForeground(Theme.current.text);
        month.setFont(FONT_TITLE);
        month.setHorizontalAlignment(SwingConstants.CENTER);
        int navButtonWidth = 34;
        int navPadding = 6;
        int labelX = navButtonWidth + navPadding;
        int labelWidth = monthChangePanelWidth - (navButtonWidth + navPadding) * 2;
        month.setBounds(labelX, 0, labelWidth, 25);
        monthChangePanel.add(month);

        JButton left = new JButton("<");
        left.setBounds(0, 0, navButtonWidth, 28);
        styleSecondaryButton(left);
        left.addActionListener(eh.leftButtonListener);
        monthChangePanel.add(left);

        JButton right = new JButton(">");
        right.setBounds(monthChangePanelWidth - navButtonWidth, 0, navButtonWidth, 28);
        styleSecondaryButton(right);
        right.addActionListener(eh.rightButtonListener);
        monthChangePanel.add(right);

        bodyPanel.add(monthChangePanel);

        JPanel calenderPanel = new JPanel();
        calenderPanel.setLayout(null);
        calenderPanel.setBackground(Theme.current.surface);
        calenderPanel.setBorder(new LineBorder(Theme.current.border, 1, true));

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
                dayButton.setBackground(Theme.current.surface);
                dayButton.setBounds(dayWidth * j, dayHeight * i, dayWidth, dayHeight);
                dayButton.setBorder(new LineBorder(Theme.current.border, 1, true));
                dayButton.setFocusPainted(false);

                JLabel date = new JLabel();
                date.setBounds(5, 5, 15, 15);
                date.setForeground(Theme.current.text);
                date.setFont(FONT_SMALL);

                int calenderPos =(j+1) + (i*7);
                if(calenderPos < startingDay +1){
                    date.setText(String.valueOf(months[prevMonthIndex].days - ((startingDay +1) - calenderPos)));
                    dayButton.setBackground(Theme.current.surfaceAlt);
                    date.setForeground(Theme.current.muted);
                }
                else if(calenderPos > months[currMonth].days + months[currMonth].startingDay){
                    date.setText(String.valueOf(nextMonthStartDays++));
                    dayButton.setBackground(Theme.current.surfaceAlt);
                    date.setForeground(Theme.current.muted);
                }else{
                    date.setText(String.valueOf(currMonthDays++));
                    if (isDarkMode) {
                        dayButton.setBackground(Theme.current.currentDayBg);
                        dayButton.setBorder(new LineBorder(Theme.current.accent, 1, true));
                    }

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
                    labels.get(labels.size()-1).setForeground(Theme.current.start);
                    labels.get(labels.size()-1).setFont(FONT_SMALL);
                }

                if(classList.get(i).dueDates.get(j).toString().equals(currDate.toString())){
                    labels.add(new JLabel("Due: " + classList.get(i).name + " -> " + classList.get(i).assessmentNames.get(j)));
                    labels.get(labels.size()-1).setForeground(Theme.current.due);
                    labels.get(labels.size()-1).setFont(FONT_SMALL);
                }
            }
        }
        return labels.toArray(new JLabel[0]);
    }


    private void drawAddClassPage() throws Exception {
        JPanel addClassPanel = new JPanel();
        addClassPanel.setLayout(null);
        addClassPanel.setBackground(Theme.current.surface);
        addClassPanel.setBorder(new LineBorder(Theme.current.border, 1, true));
        addClassPanel.setBounds((int)(width * (3.0/4)), headerMargin, 250, 125);

        ArrayList<JComponent> addClassComponents = new ArrayList<>();

        int fieldX = 0;
        int fieldY = 0;
        int fieldYBuff = 0;
        int fieldHeight = 35;
        int[] widths = {100, 100};

        addClassButton = new JButton("Add Class");
        styleSecondaryButton(addClassButton);
        addClassButton.addActionListener(eh.addClassListener);
        addClassComponents.add(addClassButton);

        addClassField = new JTextField();
        styleTextField(addClassField);
        addClassField.setVisible(false);
        addClassComponents.add(addClassField);


        JPanel addClassPanel_ = Miscellaneous.populateVerticalForm(addClassPanel, fieldX, fieldY, fieldYBuff, fieldHeight, widths, (JComponent[])addClassComponents.toArray(new JComponent[0]));


        saveNewClassButton = new JButton("Save");
        saveNewClassButton.setVisible(false);
        stylePrimaryButton(saveNewClassButton);
        saveNewClassButton.setBounds(0, 55, 100, 35);
        saveNewClassButton.addActionListener(eh.saveClassListener);
        addClassPanel_.add(saveNewClassButton);



        JButton saveButton = new JButton("Save*");
        stylePrimaryButton(saveButton);
        saveButton.setBounds((int)(width * (1.0/16)), headerMargin * 12, 100, 50);
        saveButton.addActionListener(eh.saveButtonListener);
        bodyPanel.add(saveButton);

        bodyPanel.add(addClassPanel_);



        JPanel addAssignmentPanel = new JPanel();
        addAssignmentPanel.setLayout(null);
        addAssignmentPanel.setBackground(Theme.current.surface);
        addAssignmentPanel.setBorder(new LineBorder(Theme.current.border, 1, true));
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
        styleLabel(assignmentNameLabel);
        assignmentNameLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentNameLabel);

        assignmentNameField = new JTextField();
        styleTextField(assignmentNameField);
        assignmentNameField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentNameField);

        JLabel assignmentDueDateLabel = new JLabel("Due Date");
        styleLabel(assignmentDueDateLabel);
        assignmentDueDateLabel.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentDueDateLabel);

//        assignmentDueDateField = new JTextField();
//        assignmentDueDateField.setVisible(fieldVisibility);

        MaskFormatter dueDateMask;
        try {dueDateMask = new MaskFormatter("##/##");} catch (ParseException ex) {throw new RuntimeException(ex);}
        dueDateMask.setPlaceholder("_");
        assignmentDueDateField = new JFormattedTextField(dueDateMask);
        assignmentDueDateField.setColumns(10);
        styleTextField(assignmentDueDateField);
        assignmentDueDateField.setVisible(fieldVisibility);

        addAssignmentComponents.add(assignmentDueDateField);



        JLabel assignmentStartDate = new JLabel("Start Date");
        styleLabel(assignmentStartDate);
        assignmentStartDate.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentStartDate);

//        assignmentStartField = new JTextField();
        MaskFormatter startDateMask;
        try {startDateMask = new MaskFormatter("##/##");}catch(ParseException ex) {throw new RuntimeException(ex);}
        assignmentStartField = new JFormattedTextField(startDateMask);
        styleTextField(assignmentStartField);
        assignmentStartField.setVisible(fieldVisibility);
        addAssignmentComponents.add(assignmentStartField);

        JButton assignmentSaveButton = new JButton("Save");
        assignmentSaveButton.setVisible(fieldVisibility);
        stylePrimaryButton(assignmentSaveButton);
        assignmentSaveButton.addActionListener(eh.saveAssignmentListener);
        addAssignmentComponents.add(assignmentSaveButton);


        widths = new int[]{200, 125, 100, 125, 100, 125, 100, 125};

        JPanel addAssignmentPanel_ = Miscellaneous.populateVerticalForm(addAssignmentPanel, fieldX, fieldY, fieldYBuff, fieldHeight, widths, (JComponent[])addAssignmentComponents.toArray(new JComponent[0]));

        bodyPanel.add(addAssignmentPanel_);

        JPanel viewAssignmentsPanel = new JPanel();
        viewAssignmentsPanel.setLayout(null);
        viewAssignmentsPanel.setBackground(Theme.current.surface);
        viewAssignmentsPanel.setBorder(new LineBorder(Theme.current.border, 1, true));
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
                styleLabel(showAssignmentName);
                viewAssignmentComponents.add(showAssignmentName);

                JLabel showAssignmentDueDate = new JLabel("Due Date: " + currSubject.dueDates.get(assignmentIndex).toString());
                styleLabel(showAssignmentDueDate);
                viewAssignmentComponents.add(showAssignmentDueDate);

                JLabel showAssignmentStartDates = new JLabel("Start Date: " + currSubject.startDates.get(assignmentIndex).toString());
                styleLabel(showAssignmentStartDates);
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
        styleComboBox(classDropDown);
        classDropDown.addActionListener(eh.classDropDownListener);

        //i think you would run into issues cuz this is only run once, and is not in a listener

//        if(Panel.classList.size() == 1){System.out.println(curr)
    }

    private void styleLabel(JLabel label) {
        label.setForeground(Theme.current.text);
        label.setFont(FONT_BODY);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBackground(Theme.current.surface);
        comboBox.setForeground(Theme.current.text);
        comboBox.setFont(FONT_BODY);
        comboBox.setBorder(INPUT_BORDER);
    }

    private void styleTextField(JTextField field) {
        field.setBackground(Theme.current.surface);
        field.setForeground(Theme.current.text);
        field.setFont(FONT_BODY);
        field.setBorder(INPUT_BORDER);
        field.setMargin(new Insets(2, 6, 2, 6));
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(Theme.current.accent);
        button.setForeground(Color.WHITE);
        button.setFont(FONT_BODY);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Theme.current.accentBorder, 1, true));
        button.setOpaque(true);
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(Theme.current.surface);
        button.setForeground(Theme.current.text);
        button.setFont(FONT_BODY);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Theme.current.border, 1, true));
        button.setOpaque(true);
    }

    private void styleHeaderButton(JButton button) {
        button.setBackground(Theme.current.surface);
        button.setForeground(Theme.current.text);
        button.setFont(FONT_BODY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setOpaque(true);
    }

    private void applyTheme() {
        Theme.current = isDarkMode ? Theme.DARK : Theme.LIGHT;

        colourPallet = new Color[]{
                Theme.current.bg,
                Theme.current.border,
                Theme.current.accent,
                Theme.current.surface,
                Theme.current.start,
                Theme.current.due
        };
        INPUT_BORDER = new CompoundBorder(
                new LineBorder(Theme.current.border, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        );

        if (window != null) {
            window.setBackground(Theme.current.bg);
        }
        if (mainPanel != null) {
            mainPanel.setBackground(Theme.current.bg);
        }
        if (bodyPanel != null) {
            bodyPanel.setBackground(Theme.current.bg);
        }
    }


}
