package com.phone.book.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Contact {
    
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@Column(name = "name")
	private String name  ;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "country_code")
	private String countryCode ;
	
	@Column(name ="email")
	private String email;

	@Column(name="status")
	private int status = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}



	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "id", name = "userid")
	private User user ;
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id  + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", countryCode=" + countryCode + ", email=" + email + "]";
	}

	@JsonIgnore
	public void updateContact(Contact newContact){
		this.countryCode = newContact.getCountryCode()!=null?newContact.getCountryCode():countryCode;
		this.email = newContact.getEmail()!=null?newContact.getEmail():email ;
		this.name = newContact.getName()!=null?newContact.getName():name ;
		this.phoneNumber = newContact.getPhoneNumber()!=null?newContact.getPhoneNumber():phoneNumber ;
	}

	public Contact(int id, int userId, String name, String phoneNumber, String countryCode, String email) {
		super(); 
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.countryCode = countryCode;
		this.email = email;
	} 
	public Contact()
	{
		
	}
	
}
