package com.proof.api;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    String message;
    String errorCode;
    List<String> errors;
}
