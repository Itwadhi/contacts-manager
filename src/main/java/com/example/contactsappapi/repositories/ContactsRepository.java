package com.example.contactsappapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.contactsappapi.models.Contact;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Long> {

	@Query("SELECT c FROM Contact c WHERE c.active=true ORDER BY c.createdAt DESC")
	List<Contact> findAll();

	Contact findFirstByEmail(String email);

}
