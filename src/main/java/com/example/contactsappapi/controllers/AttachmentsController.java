package com.example.contactsappapi.controllers;

import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.contactsappapi.utilities.Helper;

@RestController
public class AttachmentsController {

	@Value("${ROOT_STORAGE}")
	private String rootStorage;

	@RequestMapping(path = "/attachments/{location}/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<?> download(@PathVariable String location, @PathVariable String filename,
			HttpServletRequest request) {
		try {

			String filePath = rootStorage + "/" + location + "/" + filename;

			File file = new File(filePath);

			Path path = Paths.get(file.getAbsolutePath());

			ByteArrayResource resource = null;
			try {
				resource = new ByteArrayResource(Files.readAllBytes(path));
			} catch (NoSuchFileException e) {
				e.printStackTrace();
			}

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());

			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			HttpHeaders httpHeaders = new HttpHeaders();

			return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.parseMediaType(mimeType))
					.headers(httpHeaders).body(resource);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getLocalizedMessage());
			return Helper.createResponse(false, null, "Fail to get attachment", HttpStatus.BAD_REQUEST);
		}

	}

}
