# Mascota 

## :octocat: api 명세

| Method | URI | Description | 구현완료 |
|:-------------:| -- | -- |:-------------:|
||:house_with_garden:**HOME**|
| GET | /homes/:userIdx | [홈 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready) |☑️|  
||:evergreen_tree:**준비하기**|
| GET |/readies/questions/:userIdx/:petIdx| [준비하기 전체 질문 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready) |☑️| 
| GET | /readies/answers/:userIdx/:readyAnswerIdx | [준비하기 답변 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready)  |☑️| 
| POST | /readies/answers/:userIdx/:petIdx/:readyQuestionIdx | [준비하기 답변 작성](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready)  |☑️| 
| PATCH |/readies/answers/:userIdx/:readyAnswerIdx| [준비하기 답변 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready) |☑️|
| PATCH |/readies/answers/:userIdx/:readyAnswerIdx/status| [준비하기 답변 삭제](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready) |☑️|
||:droplet:**추억하기**|
| GET | /memories/questions/unanswered/:userIdx/:petIdx | [추억하기 전체 질문 조회 (답변하기 탭)](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|
| GET |/memories/questions/answered/:userIdx/:petIdx| [추억하기 전체 질문 조회 (모아보기 탭)](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️| 
| GET |/memories/answers/:userIdx/:memoryAnswerIdx| [추억하기 답변 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|
| POST | /memories/one/:userIdx/:petIdx/:memoryQuestionIdx | [추억하기 답변 작성](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️| 
| PATCH |/memories/answers/:userIdx/:memoryAnswerIdx| [추억하기 답변 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️| 
| PATCH |/memories/answers/:userIdx/:memoryAnswerIdx/status| [추억하기 답변 삭제](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️| 
||:closed_lock_with_key:**마이페이지**|
| GET | /myPages/:userIdx | [마이페이지 전체 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️|
| GET | /myPages/myInfo/:userIdx | [마이페이지 개인정보 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️| 
| PATCH | /myPages/bookInfo/:userIdx | [마이페이지 책 표지 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️| 
| PATCH | /myPages/petInfo/:userIdx/:petIdx | [마이페이지 반려동물 프로필 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️| 
| PATCH | /myPages/petInfo/:userIdx/:petIdx/status | [마이페이지 반려동물 프로필 삭제](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️| 
| PATCH | /mypages/user-info/password/:userIdx | [마이페이지 비밀번호 변경](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️| 
||:date:**캘린더**|
| GET | /calendar/user-moods/:userId/:diaryIdx | 유저 기분 캘린더 조회 | | 
| GET |/calendar/pet-moods/:userId/:diaryIdx/:petIdx | 반려동물 기분 캘린더 조회 | | 
