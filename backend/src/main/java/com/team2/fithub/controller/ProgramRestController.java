package com.team2.fithub.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	        // 파일이 있는지 확인합니다.
	        if (file == null || file.isEmpty()) {
	            System.out.println("응 왔어~");
	            return new ResponseEntity<>("No Thumbnail.", HttpStatus.BAD_REQUEST);
	        }

	        // 프로그램을 먼저 생성하여 ID를 확보합니다.
	        int result = ps.addProgram(program);
	        if (result != 1) {
	            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	        }

	        // 생성된 프로그램 ID를 가져옵니다.
	        int programId = program.getId();

	        // 파일명을 생성된 프로그램 ID를 사용하여 구성합니다.
	        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	        String newFileName = programId + "_thumbnail" + getFileExtension(fileName);

	        // 업로드 경로 설정 (프로젝트 상대 경로)
	        String uploadDir = "src/main/resources/upload/thumbnail";

	        // 절대 경로로 변환
	        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }

	        try {
	            // 기존 파일이 있으면 삭제합니다.
	            Path existingFilePath = uploadPath.resolve(newFileName);
	            if (Files.exists(existingFilePath)) {
	                Files.delete(existingFilePath);
	            }
	            // 새 파일을 저장합니다.
	            Path filePath = uploadPath.resolve(newFileName);
	            Files.copy(file.getInputStream(), filePath);

	            // 프로그램의 썸네일 경로 설정
	            program.setThumbnail("/upload/thumbnail/" + newFileName);

	            // 프로그램 정보를 업데이트하여 썸네일 경로를 저장합니다.
	            ps.updateProgramThumbnail(programId, program.getThumbnail());

	        } catch (IOException e) {
	            return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }

	        System.out.println("생성됨");
	        return new ResponseEntity<>(result, HttpStatus.CREATED);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}

	private String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	@GetMapping("/{programId}/thumbnail")
    public ResponseEntity<Resource> getProgramThumbnail(@PathVariable int programId) {
        try {
            String thumbnailPath = ps.getProgramThumbnailPath(programId);
            if (thumbnailPath == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Path filePath = Paths.get("src/main/resources" + thumbnailPath).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName().toString() + "\"");
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
