package com.blubank.doctorappointment.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, List<String>>> handleBusinessException(BusinessException ex) {
        String error = ex.getAdditionalInfo().isEmpty()
                ? ex.getMessage()
                : ex.getMessage() + ": (" + ex.getAdditionalInfo() + ")";
        HttpStatus status;
        switch ((ErrorCode) ex.getErrorCode()) {
            case NOT_FOUND_EXCEPTION:
                status = HttpStatus.NOT_FOUND;
                break;
            case BAD_PARAM_EXCEPTION:
            case LOCKED_EXCEPTION:
                status = HttpStatus.NOT_ACCEPTABLE;
                break;
            case GENERAL_EXCEPTION:
            case BAD_REQUEST_EXCEPTION:
            case TRANSACTION_EXCEPTION:
            case INTERNAL_SERVER_EXCEPTION:
            default:
                status = HttpStatus.BAD_REQUEST;
                break;
        }
        List<String> errors = Collections.singletonList(error);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
