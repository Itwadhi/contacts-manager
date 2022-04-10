package com.example.contactsappapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.contactsappapi.models.Contact;
import com.example.contactsappapi.repositories.ContactsRepository;
import com.example.contactsappapi.services.ContactsService;
import com.example.contactsappapi.utilities.FileService;

@Controller
@RequestMapping(path = "/contacts")
public class ContactsController {

	@Autowired
	private ContactsRepository contactsRepository;

	@Autowired
	private ContactsService contactsService;

	@Autowired
	private FileService fileService;

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

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String create() {

		return "contacts/create";
	}

	@RequestMapping(path = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model model) {

		Optional<Contact> contact = contactsRepository.findById(id);

		if (!contact.isPresent()) {
			return "redirect:/contacts?error=Contact does not exist";
		}
		if (!contact.get().getActive()) {
			return "redirect:/contacts?error=Contact does not exist";
		}

		model.addAttribute("contact", contact.get());

		return "contacts/edit";
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public String store(@RequestParam(required = false, name = "file") MultipartFile file,
			@ModelAttribute Contact contact) {

		Contact existingContact = contactsRepository.findFirstByEmail(contact.getEmail());

		if (existingContact != null) {
			return "redirect:/contacts/add?error=Email Already Exists";
		}

		if (file != null && file.getSize() > 0) {

			if (file.getSize() > 1000000) {
				return "redirect:/contacts/add?error=Image should not exceed 1MB";
			}

			// store file
			String fileDir = "images";
			ResponseEntity<String> respEntity = fileService.storeFile(file, fileDir);

			String fileLocation = respEntity.getBody();

			contact.setProfileImage(fileLocation);
		}

		contactsRepository.save(contact);

		return "redirect:/contacts?success=Contact added Successful";
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.POST)
	public String update(@PathVariable Long id, @RequestParam(required = false, name = "file") MultipartFile file,
			@ModelAttribute Contact contact) {

		Optional<Contact> existingContact = contactsRepository.findById(id);

		if (!existingContact.isPresent() || !existingContact.get().getActive()) {
			return "redirect:/contacts";
		}

		if (file != null && file.getSize() > 0) {

			if (file.getSize() > 1000000) {
				return "redirect:/contacts/" + id + "/edit?error=Image should not exceed 1MB";
			}

			// store file
			String fileDir = "images";
			ResponseEntity<String> respEntity = fileService.storeFile(file, fileDir);

			String fileLocation = respEntity.getBody();

			contact.setProfileImage(fileLocation);
		} else {
			contact.setProfileImage(existingContact.get().getProfileImage());
		}

		contact.setId(existingContact.get().getId());
		contact.setActive(existingContact.get().getActive());
		contactsRepository.save(contact);

		return "redirect:/contacts?success=Contact Updated successful";
	}

	@RequestMapping(path = "/{id}/delete", method = RequestMethod.GET)
	public String destory(@PathVariable Long id) {

		Optional<Contact> contact = contactsRepository.findById(id);

		if (!contact.isPresent()) {
			return "redirect:/contacts?error=Contact does not exist";
		}

		if (!contact.get().getActive()) {
			return "redirect:/contacts?error=Contact does not exist";
		}

		contact.get().setActive(false);
		contactsRepository.save(contact.get());

		return "redirect:/contacts?success=Contact deleted successfull";
	}
}
