package com.phone.book.service;

import java.util.ArrayList;
import java.util.List;

import com.phone.book.entity.Contact;
import com.phone.book.entity.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface Service extends UserDetailsService  {

    public int generateOtp();

	public void registerData(User user);

	public void findAll(List<User> user);

	public void saveOrUpdate(User user);
    
	public void save (Contact contact) ;

	public void findByPhoneNumber (String  phoneNumber) ;

	public User  getAuthenticateUser() ;

	public ArrayList<Contact>  getContact () ;

	public boolean updateContact(int id, Contact newContact, User user);

	public boolean updatePhoneNumber(String phoneNo, int id);
	
	public Contact getContactById(int id) ;

	public void  deleteMyAccount ( ) ;

	public boolean deleteContactById(int id ) ;

	
}
