package com.sparta.project_todo.controller;

import com.sparta.project_todo.dto.ErrorResponse;
import com.sparta.project_todo.exception.CommonErrorCode;
import com.sparta.project_todo.exception.ErrorCode;
import com.sparta.project_todo.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.sparta.project_todo.exception.CommonErrorCode.INVALID_PARAMETER;

@Slf4j
//@RestControllerAdvice
public class ToDoExceptionHandler //extends ResponseEntityExceptionHandler
{

    //TODO ToDo 컨트롤러에서 try catch 구문을 제외하고 custom 예외 처리를 시도 해봤으나 실패했습니다.
    // 정확하게는 service 쪽에서 예외를 던지면 여기서 잡기전에 이미 컴파일 과정에서
    // ToDoController에서 오류가 납니다. 그부분을 해결 못했습니다.
    // 예외처리를 처음 만들어봤는데 Board프로젝트 리뷰때 주신 커스텀 예외처리에 관련된 사이트로는 자세하게 이해가 안될뿐더러
    // 그로 인해 구현이 불가능 합니다. 구체적으로 설명을 적어주시면 감사하겠습니다.
    // 전반적으로 내용을 이해하기에 무리가 있지 않았나 싶습니다. 아래 사이트는 참고한 사이트 입니다.
    // https://mangkyu.tistory.com/204 , https://mangkyu.tistory.com/205

//    @ExceptionHandler(RestApiException.class)
//    public ResponseEntity<Object> handleCustomException(RestApiException e) {
//        ErrorCode errorCode = e.getErrorCode();
//        return handleExceptionInternal(errorCode);
//    }
//
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
//        log.warn("handleIllegalArgument", e);
//        ErrorCode errorCode = INVALID_PARAMETER;
//        return handleExceptionInternal(errorCode, e.getMessage());
//    }
//
//
//
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Object> handleAllException(Exception ex) {
//        log.warn("handleAllException", ex);
//        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
//        return handleExceptionInternal(errorCode);
//    }
//
//    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
//        return ResponseEntity.status(errorCode.getHttpStatus())
//                .body(makeErrorResponse(errorCode));
//    }
//
//    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
//        return ErrorResponse.builder()
//                .code(errorCode.name())
//                .message(errorCode.getMessage())
//                .build();
//    }
//
//    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
//        return ResponseEntity.status(errorCode.getHttpStatus())
//                .body(makeErrorResponse(errorCode, message));
//    }
//
//    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
//        return ErrorResponse.builder()
//                .code(errorCode.name())
//                .message(message)
//                .build();
//    }


//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(
//            final MethodArgumentNotValidException e,
//            final HttpHeaders headers,
//            final HttpStatus status,
//            final WebRequest request) {
//        log.warn("handleIllegalArgument", e);
//        final ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
//        return handleExceptionInternal(e, errorCode);
//    }
//
//
//    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
//        return ResponseEntity.status(errorCode.getHttpStatus())
//                .body(makeErrorResponse(e, errorCode));
//    }
//
//    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
//        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(ErrorResponse.ValidationError::of)
//                .collect(Collectors.toList());
//
//        return ErrorResponse.builder()
//                .code(errorCode.name())
//                .message(errorCode.getMessage())
//                .errors(validationErrorList)
//                .build();
//    }
}

