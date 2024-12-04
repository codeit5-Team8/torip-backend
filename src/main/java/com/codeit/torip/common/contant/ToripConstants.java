package com.codeit.torip.common.contant;

public class ToripConstants {

    public static class Note {
        public static final Integer PAGE_OFFSET = 5;
    }

    public static class Task {
        public static final Integer PAGE_OFFSET = 30;
    }

    public static class HttpConstant {
        public static final Integer SUCCESS_CODE = 200;
        public static final Integer SERVER_FAIL_CODE = 500;
        public static final Integer CLIENT_FAIL_CODE = 400;
        public static final Integer FORBIDDEN_CODE = 403;
        public static final Integer METHOD_NOT_ALLOWED_CODE = 405;

        public static final String SUCCESS_MESSAGE = "성공";
        public static final String FAIL_MESSAGE = "실패";
    }

}
