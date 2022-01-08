package com.proof.api;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that all controllers should have insted of the standard @RestController annotation. We will group
 * general configurations here instead of repeating them in all the controllers.
 */
@Retention(RetentionPolicy.RUNTIME)
@RestController
@RequestMapping("/api")
@Component
public @interface CustomRestController {
}
