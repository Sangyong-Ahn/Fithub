package com.team2.fithub.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team2.fithub.model.dto.Chat;
import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.ChatService;
import com.team2.fithub.service.UserService;
import com.team2.fithub.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserRestController {

	private final UserService us;
	private final ChatService cs;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	public UserRestController(UserService us, ChatService cs) {
		this.us = us;
		this.cs = cs;
	}

	@GetMapping("")
	public ResponseEntity<?> userList() {
		List<User> userList = us.findAllUser();
		if (userList.isEmpty())
			return new ResponseEntity<>(userList, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<?> userAdd(@RequestBody User user) {
		try {
			User existingUser = us.findUserByEmail(user.getEmail());
			if(existingUser != null)
				return new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST);
			if (user.getEmail().isEmpty())
	            return new ResponseEntity<>("이메일을 입력하세요.", HttpStatus.BAD_REQUEST);
			
			String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	        if (!user.getEmail().matches(emailPattern))
	            return new ResponseEntity<>("올바른 이메일 형식이 아닙니다.", HttpStatus.BAD_REQUEST);
			if (user.getPassword().isEmpty())
	            return new ResponseEntity<>("비밀번호를 입력하세요.", HttpStatus.BAD_REQUEST);
			if (user.getName().isEmpty())
	            return new ResponseEntity<>("이름을 입력하세요.", HttpStatus.BAD_REQUEST);
			if (user.getDateOfBirth() == null)
	            return new ResponseEntity<>("생년월일을 입력하세요.", HttpStatus.BAD_REQUEST);
			if (user.getDateOfBirth().length() != 8)
	            return new ResponseEntity<>("생년월일을 8자리로 입력하세요.", HttpStatus.BAD_REQUEST);
			if (user.getGender().isEmpty())
				return new ResponseEntity<>("성별을 선택해주세요.", HttpStatus.BAD_REQUEST);
			if (user.getPhoneNumber().isEmpty())
				return new ResponseEntity<>("전화 번호를 선택해주세요.", HttpStatus.BAD_REQUEST);
			if (!user.getPhoneNumber().matches("[0-9]+")) {
	            return new ResponseEntity<>("전화번호는 숫자로만 입력해야 합니다.", HttpStatus.BAD_REQUEST);
	        }
			
			int result = us.addUser(user);
			if (result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("올바른 생년월일이 아닙니다.", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> userDetails(@PathVariable("id") int id) {
		try {
			User user = us.findUser(id);
			if (user == null)
				return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);

			String fileName = id + "_profile.png"; // PNG 파일명 생성
			String uploadDir = "C:/img/user"; // 파일이 저장된 경로

			Path filePath = Paths.get(uploadDir).resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());

			user.setProfileImage(resource.exists() ? uploadDir + "/" + fileName : "C:/img/user/default.png");

			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/chat")
	public ResponseEntity<?> userChats(@PathVariable("id") int id) {
		Map<Integer, List<Chat>> chat = cs.findChatByUser(id);
		if (chat.isEmpty())
			return new ResponseEntity<>(chat, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> userUpdate(@PathVariable("id") int id, @RequestPart("user") User newUser,
			@RequestPart(value = "file", required = false) MultipartFile file) {

		try {
			User user = us.findUser(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			if (newUser.getPassword() != null) {
				user.setPassword(newUser.getPassword());
			}
			if (newUser.getGender() != null) {
				user.setGender(newUser.getGender());
			}
			if (newUser.getPhoneNumber() != null) {
				user.setPhoneNumber(newUser.getPhoneNumber());
			}
			if (newUser.getLatitude() != null) {
				user.setLatitude(newUser.getLatitude());
			}
			if (newUser.getLongitude() != null) {
				user.setLongitude(newUser.getLongitude());
			}
			if (file != null && !file.isEmpty()) { // 업로드된 파일이 있다면
				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
				String newFileName = id + "_profile" + getFileExtension(fileName);
				String uploadDir = "C:/img/user";

				// 파일 저장 경로
				Path uploadPath = Paths.get(uploadDir);
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				// 파일 저장
				try {
					// 기존 파일 삭제
					Path existingFilePath = uploadPath.resolve(newFileName);
					if (Files.exists(existingFilePath)) {
						Files.delete(existingFilePath);
					}
					// 새 파일 저장
					Path filePath = uploadPath.resolve(newFileName);
					Files.copy(file.getInputStream(), filePath);
				} catch (IOException e) {
					return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			int result = us.modifyUser(user);
			if (result == 1)
				return new ResponseEntity<>(result, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> userRemove(@PathVariable("id") int id) {
		try {
			User user = us.findUser(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			int result = us.removeUser(id);
			if (result == 1)
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestParam String email, @RequestParam String password) {
		try {
			User user = us.findUserByEmail(email);

			if (user == null) {
				return new ResponseEntity<>("해당 유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
			}

			if (!user.getPassword().equals(password)) {
				return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
			}

			user.setAccessToken(jwtUtil.createToken(user.getEmail()));
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<String> userlogout() {
		try {
			return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
