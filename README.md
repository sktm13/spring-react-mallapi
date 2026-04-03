mallapi
프로젝트 소개

이 프로젝트는 강의를 기반으로 학습 목적으로 구현한 쇼핑몰 API 서비스입니다.
기본적인 구조는 강의를 참고했으며, 일부 기능은 직접 구현 및 개선했습니다.

참고 강의
강의명: (작성)
플랫폼: (작성)
링크: (선택)
주요 기능
인증 / 회원
JWT 기반 로그인 / 인증 처리
Access Token / Refresh Token 구조 구현
Spring Security 기반 권한 관리
로그인 성공 / 실패 핸들러 처리 (JSON 응답)
상품
상품 등록 / 조회 / 수정 / 삭제 (CRUD)
파일 업로드 기반 이미지 관리
썸네일 이미지 생성 및 제공
장바구니
사용자별 장바구니 생성
상품 추가 / 수량 변경 / 삭제
장바구니 목록 조회
추가 구현 / 개선 사항
JWT 필터를 활용한 인증 흐름 구현 (JWTCheckFilter)
Refresh Token 재발급 API 구현
React + Redux Toolkit 기반 로그인 상태 관리 (적용)
쿠키를 활용한 로그인 유지 기능 (구현)
상품 이미지 썸네일 처리 및 기본 이미지 fallback 처리 (구현)
예외 처리 및 로그 기반 디버깅 (일부 개선)
Tech Stack
Backend
Java 17
Spring Boot
Spring Security
Spring Data JPA
Querydsl
MariaDB
Gradle
Frontend
React
TypeScript
Redux Toolkit
React Router
Axios
프로젝트 구조

mallapi/
├── backend/
└── frontend/

실행 방법
Backend

./gradlew bootRun

Frontend

npm install
npm run dev

실행 화면

(여기에 이미지/GIF 추가)

느낀 점
Spring Security와 JWT 인증 흐름에 대한 이해도 향상
인증 필터 및 토큰 기반 구조 흐름 이해
프론트엔드와 백엔드 연동 경험 확보
