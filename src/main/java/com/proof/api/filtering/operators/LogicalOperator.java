package com.proof.api.filtering.operators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogicalOperator implements Operator {

    AND(":AND:");

    private final String operator;

    public static LogicalOperator fromString(String text) {
        for (LogicalOperator b : LogicalOperator.values()) {
            if (b.operator.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
