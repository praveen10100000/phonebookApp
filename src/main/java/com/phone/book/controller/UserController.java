package com.phone.book.controller;

import java.util.ArrayList;
import java.util.List;

import com.phone.book.dto.OtpVerification;
import com.phone.book.dto.UserResponse;
import com.phone.book.entity.Contact;
import com.phone.book.entity.OtpDetails;
import com.phone.book.entity.User;
import com.phone.book.repo.OtpRepo;
import com.phone.book.repo.UserRepository;
import com.phone.book.service.JwtUtil;
import com.phone.book.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository uRepo;

	@Autowired
	private Service service;

	@Autowired
	private OtpRepo otprepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private Service UserDetails;

	@Autowired
	private JwtUtil jwtUtilToken;

	@PostMapping("/register")
	public String registerData(@RequestBody User user) {
		if (uRepo.existsByPhoneNumber(user.getPhoneNumber()) == true) {
			return "User already exists";
		} else {
			service.registerData(user);
			return "User Register Successfully";
		}
	}

	@PostMapping("/login")
	public String userLogin(@RequestBody User user) {
		user.setPhoneNumber(user.getPhoneNumber());
		user.setPassCode(user.getPassCode());
		user.setCountryCode(user.getCountryCode());

		if (uRepo.existsByPhoneNumberAndPassCodeAndCountryCode(user.getPhoneNumber(), user.getPassCode(),
				user.getCountryCode())) {
			User user2 = uRepo.findByPhoneNumberAndCountryCodeAndStatus(user.getPhoneNumber(), user.getCountryCode(),
					0);
			if (user2 == null) {
				return "user is deleted";
			}
			int otp = service.generateOtp();
			otprepo.updateOtp(user2.getId(), otp);
			return "User Login Successfully";
		} else
			return "Invalid Credential";
	}

	@PostMapping("/verifyOTP")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpVerification otpverification) throws Exception {
		User user = uRepo.findByPhoneNumberAndCountryCode(otpverification.getPhoneNumber(),
				otpverification.getCountryCode());
		OtpDetails otpDetails = user.getOtpdetails();
		if (otpverification.getOtp() == otpDetails.getOtp()) {
			String data = user.getPhoneNumber() + "," + user.getCountryCode();
			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(data, user.getPassCode()));
			} catch (BadCredentialsException e) {
				throw new Exception("incorrect username or password", e);
			}
			final UserDetails userDetails = service.loadUserByUsername(data);
			final String jwt = JwtUtil.generateToken(userDetails);
			return ResponseEntity.ok(new UserResponse(jwt));
		} else
			return ResponseEntity.badRequest().body("OTP is wrong");
	}

	@GetMapping("/getuser")
	public List<User> getdata() {
		List<User> user = new ArrayList<User>();
		service.findAll(user);
		return user;
	}

	@PutMapping("/editdetails/{id}")
	public User editDetails(@RequestBody User user, @PathVariable int id) {
		User user2 = uRepo.findById(id).get();

		user2.setAllValues(user);
		// user.setId(id);
		service.saveOrUpdate(user2);
		return user2;
	}

	// alternate way to edit the data
	@PutMapping("/editdetails2")
	public User editDetails2(@RequestBody User user) {
		User user1 = service.getAuthenticateUser();
		user1.editDetails(user);
		return user1;
	}

	@PutMapping("/editphonenumber")
	public String editPhoneNumber(@RequestBody User user) {
		String newPhoneNo = user.getPhoneNumber();
		User authUser = service.getAuthenticateUser();
		boolean status = service.updatePhoneNumber(newPhoneNo, authUser.getId());
		return status ? "updated" : "already exists";

	}

	@PutMapping("/editphoneno/{id}")
	public String changePhoneNumber(@RequestBody User user, @PathVariable int id) {
		uRepo.updatePhoneNumber(id, user.getPhoneNumber());
		return user.getPhoneNumber();
	}

	@PostMapping("/checkotp")
	public void createOtp(@RequestBody OtpDetails otp) {

	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getName(), user.getPassCode()));
		} catch (BadCredentialsException e) {
			throw new Exception("incorrect username or password", e);
		}
		final UserDetails userDetails = service.loadUserByUsername(user.getName());
		final String jwt = JwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new UserResponse(jwt));
	}

	@PostMapping("/addContact")
	public ResponseEntity<?> addcontact(@RequestBody Contact contact) {

		User user = service.getAuthenticateUser();
		contact.setUser(user);
		service.save(contact);
		return ResponseEntity.ok().body("Contact Added");
	}

	@GetMapping("/getContact")
	public ArrayList<Contact> getContact() {
		return service.getContact();
	}

	@PutMapping("/editContact/{id}")
	public String editContact(@RequestBody Contact contact, @PathVariable int id) {
		User user = service.getAuthenticateUser();
		boolean status = service.updateContact(id, contact, user);
		return status ? "Edit successfully" : "Not tihs users Contact";
	}

	@GetMapping("/getContact/{id}")
	public Contact getContactById(@PathVariable int id) {
		return service.getContactById(id);
	}

	@DeleteMapping("/deleteMyAccount")
	public String deleteMyAccount() {
		service.deleteMyAccount();
		return "Success";
	}

	@DeleteMapping("/deleteContactById/{id}")
	public String deleteContactById(@PathVariable int id) {
		boolean status = service.deleteContactById(id);
		return status ? "Success" : "unsuccessful";

	}

}
