package com.example.contactsappapi.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.example.contactsappapi.models.Contact;

@Service
public class ContactsService {

	@Autowired
	private EntityManager em;

	public List<Contact> search(LinkedMultiValueMap<String, String> requestData) {
		
		String q=requestData.getFirst("q");
		
		String sql ="SELECT c FROM Contact c WHERE c.active=true AND c.fullName LIKE '%"+q+"%' OR c.phoneNumber LIKE '%"+q+"%' OR c.email LIKE '%"+q+"%'";
		
		
		TypedQuery<Contact> query=em.createQuery(sql, Contact.class);
		
		return query.getResultList();
	}
}
