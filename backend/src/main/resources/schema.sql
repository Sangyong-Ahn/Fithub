CREATE DATABASE IF NOT EXISTS fithubdb;
USE fithubdb;


CREATE TABLE IF NOT EXISTS `user` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `email` varchar(40) NOT NULL UNIQUE,
  `password` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `gender` VARCHAR(1) NOT NULL,		# M , F
  `phone_number` varchar(20) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  `profile_image` varchar(100) DEFAULT NULL,
  `latitude` FLOAT DEFAULT NULL,		
  `longitude` FLOAT DEFAULT NULL
  
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `mentor` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `email` varchar(40) NOT NULL UNIQUE,
  `password` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `date_of_birth` DATE NOT NULL,
  `gender` VARCHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
  `phone_number` varchar(20) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  `profile_image` varchar(100) DEFAULT NULL,
  `content` TEXT DEFAULT NULL,
  `certificate` VARCHAR(100) DEFAULT NULL,
  `latitude` FLOAT DEFAULT NULL,		
  `longitude` FLOAT DEFAULT NULL
  
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `program` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mentor_id` VARCHAR(40) NOT NULL,       -- 멘토 ID (외래 키)
  `category_id` INT NOT NULL,             -- 카테고리 ID (외래 키)
  `title` VARCHAR(100) NOT NULL,          -- 제목
  `reservation_start_date` DATE NOT NULL, -- 예약 시작 일시
  `reservation_end_date` DATE NOT NULL,   -- 예약 종료 일시
  `program_start_date` DATE NOT NULL,     -- 시작 일시
  `program_end_date` DATE NOT NULL,       -- 종료 일시
  `thumbnail` VARCHAR(100) NOT NULL,      -- 대표 이미지 경로
  `content` TEXT NOT NULL,                -- 내용
  `youtube_url` VARCHAR(100) NOT NULL,    -- YouTube URL
  `latitude` FLOAT NOT NULL,              -- 위도
  `longitude` FLOAT NOT NULL,             -- 경도
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`mentor_id`) REFERENCES `mentor`(`email`),
  FOREIGN KEY (`category_id`) REFERENCES `category`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `time` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `program_id` INT NOT NULL,         -- 프로그램 ID (외래 키)
  `sunday` BOOLEAN DEFAULT FALSE,    -- 일요일
  `monday` BOOLEAN DEFAULT FALSE,    -- 요일
  `tuesday` BOOLEAN DEFAULT FALSE,   -- 요일
  `wednesday` BOOLEAN DEFAULT FALSE, -- 요일
  `thursday` BOOLEAN DEFAULT FALSE,  -- 요일
  `friday` BOOLEAN DEFAULT FALSE,    -- 요일
  `saturday` BOOLEAN DEFAULT FALSE,  -- 요일
  `start_time` TIME NOT NULL,       -- 시작 시간
  `end_time` TIME NOT NULL,         -- 종료 시간
  `price` INT NOT NULL,             -- 가격
  `capacity` INT NOT NULL,          -- 수용 인원
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`program_id`) REFERENCES `program`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `match` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `time_id` INT NOT NULL,           -- 시간 ID (외래 키)
  `mentor_id` VARCHAR(40) NOT NULL, -- 멘토 ID (외래 키)
  `user_id` VARCHAR(40) NOT NULL,   -- 사용자 ID (외래 키)
  `purchase_confirm` BOOLEAN DEFAULT FALSE,       -- 최종 확인 여부(유저가 confirm)
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`time_id`) REFERENCES `time`(`id`),
  FOREIGN KEY (`mentor_id`) REFERENCES `mentor`(`email`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`email`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mentor_id` VARCHAR(40) NOT NULL,    -- 멘토 ID (외래 키)
  `program_id` INT NOT NULL,           -- 프로그램 ID (외래 키)
  `user_id` VARCHAR(40) NOT NULL,      -- 사용자 ID (외래 키)
  `content` TEXT NOT NULL,                                 -- 리뷰 내용
  `score` INT NOT NULL CHECK (score >= 0 AND score <= 5),  -- 점수
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,        -- 생성 일시
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`mentor_id`) REFERENCES `mentor`(`email`),
  FOREIGN KEY (`program_id`) REFERENCES `program`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`email`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `chat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mentor_id` VARCHAR(40) NOT NULL,  -- 멘토 ID (외래 키)
  `user_id` VARCHAR(40) NOT NULL,    -- 사용자 ID (외래 키)
  `content` TEXT,                     -- 채팅 내용
  `isUser` BOOLEAN,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시

  PRIMARY KEY (`id`),
  FOREIGN KEY (`mentor_id`) REFERENCES `mentor`(`email`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`email`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO user(email, password, name, date_of_birth, gender, phone_number)
VALUES ("admin@admin", "admin", "admin", 11111111, "M", "01000000000");

SELECT * FROM user;

