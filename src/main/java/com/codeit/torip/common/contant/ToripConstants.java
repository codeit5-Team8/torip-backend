package com.codeit.torip.common.contant;

public class ToripConstants {

    public static class Note {
        public static final Integer PAGE_SIZE = 5;
    }

    public static class Task {
        public static final Integer PAGE_SIZE = 30;
        public static final Integer TASK_LIMIT_PER_TRIP_STATUS = 60;
        public static final Integer TASK_LIMIT = 180;
    }

    public static class HttpConstant {
        public static final Integer SUCCESS_CODE = 200;
        public static final Integer SERVER_FAIL_CODE = 500;
        public static final Integer CLIENT_FAIL_CODE = 400;
        public static final String SUCCESS_MESSAGE = "성공";
    }

}
