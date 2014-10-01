package com.isobar.projecttaskmanager.pojo;

public class FormData {
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String contactTime;
	private String contactMethod;
	private String postcode;
	
	public FormData(String title, String firstName, String lastName,
			String email, String phone, String contactTime,
			String contactMethod, String postcode) {
		super();
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.contactTime = contactTime;
		this.contactMethod = contactMethod;
		this.postcode = postcode;
	}

	@Override
	public String toString() {
		return "FormData [title=" + title + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", phone="
				+ phone + ", contactTime=" + contactTime + ", contactMethod="
				+ contactMethod + ", postcode=" + postcode + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getContactTime() {
		return contactTime;
	}

	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}

	public String getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

}
