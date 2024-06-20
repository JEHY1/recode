-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- 생성 시간: 24-06-19 01:04
-- 서버 버전: 8.0.36
-- PHP 버전: 8.3.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `recode`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `address_tb`
--

CREATE TABLE `address_tb` (
  `address_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `address_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address_recipient_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '택배 수령자 이름',
  `address_postal_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `address_road_name_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '도로명 주소',
  `address_detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상세 주소',
  `address_recipient_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '택배 수령자 전화번호',
  `address_delivery_request` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '택배 요청사항(선택지)',
  `address_front_door_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '현관 비밀번호',
  `address_delivery_box_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '택배함 번호',
  `address_default` int NOT NULL COMMENT '기본 배송지 설정 여부'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `address_tb`
--

INSERT INTO `address_tb` (`address_id`, `user_id`, `address_nickname`, `address_recipient_name`, `address_postal_code`, `address_road_name_address`, `address_detail_address`, `address_recipient_phone`, `address_delivery_request`, `address_front_door_secret`, `address_delivery_box_num`, `address_default`) VALUES
(6, 2, '집', '김재현', '50650', '경남 양산시 동면 금오16길 65', '106동 1503호', '01048527204', '택배함', '#45682', '0215', 0),
(8, 2, 'test', 'eee', '03085', '서울 종로구 낙산길 3-4', 'sdfsdf', '11111100000', '문 앞', '', '', 0),
(11, 2, '배송지3', '김재현3', '08782', '서울 관악구 남부순환로202길 28', '상세주소 3', '01048527204', '직접 받고 부재 시 문 앞', '11235', NULL, 0),
(12, 1, 'user1address1', 'user1', '63275', '제주특별자치도 제주시 가령골길 1', 'detail1', '11111111111', '택배함', '1122', '1111', 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `cart_tb`
--

CREATE TABLE `cart_tb` (
  `cart_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `cart_tb`
--

INSERT INTO `cart_tb` (`cart_id`, `user_id`, `product_id`) VALUES
(53, 1, 2),
(54, 1, 3);

-- --------------------------------------------------------

--
-- 테이블 구조 `history_tb`
--

CREATE TABLE `history_tb` (
  `history_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `history_tb`
--

INSERT INTO `history_tb` (`history_id`, `product_id`, `user_id`) VALUES
(1, 2, 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `notice_tb`
--

CREATE TABLE `notice_tb` (
  `notice_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `notice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '공지 제목',
  `notice_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '공지 내용',
  `notice_create_date` datetime NOT NULL COMMENT '공지사항 작성일자',
  `notice_views` int NOT NULL COMMENT '공지사항 조회수'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `notice_tb`
--

INSERT INTO `notice_tb` (`notice_id`, `user_id`, `notice_title`, `notice_content`, `notice_create_date`, `notice_views`) VALUES
(1, 1, 'TestNoticeTitle', 'TestNoticeContent', '2024-06-05 07:35:59', 10),
(2, 2, '공지사항1', '공지', '2024-06-18 13:42:22', 1),
(3, 2, '공지사항1', '공지', '2024-06-18 14:12:56', 1),
(4, 2, '공지사항2', '공지22222', '2024-06-18 15:34:03', 1),
(5, 2, '공지사항3', '공지3333', '2024-06-18 15:34:37', 1),
(6, 2, '공지사항4', '공지44444', '2024-06-18 15:37:08', 1),
(7, 2, '공지사항5', '공지555555', '2024-06-18 15:40:55', 1),
(8, 2, '공지사항6', '공지66666', '2024-06-18 15:41:17', 1),
(9, 2, '공지사항7', '공지66666', '2024-06-18 15:54:02', 1),
(10, 2, '공지사항8', '공지66666', '2024-06-18 15:57:14', 1),
(11, 2, '공지사항9', '공지9', '2024-06-18 16:57:49', 1),
(12, 2, '공지사항11', '공지10', '2024-06-19 09:26:38', 1),
(13, 2, '공지사항11', '공지11', '2024-06-19 09:30:00', 1),
(14, 2, '공지사항13', '공지12', '2024-06-19 09:31:55', 1);

-- --------------------------------------------------------

--
-- 테이블 구조 `payment_detail_tb`
--

CREATE TABLE `payment_detail_tb` (
  `payment_detail_id` bigint NOT NULL,
  `payment_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `payment_detail_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 판매 상태',
  `payment_detail_price` int NOT NULL COMMENT '상품 구매가'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `payment_detail_tb`
--

INSERT INTO `payment_detail_tb` (`payment_detail_id`, `payment_id`, `product_id`, `payment_detail_status`, `payment_detail_price`) VALUES
(1, 1, 2, '배송 준비중', 80000);

-- --------------------------------------------------------

--
-- 테이블 구조 `payment_tb`
--

CREATE TABLE `payment_tb` (
  `payment_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `payment_price` int NOT NULL COMMENT '결제 금액',
  `payment_date` datetime NOT NULL COMMENT '결제일',
  `payment_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '결제 방식',
  `payment_bank` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '계좌이체시 사용 은행',
  `payment_account_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '계좌이체시 사용한 계좌번호',
  `payment_card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '카드결제시 사용한 카드사',
  `payment_installment` int DEFAULT NULL COMMENT '할부 기간',
  `payment_micropayment_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '핸드폰 결제시 사용한 번호',
  `payment_deposit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '무통장 입금시 계좌',
  `payment_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '결제 상태',
  `payment_postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '우편주소',
  `payment_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '배송지 주소',
  `payment_delivery_request` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '배송 요청사항',
  `payment_recipient_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '택배 수령자 이름',
  `payment_recipient_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '택배 수령자 전화번호',
  `payment_delivery_fee` int NOT NULL COMMENT '배송비',
  `payment_front_door_secret` varchar(50) DEFAULT NULL COMMENT '현관비밀번호',
  `payment_delivery_box_num` varchar(255) DEFAULT NULL COMMENT '택배함 번호'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `payment_tb`
--

INSERT INTO `payment_tb` (`payment_id`, `user_id`, `payment_price`, `payment_date`, `payment_type`, `payment_bank`, `payment_account_number`, `payment_card`, `payment_installment`, `payment_micropayment_phone`, `payment_deposit`, `payment_status`, `payment_postal_code`, `payment_address`, `payment_delivery_request`, `payment_recipient_name`, `payment_recipient_phone`, `payment_delivery_fee`, `payment_front_door_secret`, `payment_delivery_box_num`) VALUES
(1, 1, 80000, '2024-06-05 07:38:18', '계좌이체', '하나은행', '5446121656', NULL, NULL, NULL, NULL, '입금완료', '45651', '주소주소주소주소주소주소주소주소주소주소', '문 앞', '홍길동', '010-0000-0000', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- 테이블 구조 `product_img_tb`
--

CREATE TABLE `product_img_tb` (
  `product_img_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `product_img_src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 설명 이미지'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `product_img_tb`
--

INSERT INTO `product_img_tb` (`product_img_id`, `product_id`, `product_img_src`) VALUES
(1, 2, '/images/productDetail/testProductDetailImg01.jpg'),
(2, 2, '/images/productDetail/testProductDetailImg02.jpg'),
(3, 2, '/images/productDetail/testProductDetailImg03.jpg'),
(4, 2, '/images/productDetail/testProductDetailImg04.jpg'),
(5, 3, '/images/productDetail/testproductDetailImg06.webp'),
(6, 3, '/images/productDetail/testproductDetailImg07.webp');

-- --------------------------------------------------------

--
-- 테이블 구조 `product_tb`
--

CREATE TABLE `product_tb` (
  `product_id` bigint NOT NULL,
  `product_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 이름',
  `product_regular_price` int NOT NULL COMMENT '정가',
  `product_discount_price` int DEFAULT NULL COMMENT '할인가',
  `product_model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '모델명',
  `product_size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 규격',
  `product_material` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 재질',
  `product_representative_img_src` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 대표 이미지',
  `product_registration_date` datetime NOT NULL COMMENT '상품 등록일',
  `product_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 카테고리',
  `product_sold` int NOT NULL COMMENT '판매 여부',
  `product_color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `product_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '상품 종류'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `product_tb`
--

INSERT INTO `product_tb` (`product_id`, `product_name`, `product_regular_price`, `product_discount_price`, `product_model`, `product_size`, `product_material`, `product_representative_img_src`, `product_registration_date`, `product_category`, `product_sold`, `product_color`, `product_type`) VALUES
(2, 'TestProduct', 100000, 78000, 'abc-0000', '상품 규격 설명\r\n상품 규격 설명\r\n상품 규격 설명', '상품 재질', '/images/productRep/testRepProductImg.webp', '2024-06-05 02:43:16', '백팩', 1, 'red', '스포츠'),
(3, 'TestProduct2', 350000, NULL, 'aaa-1234', '123', '123', '/images/productRep/testRepProductImg2.jpg', '2024-06-10 08:00:49', '크로스백', 1, 'green', 'mmgs');

-- --------------------------------------------------------

--
-- 테이블 구조 `qna_tb`
--

CREATE TABLE `qna_tb` (
  `qna_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `qna_question_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '질문 제목',
  `qna_question_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '질문 내용',
  `qna_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '답변',
  `qna_create_date` datetime NOT NULL COMMENT 'QnA등록 일자',
  `qna_views` int NOT NULL COMMENT 'QnA 조회수',
  `qna_answer_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `qna_tb`
--

INSERT INTO `qna_tb` (`qna_id`, `user_id`, `product_id`, `qna_question_title`, `qna_question_content`, `qna_answer`, `qna_create_date`, `qna_views`, `qna_answer_date`) VALUES
(1, 1, 2, 'testQnATitle', 'TestQnAContent', 'testQnAAnswer', '2024-06-14 15:28:12', 2, '2024-06-05 09:20:53'),
(2, 1, 2, 'inputTitle', 'input Question content', 'input answer', '2024-06-05 15:39:04', 3, '2024-06-03 09:20:53'),
(3, 1, 2, 'inputTitle', 'input Question content', 'input answer', '2024-06-05 15:39:36', 3, '2024-06-06 09:20:53'),
(4, 1, 2, 'QnATestTitle', 'QnATestContent4', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(5, 1, 2, 'QnATestTitle', 'QnATestContent5', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(6, 1, 2, 'QnATestTitle', 'QnATestContent6', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(7, 1, 2, 'QnATestTitle', 'QnATestContent7', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(8, 1, 2, 'QnATestTitle', 'QnATestContent8', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(9, 1, 2, 'QnATestTitle', 'QnATestContent9', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(10, 1, 2, 'QnATestTitle', 'QnATestContent10', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(11, 1, 2, 'QnATestTitle', 'QnATestContent11', NULL, '2024-06-10 07:28:43', 4, '2024-06-10 07:28:43'),
(12, 1, 2, 'sadfsadf', 'QnATestContent12', NULL, '2024-06-10 07:32:58', 3, '2024-06-10 07:32:58'),
(13, 1, 3, '질문 제목', '질문 답변', NULL, '2024-06-13 06:04:03', 3, '2024-06-13 06:04:03');

-- --------------------------------------------------------

--
-- 테이블 구조 `review_img_tb`
--

CREATE TABLE `review_img_tb` (
  `review_img_id` bigint NOT NULL,
  `review_id` bigint NOT NULL,
  `review_img_src` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '리뷰 이미지'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `review_img_tb`
--

INSERT INTO `review_img_tb` (`review_img_id`, `review_id`, `review_img_src`) VALUES
(1, 1, '/img/#');

-- --------------------------------------------------------

--
-- 테이블 구조 `review_tb`
--

CREATE TABLE `review_tb` (
  `review_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `review_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '리뷰 제목',
  `review_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '리뷰 내용',
  `review_create_date` datetime NOT NULL COMMENT '리뷰 작성일자',
  `review_score` int NOT NULL COMMENT '상품 평점',
  `review_views` int NOT NULL COMMENT '리뷰 조회수'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `review_tb`
--

INSERT INTO `review_tb` (`review_id`, `user_id`, `product_id`, `review_title`, `review_content`, `review_create_date`, `review_score`, `review_views`) VALUES
(1, 1, 2, 'TestReviewTitle', 'TestReviewContent', '2024-06-05 07:41:20', 4, 22);

-- --------------------------------------------------------

--
-- 테이블 구조 `user_tb`
--

CREATE TABLE `user_tb` (
  `user_id` bigint NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '유저 아이디',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '유저 비밀번호',
  `user_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '유저 이름',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '유저 전화번호',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '유저 이메일',
  `user_submit_date` datetime NOT NULL COMMENT '회원 가입 일자',
  `user_role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '권한'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 테이블의 덤프 데이터 `user_tb`
--

INSERT INTO `user_tb` (`user_id`, `username`, `user_password`, `user_real_name`, `user_phone`, `user_email`, `user_submit_date`, `user_role`) VALUES
(1, 'testUser', '1234', 'TestUser', '010-0000-0000', 'TestUserEmail@TesetUserEmail.com', '2024-06-05 16:16:16', 'COSTOMER'),
(2, 'test', '$2a$10$E6IjkZuRgx98BOalGduGhuH1Iuxi1nitR5pmGeCFkjKVIw8P19SZu', '황예지', '01049162815', 'test@naver.com', '2024-06-17 12:31:36', 'ADMIN'),
(6, 'test2', '$2a$10$NOl8fBFiQfQ.9v/BH/48hecX0MNpewRwIGJ8GdGKyhhbympyJCwDW', '황예지', '01049162810', 'test2@naver.com', '2024-06-17 14:09:32', 'COSTOMER'),
(7, 'test3', '$2a$10$19XfUSYgq4mZmVYgzJ67h.8kfFrK1NN/X2xH/kjgapYhyVkHkNmfG', '황예지', '01049162812', 'test3@naver.com', '2024-06-17 15:40:34', 'COSTOMER');

--
-- 덤프된 테이블의 인덱스
--

--
-- 테이블의 인덱스 `address_tb`
--
ALTER TABLE `address_tb`
  ADD PRIMARY KEY (`address_id`),
  ADD KEY `user_id` (`user_id`);

--
-- 테이블의 인덱스 `cart_tb`
--
ALTER TABLE `cart_tb`
  ADD PRIMARY KEY (`cart_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- 테이블의 인덱스 `history_tb`
--
ALTER TABLE `history_tb`
  ADD PRIMARY KEY (`history_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `user_id` (`user_id`);

--
-- 테이블의 인덱스 `notice_tb`
--
ALTER TABLE `notice_tb`
  ADD PRIMARY KEY (`notice_id`),
  ADD KEY `user_id` (`user_id`);

--
-- 테이블의 인덱스 `payment_detail_tb`
--
ALTER TABLE `payment_detail_tb`
  ADD PRIMARY KEY (`payment_detail_id`),
  ADD KEY `payment_id` (`payment_id`),
  ADD KEY `product_id` (`product_id`);

--
-- 테이블의 인덱스 `payment_tb`
--
ALTER TABLE `payment_tb`
  ADD PRIMARY KEY (`payment_id`),
  ADD KEY `user_id` (`user_id`);

--
-- 테이블의 인덱스 `product_img_tb`
--
ALTER TABLE `product_img_tb`
  ADD PRIMARY KEY (`product_img_id`),
  ADD KEY `product_id` (`product_id`);

--
-- 테이블의 인덱스 `product_tb`
--
ALTER TABLE `product_tb`
  ADD PRIMARY KEY (`product_id`);

--
-- 테이블의 인덱스 `qna_tb`
--
ALTER TABLE `qna_tb`
  ADD PRIMARY KEY (`qna_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- 테이블의 인덱스 `review_img_tb`
--
ALTER TABLE `review_img_tb`
  ADD PRIMARY KEY (`review_img_id`),
  ADD KEY `review_id` (`review_id`);

--
-- 테이블의 인덱스 `review_tb`
--
ALTER TABLE `review_tb`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

--
-- 테이블의 인덱스 `user_tb`
--
ALTER TABLE `user_tb`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_name` (`username`) USING BTREE,
  ADD UNIQUE KEY `user_phone` (`user_phone`),
  ADD UNIQUE KEY `user_email` (`user_email`);

--
-- 덤프된 테이블의 AUTO_INCREMENT
--

--
-- 테이블의 AUTO_INCREMENT `address_tb`
--
ALTER TABLE `address_tb`
  MODIFY `address_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- 테이블의 AUTO_INCREMENT `cart_tb`
--
ALTER TABLE `cart_tb`
  MODIFY `cart_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- 테이블의 AUTO_INCREMENT `history_tb`
--
ALTER TABLE `history_tb`
  MODIFY `history_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 테이블의 AUTO_INCREMENT `notice_tb`
--
ALTER TABLE `notice_tb`
  MODIFY `notice_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- 테이블의 AUTO_INCREMENT `payment_detail_tb`
--
ALTER TABLE `payment_detail_tb`
  MODIFY `payment_detail_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 테이블의 AUTO_INCREMENT `payment_tb`
--
ALTER TABLE `payment_tb`
  MODIFY `payment_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 테이블의 AUTO_INCREMENT `product_img_tb`
--
ALTER TABLE `product_img_tb`
  MODIFY `product_img_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 테이블의 AUTO_INCREMENT `product_tb`
--
ALTER TABLE `product_tb`
  MODIFY `product_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- 테이블의 AUTO_INCREMENT `qna_tb`
--
ALTER TABLE `qna_tb`
  MODIFY `qna_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- 테이블의 AUTO_INCREMENT `review_img_tb`
--
ALTER TABLE `review_img_tb`
  MODIFY `review_img_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 테이블의 AUTO_INCREMENT `review_tb`
--
ALTER TABLE `review_tb`
  MODIFY `review_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 테이블의 AUTO_INCREMENT `user_tb`
--
ALTER TABLE `user_tb`
  MODIFY `user_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- 덤프된 테이블의 제약사항
--

--
-- 테이블의 제약사항 `address_tb`
--
ALTER TABLE `address_tb`
  ADD CONSTRAINT `address_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `cart_tb`
--
ALTER TABLE `cart_tb`
  ADD CONSTRAINT `cart_tb_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `cart_tb_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `history_tb`
--
ALTER TABLE `history_tb`
  ADD CONSTRAINT `history_tb_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `history_tb_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `notice_tb`
--
ALTER TABLE `notice_tb`
  ADD CONSTRAINT `notice_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `payment_detail_tb`
--
ALTER TABLE `payment_detail_tb`
  ADD CONSTRAINT `payment_detail_tb_ibfk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment_tb` (`payment_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `payment_detail_tb_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `payment_tb`
--
ALTER TABLE `payment_tb`
  ADD CONSTRAINT `payment_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `product_img_tb`
--
ALTER TABLE `product_img_tb`
  ADD CONSTRAINT `product_img_tb_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `qna_tb`
--
ALTER TABLE `qna_tb`
  ADD CONSTRAINT `qna_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `qna_tb_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `review_img_tb`
--
ALTER TABLE `review_img_tb`
  ADD CONSTRAINT `review_img_tb_ibfk_1` FOREIGN KEY (`review_id`) REFERENCES `review_tb` (`review_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 테이블의 제약사항 `review_tb`
--
ALTER TABLE `review_tb`
  ADD CONSTRAINT `review_tb_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_tb` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `review_tb_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product_tb` (`product_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
