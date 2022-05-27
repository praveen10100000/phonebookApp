package com.phone.book.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OtpDetails {
      
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int id ;
 
	  @Column(name = "otp")
	  private int  otp ; 
	  
	  @Column(name = "created")
	  private LocalDate created;
	  
	  @Column(name  = "expire")
	  private LocalDateTime expire = LocalDateTime.now().plusMinutes(20) ;
	  
	  @Column (name = "updated")
	  private LocalDate updated ;
	  
	  @JsonIgnore
	  @OneToOne(cascade = CascadeType.ALL)
	  @JoinColumn(referencedColumnName = "id" , name = "user_id",unique = true, nullable = false)
	  private User user;
	  
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public LocalDateTime getExpire() {
		return expire;
	}

	public void setExpire(LocalDateTime expire) {
		this.expire = expire;
	}

	public LocalDate getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDate updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "OtpDetails [id=" + id + ", otp=" + otp + ", created=" + created + ", expire="
				+ expire + ", updated=" + updated + "]";
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OtpDetails(int id, int userId, int otp, LocalDate created, LocalDateTime expire, LocalDate updated) {
		super();
		this.id = id;
		this.otp = otp;
		this.created = created;
		this.expire = expire;
		this.updated = updated;
	} 
	  
	public OtpDetails()
	{
		
	}
	  
}
