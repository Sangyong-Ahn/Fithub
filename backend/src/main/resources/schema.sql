CREATE DATABASE IF NOT EXISTS fithubdb;
USE fithubdb;


CREATE TABLE IF NOT EXISTS `user` (
  `id` int AUTO_INCREMENT PRIMARY KEY,
  `email` varchar(40) NOT NULL UNIQUE,
  `password` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `dateOfBirth` DATE NOT NULL,
  `gender` VARCHAR(1) NOT NULL,		# M , F
  `phoneNumber` varchar(20) DEFAULT NULL,
  `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  `profileImage` varchar(100) DEFAULT NULL,
  `latitude` FLOAT DEFAULT NULL,		
  `longitude` FLOAT DEFAULT NULL
  
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `mentor` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `email` varchar(40) NOT NULL UNIQUE,
  `password` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `dateOfBirth` DATE NOT NULL,
  `gender` VARCHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
  `phoneNumber` varchar(20) DEFAULT NULL,
  `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  `profileImage` varchar(100) DEFAULT NULL,
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
  `mentorId` INT NOT NULL,       -- 멘토 ID (외래 키)
  `categoryId` INT NOT NULL,             -- 카테고리 ID (외래 키)
  `title` VARCHAR(100) NOT NULL,          -- 제목
  `reservationStartDate` DATE NOT NULL, -- 예약 시작 일시
  `reservationEndDate` DATE NOT NULL,   -- 예약 종료 일시
  `programStartDate` DATE NOT NULL,     -- 시작 일시
  `programEndDate` DATE NOT NULL,       -- 종료 일시
  `thumbnail` VARCHAR(100) NOT NULL,      -- 대표 이미지 경로
  `content` TEXT NOT NULL,                -- 내용
  `youtubeUrl` VARCHAR(100) NOT NULL,    -- YouTube URL
  `latitude` FLOAT NOT NULL,              -- 위도
  `longitude` FLOAT NOT NULL,             -- 경도
  `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`mentorId`) REFERENCES `mentor`(`id`),
  FOREIGN KEY (`categoryId`) REFERENCES `category`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `time` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `programId` INT NOT NULL,         -- 프로그램 ID (외래 키)
  `sunday` BOOLEAN DEFAULT FALSE,    -- 일요일
  `monday` BOOLEAN DEFAULT FALSE,    -- 요일
  `tuesday` BOOLEAN DEFAULT FALSE,   -- 요일
  `wednesday` BOOLEAN DEFAULT FALSE, -- 요일
  `thursday` BOOLEAN DEFAULT FALSE,  -- 요일
  `friday` BOOLEAN DEFAULT FALSE,    -- 요일
  `saturday` BOOLEAN DEFAULT FALSE,  -- 요일
  `startTime` TIME NOT NULL,       -- 시작 시간
  `endTime` TIME NOT NULL,         -- 종료 시간
  `price` INT NOT NULL,             -- 가격
  `capacity` INT NOT NULL,          -- 수용 인원
  `reserveNum` INT DEFAULT 0,		-- 현재 예약한 인원
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`programId`) REFERENCES `program`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `match` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `timeId` INT NOT NULL,           -- 시간 ID (외래 키)
  `mentorId` INT NOT NULL, -- 멘토 ID (외래 키)
  `userId` INT NOT NULL,   -- 사용자 ID (외래 키)
  `purchaseConfirm` BOOLEAN DEFAULT FALSE,       -- 최종 확인 여부(유저가 confirm)
  `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`timeId`) REFERENCES `time`(`id`),
  FOREIGN KEY (`mentorId`) REFERENCES `mentor`(`id`),
  FOREIGN KEY (`userId`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mentorId` INT NOT NULL,    -- 멘토 ID (외래 키)
  `programId` INT NOT NULL,           -- 프로그램 ID (외래 키)
  `userId` INT NOT NULL,      -- 사용자 ID (외래 키)
  `content` TEXT NOT NULL,                                 -- 리뷰 내용
  `score` INT NOT NULL CHECK (score >= 0 AND score <= 5),  -- 점수
  `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,        -- 생성 일시
  
  PRIMARY KEY (`id`),
  FOREIGN KEY (`mentorId`) REFERENCES `mentor`(`id`),
  FOREIGN KEY (`programId`) REFERENCES `program`(`id`),
  FOREIGN KEY (`userId`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;


CREATE TABLE IF NOT EXISTS `chat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mentorId` INT NOT NULL,  -- 멘토 ID (외래 키)
  `userId` INT NOT NULL,    -- 사용자 ID (외래 키)
  `content` TEXT,                     -- 채팅 내용
  `isUser` BOOLEAN,
  `createdAt` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 생성 일시

  PRIMARY KEY (`id`),
  FOREIGN KEY (`mentorId`) REFERENCES `mentor`(`id`),
  FOREIGN KEY (`userId`) REFERENCES `user`(`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET = utf8mb4;



INSERT INTO user(email, password, name, dateOfBirth, gender, phoneNumber)
VALUES ("user1@user1", "userpass1", "username1", 11111111, "M", "11111111111"),
	   ("user2@user2", "userpass2", "username2", 11111112, "M", "11111111112"),
       ("user3@user3", "userpass3", "username3", 11111113, "M", "11111111113"),
       ("user4@user4", "userpass4", "username4", 11111114, "M", "11111111114"),
       ("user5@user5", "userpass5", "username5", 11111115, "M", "11111111115"),
       ("user6@user6", "userpass6", "username6", 11111116, "M", "11111111116"),
       ("user7@user7", "userpass7", "username7", 11111117, "M", "11111111117"),
       ("user8@user8", "userpass8", "username8", 11111118, "M", "11111111118"),
       ("user9@user9", "userpass9", "username9", 11111119, "M", "11111111119");
       
INSERT INTO mentor(email, password, name, dateOfBirth, gender, phoneNumber)
VALUES ("mentor1@mentor1", "mentorpass1", "mentorname1", 11111111, "M", "11111111111"),
	   ("mentor2@mentor2", "mentorpass2", "mentorname2", 11111112, "M", "11111111112"),
       ("mentor3@mentor3", "mentorpass3", "mentorname3", 11111113, "M", "11111111113"),
       ("mentor4@mentor4", "mentorpass4", "mentorname4", 11111114, "M", "11111111114"),
       ("mentor5@mentor5", "mentorpass5", "mentorname5", 11111115, "M", "11111111115"),
       ("mentor6@mentor6", "mentorpass6", "mentorname6", 11111116, "M", "11111111116"),
       ("mentor7@mentor7", "mentorpass7", "mentorname7", 11111117, "M", "11111111117"),
       ("mentor8@mentor8", "mentorpass8", "mentorname8", 11111118, "M", "11111111118"),
       ("mentor9@mentor9", "mentorpass9", "mentorname9", 11111119, "M", "11111111119");

INSERT INTO category(name)
VALUES ("헬스"), ("필라테스"), ("골프"), ("테니스"), ("탁구"), ("축구"), ("농구"), ("러닝"), ("산악"), ("싸이클"),
       ("클라이밍"), ("댄스"), ("비보잉"), ("보드"), ("권투"), ("유도"), ("수영"), ("입시"), ("재활"), ("특수");
       


-- 랜덤하게 채팅 생성하는 함수  
     
DELIMITER $$

CREATE PROCEDURE InsertDummyData()
BEGIN
    DECLARE i INT DEFAULT 0;
    
    WHILE i < 300 DO
        SET @mentorId = FLOOR(RAND() * 9) + 1; -- 1부터 10까지의 랜덤한 mentorId 생성
        SET @userId = FLOOR(RAND() * 9) + 1; -- 1부터 10까지의 랜덤한 userId 생성
        SET @content = CONCAT('멘토', @mentorId, '와 사용자', @userId, '의 대화입니다.');
        SET @isUser = FLOOR(RAND() * 2); -- 0 또는 1 랜덤 생성
        
        INSERT INTO `chat` (`mentorId`, `userId`, `content`, `isUser`) VALUES
        (@mentorId, @userId, @content, @isUser);
        
        SET i = i + 1;
    END WHILE;
END$$

DELIMITER ;

CALL InsertDummyData();


