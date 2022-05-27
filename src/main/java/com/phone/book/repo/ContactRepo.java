package com.phone.book.repo;

import java.util.ArrayList;

import javax.transaction.Transactional;

import com.phone.book.entity.Contact;
import com.phone.book.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository <Contact , Integer > {

    
    public ArrayList<Contact> findByUser(User user);

    public boolean existsByUserAndId(User user, int id);

    @Modifying
	@Transactional
	@Query( value = "update user set name=?2, email=?3, countryCode=?4, phoneNumber=?5 where id=?1", nativeQuery = true)
	void updateContact(int id, String name, String email , String countryCode , String phoneNumer);
}
