package com.proof.api.filtering.parser;

import com.proof.api.filtering.operators.LogicalOperator;
import com.proof.exceptions.FilterParseException;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * Helper class to find logical operators inside strings
 */
@Component
public class LogicalOperatorFinder {

    public Optional<String> getFirstLogicalOperator(String filter) {
        Optional<String> operator = Optional.empty();
        int position = getFirstLogicalOperatorPosition(filter);
        if (position != -1) {
            operator = Optional.of(
                    getOperatorInPosition(filter, position));
        }

        return operator;
    }

    private int getFirstLogicalOperatorPosition(String filter) {
        return Arrays.stream(LogicalOperator.values())
                .mapToInt(operator -> filter.indexOf(operator.getOperator()))
                .filter(position -> position > 0)
                .min()
                .orElse(-1);
    }

    private String getOperatorInPosition(String filter, int position) {
        return Arrays.stream(LogicalOperator.values())
                .filter(logicalOperator -> filter.indexOf(logicalOperator.getOperator()) == position)
                .findFirst()
                .orElseThrow(() -> new FilterParseException("No logical operator found"))
                .getOperator();
    }
}
