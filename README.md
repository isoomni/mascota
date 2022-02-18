# Mascota 

## :octocat: api 명세

| Method | URI | Description | 개발 | 서버 |
| ------ | -- | -- |--------------- |-------------- |
| GET | /readies/all/:userIdx/:petIdx | [준비하기 전체 질문 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready) |☑️|  | 
| GET | /readies/ones/:userIdx/:readyAnswerIdx | [준비하기 개별 질문 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready)  |☑️|  | 
| POST | /readies/one/:userIdx/:petIdx/:readyQuestionIdx | [준비하기 작성](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready)  |☑️|  | 
| PATCH |/readies/one/:userIdx/:readyAnswerIdx| [준비하기 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/ready) |☑️|  | 
| GET | /memories/notAnsweredMemories/:userIdx/:petIdx | [추억하기 전체 질문 조회 (답변하기 탭)](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|  | 
| GET | /memories/answeredMemories/:userIdx/:petIdx | [추억하기 전체 질문 조회 (모아보기 탭)](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|  | 
| GET | /memories/:userIdx/:readyAnswerIdx | [추억하기 개별 질문 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|  | 
| PATCH | /memories/one/:userIdx/:memoryAnswerIdx | [추억하기 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|  | 
| POST | /memories/one/:userIdx/:petIdx/:memoryQuestionIdx | [추억하기 작성](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/memory) |☑️|  | 
| GET | /myPages/:userIdx | [마이페이지 전체 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️|  | 
| GET | /myPages/myInfo/:userIdx | [마이페이지 개인정보 조회](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️|  | 
| PATCH | /myPages/bookInfo/:userIdx | [책 표지 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️|  | 
| PATCH | /myPages/petInfo/:userIdx/:petIdx | [동물 프로필 수정](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️|  | 
| PATCH | /myPages/petInfo/:userIdx/:petIdx/status | [동물 프로필 삭제](https://github.com/isoomni/mascota/tree/master/src/main/java/com/example/demo/src/my) |☑️|  | 
| GET |  | [기록하기 일기 상세 조회]() || | 
| GET |  | [캘린더 조회]() | |  | 
