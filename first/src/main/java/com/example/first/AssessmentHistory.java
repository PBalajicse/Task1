package com.example.first;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class AssessmentHistory {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String studentName;
	    private String targetRole;
	    private int finalScore;
	    private String missingSkillsList; 
	    private LocalDateTime assessedAt;

	    // Getters and Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    public String getStudentName() { return studentName; }
	    public void setStudentName(String studentName) { this.studentName = studentName; }
	    public String getTargetRole() { return targetRole; }
	    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
	    public int getFinalScore() { return finalScore; }
	    public void setFinalScore(int finalScore) { this.finalScore = finalScore; }
	    public String getMissingSkillsList() { return missingSkillsList; }
	    public void setMissingSkillsList(String missingSkillsList) { this.missingSkillsList = missingSkillsList; }
	    public LocalDateTime getAssessedAt() { return assessedAt; }
	    public void setAssessedAt(LocalDateTime assessedAt) { this.assessedAt = assessedAt; }

}
