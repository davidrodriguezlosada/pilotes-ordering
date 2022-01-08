package com.proof.api;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Class that represents every error our API will return. The {@link CustomExceptionHandler} will handle all these
 * exceptions to return the correct response.
 */
@Data
@Builder
public class ErrorResponse {
    String message;
    String errorCode;
    List<String> errors;
}
