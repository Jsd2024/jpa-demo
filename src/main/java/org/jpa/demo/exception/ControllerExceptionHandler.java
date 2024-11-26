package org.jpa.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

//import jakarta.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.Date;

/**
 * The type Controller exception handler.
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * Handled exception response entity.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(HandledException.class)
    public ResponseEntity<ErrorMessage> handledException(HandledException e) {
        ErrorMessage message = new ErrorMessage(e.getCode(), e.getCode().value(), new Date(), e.getMessage(), "");
        return new ResponseEntity<ErrorMessage>(message, e.getCode());
    }

    /**
     * Generic exception response entity.
     *
     * @param e the exception
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> genericException(Exception e) {
        log.error("ControllerExceptionHandler::genericException Generic Exception Thrown - " + e.getMessage(), e);

        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
            HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), e.getMessage(), "");

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle multipart exception response entity.
     *
     * @param e the exception
     * @return the response entity
     * @throws IOException the io exception
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorMessage> handleMultipartException(MultipartException e) throws IOException {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
            new Date(), e.getMessage(), "Please select a XML file");
        log.error("ControllerExceptionHandler::handleMultipartException Generic Exception Thrown - " + e.getMessage(), e);

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle constraint violation exception response entity.
     *
     * @param e the e
     * @return the response entity
     * @throws IOException the io exception
     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) throws IOException {
//        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
//            new Date(), e.getMessage(), "Please select a XML file");
//
//        log.error("ControllerExceptionHandler::handleConstraintViolationException Generic Exception Thrown - " + e.getMessage(), e);
//
//        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
//    }
}
