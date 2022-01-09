package com.proof.api.filtering.parser;

import com.proof.api.filtering.operators.CompareOperator;
import com.proof.exceptions.FilterParseException;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * Helper class to find compare operators inside strings
 */
@Component
public class CompareOperatorFinder {

    public Optional<String> getFirstCompareOperator(String filter) {
        Optional<String> operator = Optional.empty();
        int position = getFirstCompareOperatorPosition(filter);
        if (position != -1) {
            operator = Optional.of(
                    getOperatorInPosition(filter, position));
        }

        return operator;
    }

    private int getFirstCompareOperatorPosition(String filter) {
        return Arrays.stream(CompareOperator.values())
                .mapToInt(operator -> filter.indexOf(operator.getOperator()))
                .filter(position -> position > 0)
                .min()
                .orElse(-1);
    }

    private String getOperatorInPosition(String filter, int position) {
        return Arrays.stream(CompareOperator.values())
                .filter(compareOperator -> filter.indexOf(compareOperator.getOperator()) == position)
                .findFirst()
                .orElseThrow(() -> new FilterParseException("No compare operator found"))
                .getOperator();
    }
}
