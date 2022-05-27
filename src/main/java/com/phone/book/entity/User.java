package com.phone.book.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Data
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "phoneNumber" })})
public class User {
      
	  @Id 
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int id ; 
	  
	  @Column(name  ="name")
	  private String name  ;
	  
	  @Column(name  ="email")
	  private String email ;
	  
	  @Column(name = "country_code")
	  private String countryCode ;
	  
	  @Column(name  ="phone_no.")
	  private String phoneNumber ;
	  
	  @Column(name  ="pass_code")
	  private String passCode ;
	  
	  @Column(name  ="status")
	  private int status = 0 ;
	  
	  @Column(name  ="created")
	  private LocalDate created = LocalDate.now() ;
	  
	  @LastModifiedDate
	  @Column(name  ="updated")
	   private Date updated  ;
	  
	  
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassCode() {
		return passCode;
	}
	public void setPassCode(String passCode) {
		this.passCode = passCode;
	}

	public LocalDate getCreated() {
		return created;
	}
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@OneToMany(mappedBy = "user")
	private Set<Contact> contact ;
	
	@OneToOne(mappedBy = "user")
	private OtpDetails otpdetails ;
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Set<Contact> getContact() {
		return contact;
	}
	public void setContact(Set<Contact> contact) {
		this.contact = contact;
	}
    
	
	public OtpDetails getOtpdetails() {
		return otpdetails;
	}
	public void setOtpdetails(OtpDetails otpdetails) {
		this.otpdetails = otpdetails;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", passCode="
				+ passCode + ", status=" + status + ", created=" + created + ", updated=" + updated + "]";
	}
	public User(int id, String name, String email, String phoneNumber, String passCode, int status, LocalDate created,
			Date updated) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.passCode = passCode;
		this.status = status;
		this.created = created;
		this.updated = updated;
	}

	public void editDetails(User newUser)
	{
        this.name = newUser.getName()!=null?newUser.getName():name ;
		this.email = newUser.getEmail()!=null?newUser.getEmail():email;
		this.phoneNumber = newUser.getPhoneNumber()!=null?newUser.getPhoneNumber():phoneNumber;
		this.countryCode = newUser.getCountryCode()!=null?newUser.getCountryCode():countryCode ;
		this.passCode = newUser.getPassCode()!=null?newUser.getPassCode():passCode ;

	}
	  
	public void setAllValues(User user) {
		name = user.getName()!=null?user.getName():name;
		email = user.getEmail()!=null?user.getEmail():email;
		phoneNumber = user.getPhoneNumber()!=null?user.getPhoneNumber():phoneNumber;
		passCode = user.getPassCode()!=null?user.getPassCode():passCode;
		
	}
	public User()
	{
		
	}
     
}
