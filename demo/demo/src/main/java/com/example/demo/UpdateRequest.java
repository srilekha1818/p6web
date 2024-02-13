package com.example.demo;
public class UpdateRequest {
   
	private int objectId;
    private String newStartDate;

    // Constructors, getters, and setters

    // Default constructor is necessary for deserialization
    public UpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateRequest(int objectId, String newStartDate) {
		super();
		this.objectId = objectId;
		this.newStartDate = newStartDate;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(String newStartDate) {
		this.newStartDate = newStartDate;
	}

	@Override
	public String toString() {
		return "UpdateRequest{" +
                "objectId=" + objectId +
                ", newStartDate='" + newStartDate + '\'' +
                 
                '}';
	}

   

    // Getters and setters
}
