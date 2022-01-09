package com.proof.api.filtering.parser;

import com.proof.api.filtering.expressions.CompareExpression;
import com.proof.api.filtering.expressions.Expression;
import com.proof.api.filtering.expressions.LogicalExpression;
import com.proof.api.filtering.operators.CompareOperator;
import com.proof.api.filtering.operators.LogicalOperator;
import com.proof.exceptions.FilterParseException;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Class that parses filter strings into expressions composed by {@link LogicalExpression} and {@link CompareExpression}
 * <br><br>
 * Filter Strings can have the shape of: firstName=John:AND:lastName:LIKE:Dal
 * <br><br>
 * For more information about supported expressions check {@link LogicalOperator} and {@link CompareOperator}.
 */
@Component
public class ApiFilterParser {

    CompareOperatorFinder compareOperatorFinder;
    LogicalOperatorFinder logicalOperatorFinder;
    FieldValueConverter fieldValueConverter;

    public ApiFilterParser(CompareOperatorFinder compareOperatorFinder,
                           LogicalOperatorFinder logicalOperatorFinder,
                           FieldValueConverter fieldValueConverter) {
        this.compareOperatorFinder = compareOperatorFinder;
        this.logicalOperatorFinder = logicalOperatorFinder;
        this.fieldValueConverter = fieldValueConverter;
    }

    /**
     * Converts a filter string into an {@link Expression}
     *
     * @param filter
     * @param clazz The class to check the field values. It won't be allowed to parse expressions using fields that
     *              do not exist in this class.
     * @return An expression representing the given string filter.
     */
    public Expression<Object, Object> parseExpression(String filter, Class<?> clazz) {
        Expression<Object, Object> expression;

        Optional<String> logicalOperator = logicalOperatorFinder.getFirstLogicalOperator(filter);

        if (logicalOperator.isPresent()) {
            expression = new LogicalExpression<>(
                    parseExpression(filter.substring(0, filter.indexOf(logicalOperator.get())), clazz),
                    parseExpression(filter.substring(filter.indexOf(logicalOperator.get()) + logicalOperator.get().length()), clazz),
                    LogicalOperator.fromString(logicalOperator.get())
            );
        } else {
            String compareOperator = compareOperatorFinder.getFirstCompareOperator(filter)
                    .orElseThrow(() -> new FilterParseException("No compare operator found"));

            String field = filter.substring(0, filter.indexOf(compareOperator));
            String value = filter.substring(filter.indexOf(compareOperator) + compareOperator.length());
            Object valueWithType = fieldValueConverter.getValueWithRightType(field, value, clazz);
            expression = new CompareExpression<>(
                    field,
                    valueWithType,
                    CompareOperator.fromString(compareOperator)
            );
        }

        return expression;
    }
}
