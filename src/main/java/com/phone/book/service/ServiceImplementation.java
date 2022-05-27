package com.phone.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.phone.book.entity.Contact;
import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.User;
import com.phone.book.repo.ContactRepo;
import com.phone.book.repo.OtpRepo;
import com.phone.book.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@org.springframework.stereotype.Service
public class ServiceImplementation implements Service {
	
    @Autowired
	private OtpRepo otprepo ;

	@Autowired
	private ContactRepo contactRepo ;

	@Autowired
    private UserRepository uRepo ;

	@Override
	public void registerData(User user) {
		OtpDetails otpD = new OtpDetails() ; 
		User user2 = uRepo.save(user);
		otpD.setOtp(this.generateOtp());
		otpD.setUser(user2);
		otprepo.save(otpD) ;	
	}

	@Override
	public void findAll(List<User> user) {
		uRepo.findAll().forEach(user1 -> user.add(user1));
	}

	@Override
	public void saveOrUpdate(User user) {
        uRepo.saveAndFlush(user);
	}

	@Override
	public void save(Contact contact){
       contactRepo.save(contact) ;
	}

	@Override
	public int generateOtp() {
		Random random = new Random() ;
		return 100000 + random.nextInt(900000);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String[] data = username.split(",");
		System.out.println(data[0]+" >>> "+data[1]);
		User user = uRepo.findByPhoneNumberAndCountryCodeAndStatus(data[0], data[1], 0);
		System.out.println((user==null)+"status");
		return new org.springframework.security.core.userdetails.User(username, user.getPassCode(), new ArrayList<>());
	}

	@Override
	public void findByPhoneNumber(String phoneNumbeString) {
		
		
	}

	

	@Override
	public User getAuthenticateUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userData = null;
		if(principal instanceof UserDetails){
            userData = ((UserDetails)principal).getUsername();
        }else{
            userData = principal.toString();
        }
		String[] data = userData.split(",") ;
		User user =  uRepo.findByPhoneNumberAndCountryCode(data[0], data[1]) ;
		return user;
	}

	@Override
	public ArrayList<Contact>  getContact() {
		User user = this.getAuthenticateUser() ;
		ArrayList<Contact>  contacts = contactRepo.findByUser(user);
		return contacts ;
 		
	}

	@Override
	public boolean updateContact(int id, Contact newContact, User user) {
		if(!contactRepo.existsByUserAndId(user, id))	return false;
		Contact oldContact = contactRepo.getById(id);
		oldContact.updateContact(newContact);
		contactRepo.save(oldContact);
		return true;
	}
   
	public boolean updatePhoneNumber(String phoneNo, int id){
		if(uRepo.existsByPhoneNumber(phoneNo)){
			return false;
		}
		uRepo.updatePhoneNumber(id, phoneNo);
		return true;
	}
	public Contact getContactById(int id)
	{
		Contact newContact = contactRepo.getById(id);
        return newContact;
	}

	@Override
	public void deleteMyAccount() {
		 
		User user = this.getAuthenticateUser() ;
		user.setStatus(2);
		uRepo.save(user);
	}

	@Override
	public boolean deleteContactById(int id) {
		User user = this.getAuthenticateUser();
		Contact contact = contactRepo.getById(id);
		if(user.getId() != contact.getUser().getId()){
			return false;
		}
		contact.setStatus(2);
		contactRepo.save(contact);
		return true;
		
	}

}
