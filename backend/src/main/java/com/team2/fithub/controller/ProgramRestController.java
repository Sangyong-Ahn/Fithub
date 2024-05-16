package com.team2.fithub.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.SearchCondition;
import com.team2.fithub.service.ProgramService;

@RestController
@RequestMapping("/program")
@CrossOrigin("*")
public class ProgramRestController {

	private final ProgramService ps;

	@Autowired
	public ProgramRestController(ProgramService ps) {
		this.ps = ps;
	}

	@GetMapping("")
	public ResponseEntity<?> programList() {
		List<Program> programList = ps.findAllProgram();
//		if (programList.isEmpty())
//			return new ResponseEntity<>("검색 결과가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(programList, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> programAdd(@RequestPart("program") Program program,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		try {
			if (file == null || file.isEmpty()) {
				System.out.println("응 왔어~");
				return new ResponseEntity<>("No Thumnail.", HttpStatus.BAD_REQUEST);
			}
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String newFileName = program.getId() + "_thumbnail" + getFileExtension(fileName);
			String uploadDir = "C:/img/program";

			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try {
				Path existingFilePath = uploadPath.resolve(newFileName);
				if (Files.exists(existingFilePath)) {
					Files.delete(existingFilePath);
				}
				Path filePath = uploadPath.resolve(newFileName);
				Files.copy(file.getInputStream(), filePath);
				program.setThumbnail(uploadDir + "/" + newFileName);
			} catch (IOException e) {
				return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			int result = ps.addProgram(program);
			if (result == 1) {
				System.out.println("생성됨");
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> programDetails(@PathVariable("id") int id) {
		Program program = ps.findProgram(id);
//		if (program == null)
//			return new ResponseEntity<>("검색 결과가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(program, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> programSearch(@ModelAttribute SearchCondition condition) {
		System.out.println(condition);
		List<Program> programs = ps.searchProgram(condition);
//		if (programs.isEmpty())
//			return new ResponseEntity<>("검색 결과가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(programs, HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
