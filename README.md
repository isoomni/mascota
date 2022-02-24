# Mascota 

👉 Base URL : https://mascota.kr

</br>

## :octocat: api 명세


| Method | URI | Description | 구현완료 |
|:-------------:| -- | -- |:-------------:|
||:house_with_garden:**HOME**|
| GET | /homes/:userIdx | [홈 조회](https://organic-manta-fd9.notion.site/1-3ba2d3438e7d48afba1782e8abddae25) |☑️|  
||:evergreen_tree:**준비하기**|
| GET |/readies/questions/:userIdx/:petIdx| [준비하기 전체 질문 조회](https://organic-manta-fd9.notion.site/1-77adc3648008451b84466cda6fdc62d1) |☑️| 
| GET | /readies/answers/:userIdx/:readyAnswerIdx | [준비하기 답변 조회](https://organic-manta-fd9.notion.site/1-93e864f5374f41e4ba8276d87db55463)  |☑️| 
| POST | /readies/answers/:userIdx/:petIdx/:readyQuestionIdx | [준비하기 답변 작성](https://organic-manta-fd9.notion.site/1-259c835ac26f447e91c30456d057f9f9)  |☑️| 
| PATCH |/readies/answers/:userIdx/:readyAnswerIdx| [준비하기 답변 수정](https://organic-manta-fd9.notion.site/1-120f9198b9484d4d8c50723c60a1b5b6) |☑️|
| PATCH |/readies/answers/:userIdx/:readyAnswerIdx/status| [준비하기 답변 삭제](https://organic-manta-fd9.notion.site/1-d86432d0898d4ca49079c4ee978189e1) |☑️|
||:droplet:**추억하기**|
| GET | /memories/questions/unanswered/:userIdx/:petIdx | [추억하기 전체 질문 조회 (답변하기 탭)](https://organic-manta-fd9.notion.site/1-a5b359e04cc94a8ca98eb2f0f7653598) |☑️|
| GET |/memories/questions/answered/:userIdx/:petIdx| [추억하기 전체 질문 조회 (모아보기 탭)](https://organic-manta-fd9.notion.site/1-f79b0dbae5d249c69467d14111184eee) |☑️| 
| GET |/memories/answers/:userIdx/:memoryAnswerIdx| [추억하기 답변 조회](https://organic-manta-fd9.notion.site/1-910bdd9bbac74c1e8145625233d492b3) |☑️|
| POST | /memories/one/:userIdx/:petIdx/:memoryQuestionIdx | [추억하기 답변 작성](https://organic-manta-fd9.notion.site/1-9f0b9cf3a472483094dbdeacbbffbf4b) |☑️| 
| PATCH |/memories/answers/:userIdx/:memoryAnswerIdx| [추억하기 답변 수정](https://organic-manta-fd9.notion.site/1-b956d0b7582d41298ab675a16e484eb5) |☑️| 
| PATCH |/memories/answers/:userIdx/:memoryAnswerIdx/status| [추억하기 답변 삭제](https://organic-manta-fd9.notion.site/1-510d2da65e384e87836ff6b5407a2905) |☑️| 
||:closed_lock_with_key:**마이페이지**|
| GET | /myPages/:userIdx | [마이페이지 전체 조회](https://organic-manta-fd9.notion.site/1-076eda2078b244178dd88c2aa2688761) |☑️|
| GET | /myPages/myInfo/:userIdx | [마이페이지 개인정보 조회](https://organic-manta-fd9.notion.site/1-9e928c30b0dc4e7f83a448773e1c674e) |☑️| 
| PATCH | /myPages/bookInfo/:userIdx | [마이페이지 책 표지 수정](https://organic-manta-fd9.notion.site/1-5774eaf92e44454bb419d76e4d0c1c5e) |☑️| 
| PATCH | /myPages/petInfo/:userIdx/:petIdx | [마이페이지 반려동물 프로필 수정](https://organic-manta-fd9.notion.site/1-fcb72ca892bb4a0f98a00e5703b123e0) |☑️| 
| PATCH | /myPages/petInfo/:userIdx/:petIdx/status | [마이페이지 반려동물 프로필 삭제](https://organic-manta-fd9.notion.site/1-5722d9316063480ab02d65503dd0c79e) |☑️| 
| PATCH | /mypages/user-info/password/:userIdx | [마이페이지 비밀번호 변경](https://organic-manta-fd9.notion.site/1-91b917b8111f445499509145c8273664) |☑️| 
||:date:**캘린더**|
| GET | /calendar/user-moods/:userId/:diaryIdx | 유저 기분 캘린더 조회 | | 
| GET |/calendar/pet-moods/:userId/:diaryIdx/:petIdx | 반려동물 기분 캘린더 조회 | | 
