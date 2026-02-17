
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel {
    EventHandler eh;
    JFrame window;
    JPanel mainPanel;
    JPanel bodyPanel;
    JPanel headerPanel;
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
    static Color[] dmList;
    static Color[] lmList;
    public static Color[] colourPallet;

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
        this.months[1] = new Month("February", 29);
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
        this.headerPanel = new JPanel();
        this.headerPanel.setLayout((LayoutManager)null);
        this.headerPanel.setBackground(colourPallet[0]);
        this.headerPanel.setBounds(0, 0, this.width, 42);
        JPanel headerLine = new JPanel();
        headerLine.setBackground(colourPallet[1]);
        headerLine.setBounds(0, 42, this.width, 2);
        this.headerPanel.add(headerLine);
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
        calenderPanel.setLayout((LayoutManager)null);
        calenderPanel.setBackground(colourPallet[3]);
        int calX = (int)((double)this.width * (double)0.0625F);
        int calY = 42;
        int calWidth = (int)((double)this.width - (double)this.width * (double)0.125F);
        int calHeight = this.height - 168 - 2;
        calenderPanel.setBounds(calX, calY, calWidth, calHeight);
        int neededDays = this.months[this.currMonth].startingDay + this.months[this.currMonth].startingDay;
        int overFlow = neededDays % 5;
        if (this.currMonth + 1 < this.months.length - 1 && overFlow < 7) {
            this.months[this.currMonth + 1].startingDay = 7 - overFlow;
        } else {
            this.months[0].startingDay = 7 - overFlow;
        }

        int var10000 = neededDays + overFlow;
        int dayWidth = calWidth / 7;
        int dayHeight = calHeight / 5;
        int currMonthCount = 1;
        int prevMonthOverFlow;
        if (this.currMonth - 1 > 0) {
            prevMonthOverFlow = this.months[this.currMonth - 1].days - this.months[this.currMonth].startingDay;
        } else {
            prevMonthOverFlow = this.months[this.months.length - 1].days - this.months[this.currMonth].startingDay;
        }

        int nextMonthStart = 0;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 7; ++j) {
                JButton day = new JButton();
                day.setLayout((LayoutManager)null);
                day.setBackground(colourPallet[1]);
                day.setBounds(dayWidth * j, dayHeight * i, dayWidth, dayHeight);
                int currCord = j + i * 7;
                JLabel date = new JLabel();
                date.setBackground(colourPallet[0]);
                date.setBounds(5, 5, 15, 15);
                if (currCord >= this.months[this.currMonth].startingDay && currCord <= this.months[this.currMonth].days + this.months[this.currMonth].startingDay) {
                    date.setText(Integer.toString(currMonthCount++));
                } else if (currCord >= this.months[this.currMonth].startingDay) {
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
    }

    static {
        dmList = new Color[]{Color.DARK_GRAY, Color.BLACK, Color.ORANGE, Color.getHSBColor(296.0F, 100.0F, 35.0F)};
        lmList = new Color[]{Color.WHITE, Color.GRAY, Color.GREEN, Color.getHSBColor(296.0F, 100.0F, 35.0F)};
        colourPallet = dmList;
    }
}

