package com.example.contactsappapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.contactsappapi.models.Contact;
import com.example.contactsappapi.repositories.ContactsRepository;
import com.example.contactsappapi.services.ContactsService;

@Controller
@RequestMapping(path = "/")
public class HomeController {

	@Autowired
	private ContactsRepository contactsRepository;

	@Autowired
	private ContactsService contactsService;

	@RequestMapping(path = "", method = RequestMethod.GET)
	public String index(@RequestParam LinkedMultiValueMap<String, String> requestData, Model model) {

		List<Contact> contacts = new ArrayList<Contact>();
		if (requestData.isEmpty() || requestData.getFirst("q") == null) {
			contacts = contactsRepository.findAll();
		} else {
			contacts = contactsService.search(requestData);
		}

		model.addAttribute("contacts", contacts);

		return "contacts/index";
	}
}
