package com.phone.book.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.phone.book.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByPhoneNumberAndPassCodeAndCountryCode(String phoneNumber, String passCode , String countryCode );
  
	boolean existsByPassCode(String passCode);
	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByCountryCode(String countryCode);
	
	//alternate way to edit the date

	User findByPhoneNumberAndCountryCode(String phoneNumber , String countrycode) ;
	User findByPhoneNumberAndCountryCodeAndStatus(String phoneNumber , String countrycode, int status) ;
	User findByPhoneNumber(String phoneNumber);
	@Modifying
	@Transactional
	@Query( value = "update user set email=?2, name=?3 where id=?1", nativeQuery = true)
	void updateEmailAndName(int id, String email, String name);

	
	
	@Modifying
	@Transactional
	@Query( value = "update user set phone_no_=?2 where id=?1", nativeQuery = true)
	void updatePhoneNumber(int id, String phoneNumber);

}

