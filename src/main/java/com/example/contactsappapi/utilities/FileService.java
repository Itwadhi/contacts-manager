package com.example.contactsappapi.utilities;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Value("${ROOT_STORAGE}")
	private String rootStorage;

	public ResponseEntity<String> storeFile(MultipartFile file, String dir) {

		String fileHashName = "";
		// create a directory where to save file
		Path fileStorageLocation = Paths.get(rootStorage + "/" + dir).toAbsolutePath().normalize();
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Could not create the directory where the uploaded files will be stored");
		}

		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains("..")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Sorry! Filename contains invalid path sequence");
			}

			String fileNameTokens[] = fileName.split("\\.");
			fileHashName = UUID.randomUUID().toString();

			if (fileNameTokens.length > 1) {
				fileHashName += "." + fileNameTokens[fileNameTokens.length - 1];
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = fileStorageLocation.resolve(fileHashName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			if (!fileHashName.isEmpty()) {
				String filePath = dir + "/" + fileHashName;
				return ResponseEntity.status(HttpStatus.OK).body(filePath);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to Store file");

	}

	public ResponseEntity<String> delete(String file) {

		String filePath = rootStorage + "/" + file;

		try {
			Files.deleteIfExists(Paths.get(filePath));
			return ResponseEntity.status(HttpStatus.OK).body("File deleted successfully");
		} catch (NoSuchFileException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to delete file, File not Exist");
		} catch (DirectoryNotEmptyException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to delete file, Directory is not empty");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to delete file, Invalid permissions");
		} catch (Exception e) {
			System.out.println("Unknown exception");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fail to delete file, Unknown exception");
		}

	}
}
