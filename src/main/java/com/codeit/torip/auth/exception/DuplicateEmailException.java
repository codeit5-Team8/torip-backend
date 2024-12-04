package com.codeit.torip.auth.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {
        super("이메일이 중복되었습니다.");
    }

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
