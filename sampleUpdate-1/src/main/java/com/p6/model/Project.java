package com.p6.model;

import java.time.LocalDate;
import java.util.List;

//import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;






public class Project {
    
	 private Long id;
    
   private String projectName;
    

	private LocalDate startDate;
    // other fields...
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
    
    // getters and setters...

public Project() {
	super();
	// TODO Auto-generated constructor stub
}

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // custom queries if needed
    List<Project> findByProjectName(String projectName);

	
}

}