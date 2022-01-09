package com.proof.api.filtering.operators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompareOperator implements Operator {

    EQUAL("="),
    DISTINCT("<>"),
    LIKE(":LIKE:");

    private final String operator;

    public static CompareOperator fromString(String text) {
        for (CompareOperator b : CompareOperator.values()) {
            if (b.operator.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
