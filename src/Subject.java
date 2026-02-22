import Dates.Date;

import java.io.Serializable;
import java.util.ArrayList;

public class Subject implements Serializable {

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

    public String[][] getAssessments(){
        String[][] assessments = new String[assessmentNames.size()][3];
        for(int i = 0; i < assessments.length; i++){
            assessments[i][0] = assessmentNames.get(i);
            assessments[i][1] = dueDates.get(i).toString();
            assessments[i][2] = startDates.get(i).toString();
        }
        return assessments;
    }

}