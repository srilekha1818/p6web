package com.p6.p6web18;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public class Project {

    private Long id;
    private String name;
    private String epsid;
	public String getEpsid() {
		return epsid;
	}
	public void setEpsid(String epsid) {
		this.epsid = epsid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", epsid=" + epsid + "]";
	}

    // getters and setters
	

    // Implement equals and hashCode if needed
}

