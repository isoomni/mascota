package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    LISTS_EMPTY_USER_ID(false, 2011, "목차 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_DATE(false, 2016, "날짜 형식을 확인해주세요."),
    POST_USERS_EXISTS_ID(false,2017,"중복된 ID입니다."),
    POST_DIARYS_EXISTS_TITLE(false,2018,"제목을 입력해주세요."),
    POST_DIARYS_EXISTS_CONTEXT(false,2019,"내용을 입력해주세요."),
    POST_DIARYS_EXISTS_DATE(false,2020,"날짜를 입력해주세요."),
    POST_DIARYS_EXISTS_IMG(false,2021,"이미지를 입력해주세요."),
    POST_DIARYS_EXISTS_MOOD(false,2022,"기분을 입력해주세요."),
    POST_DIARYS_EXISTS_ELEMENT(false,2023,"기분을 선택해주세요."),
    POST_DIARYS_EXCEED_IMG(false,2024,"이미지는 최대 3개 입니다."),
    POST_DIARYS_NONE(false,2025,"해당 다이어리는 존재하지 않습니다."),
    FAIL_DIARYS_MODIFY(false,2026,"해당 다이어리를 변경하는데 실패했습니다."),
    FAIL_LISTS_ADD(false,2027,"목차를 변경하는데 실패했습니다."),
    ADD_LISTS_TEXT_EMPTY(false,2028,"목차내용을 입력해주세요."),
    NONE_USER_EXIST(false,2029,"존재하지 않는 사용자입니다."),
    NONE_ID_EXIST(false,2030,"ID를 입력해주세요."),
    NONE_PASSWORD_EXIST(false,2031,"패스워드를 입력해주세요."),
    NONE_NICKNAME_EXIST(false,2032,"작가명을 입력해주세요."),
    NONE_TITLE_EXIST(false,2033,"제목을 입력해주세요."),
    CHANGE_NICKNAME_EXIST(false,2033,"변경할 작가명을 입력해주세요."),
    NONE_USER_HAVE(false,2034,"해당 목차의 작가가 아닙니다."),
    NONE_DIARY_EXIST(false,2035,"이미 삭제된 일기입니다."),
    FAIL_LISTS_DEL(false,2036,"목차를 삭제하는데 실패했습니다."),
    FAIL_USER_LEN(false,2037,"작가명은 최대 6글자입니다."),
    ALREADY_USER_EXIST(false,2038,"중복된 사용자입니다."),
    NONE_NAME_EXIST(false,2039,"이름을 입력해주세요."),
    NONE_IMG_EXIST(false,2040,"이미지를 입력해주세요."),
    NONE_TYPE_EXIST(false,2041,"타입을 입력해주세요."),
    NONE_BIRTH_EXIST(false,2042,"날짜를 입력해주세요."),
    NONE_UPDATE_EXIST(false,2043,"변경할 항목이 없습니다."),
    NONE_PET_EXIST(false,2044,"이미 삭제된 주인공입니다."),
    NONE_DIARY_WRITE(false,2045,"해당 다이어리의 작성자가 아닙니다."),
    NONE_TYPED(false,2046,"타입 형식을 올바르게 입력해주세요."),
    NONE_PAGE(false,2047,"해당 페이지는 존재하지 않습니다."),
    ALREADY_PET_EXIST(false,2048,"중복된 반려동물 이름입니다."),
    NONE_PETS_EXIST(false,2049,"존재하지 않는 반려동물입니다."),
    UNVALID_FILTER(false, 2050, "해당하는 필터가 없습니다."),

    MODIFY_FAIL(false, 2070, "준비하기 답변 수정에 실패했습니다."),
    EMPTY_BOOK_TITLE(false, 2071, "책 제목을 입력해주세요"),
    EMPTY_WRITER_NICKNAME(false, 2072, "작가명을 입력해주세요"),
    OVER_TWELVE_CHAR(false,2073,"12자 이내로 작성해주세요"),
    FAIL_MODIFY_BOOK(false,2074,"책 표지를 수정하는데 실패했습니다."),

    EMPTY_PET_NAME(false,2075,"반려동물 이름을 입력하세요"),
    EMPTY_PET_TYPE(false,2076,"반려동물 종을 입력하세요"),
    EMPTY_PET_FAMILY_DATE(false,2077,"반려동물이 가족이 된 날짜를 입력하세요"),
    OVER_SIX_CHAR(false,2078,"6자 이내로 작성해주세요"),
    EMPTY_PET_STATUS(false,2079,"반려동물 상태를 입력하세요"),
    FAIL_MODIFY_PET_INFO(false,2080,"반려동물 정보를 수정하는데 실패했습니다."),
    FAIL_DELETE_PET(false,2081,"반려동물 정보를 삭제하는데 실패했습니다."),

    EXIST_ANSWER(false,2081,"해당 질문에 대한 답변이 이미 작성되었습니다."),

    ALREADY_DELETE_ANSWER(false,2082, "이미 삭제된 답변입니다"),
    IDX_NOT_EXIST(false,2083,"존재하지 않는 idx입니다."),
    EMPTY_USER_OLD_PASSWORD(false,2084,"비밀번호를 입력하세요"),
    EMPTY_USER_NEW_PASSWORD(false,2085,"신규 비밀번호를 입력하세요"),
    REENTER_PASSWORD_IS_DIFFERENT(false,2086,"재확인 비밀번호가 일치하지 않습니다."),

    POST_USERS_INVALID_PASSWORD(false,2087,"비밀번호 형식을 확인해주세요."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    DATABASE_EXIST_ERROR(false, 4001, "데이터베이스에 존재하는지 여부 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4002, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
    PASSWORD_ERROR(false, 4013, "비밀번호 인증에 실패하였습니다."),
    PASSWORD_MODIFY_ERROR(false, 4014, "비밀번호 변경에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
