package com.example.first;

import java.util.List;

public class StudentProfile {
	
    private String studentName;
    private String targetRole; 
    private List<String> currentSkills; 

    // Getters and Setters
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
    
    public List<String> getCurrentSkills() { return currentSkills; }
    public void setCurrentSkills(List<String> currentSkills) { this.currentSkills = currentSkills; }

}
