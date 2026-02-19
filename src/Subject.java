import Dates.Date;

import java.util.ArrayList;

public class Subject {

    Panel panel;

    String name;
    Integer currAssignmentIndex;

    ArrayList<String> assessmentNames = new ArrayList<>();

    ArrayList<Date> dueDates = new ArrayList<>();
    ArrayList<Date> startDates = new ArrayList<>();

    ArrayList<Double> assessmentGrades = new ArrayList<>();
    ArrayList<Double> assessmentWeights = new ArrayList<>();

    public Subject(String name){
        this.name = name;
    }

    public void addAssessment(String assessmentName, Date dueDate){
        assessmentNames.add(assessmentName);
        dueDates.add(dueDate);
        startDates.add(null);

        if(currAssignmentIndex == null){currAssignmentIndex = 0;}
    }


    public void addAssessment(String assessmentName, Date dueDate, Date startDate){
        assessmentNames.add(assessmentName);
        dueDates.add(dueDate);
        startDates.add(startDate);

        if(currAssignmentIndex == null){currAssignmentIndex = 0;}
    }
}
