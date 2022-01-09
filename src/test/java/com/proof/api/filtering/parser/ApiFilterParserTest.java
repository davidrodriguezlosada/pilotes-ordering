package com.proof.api.filtering.parser;

import com.proof.api.filtering.expressions.CompareExpression;
import com.proof.api.filtering.expressions.Expression;
import com.proof.api.filtering.operators.CompareOperator;
import com.proof.api.filtering.operators.LogicalOperator;
import com.proof.exceptions.FilterParseException;
import com.proof.persistence.entities.Client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiFilterParserTest {

    ApiFilterParser apiFilterParser;

    ApiFilterParserTest() {
        this.apiFilterParser = new ApiFilterParser(
                new CompareOperatorFinder(),
                new LogicalOperatorFinder(),
                new FieldValueConverter()
        );
    }

    @Test
    void parseSimpleStringExpression() {
        // given
        String filter = "firstName=John";

        // when
        Expression<?, ?> expression = apiFilterParser.parseExpression(filter, Client.class);

        // then
        assertAll(
                () -> assertNotNull(expression),
                () -> assertEquals("firstName", expression.getLeft()),
                () -> assertEquals("John", expression.getRight()),
                () -> assertEquals(CompareOperator.EQUAL, expression.getOperator())
        );
    }

    @Test
    void parseSimpleLongExpression() {
        // given
        String filter = "id=15";

        // when
        Expression<?, ?> expression = apiFilterParser.parseExpression(filter, Client.class);

        // then
        assertAll(
                () -> assertNotNull(expression),
                () -> assertEquals("id", expression.getLeft()),
                () -> assertEquals(15L, expression.getRight()),
                () -> assertEquals(CompareOperator.EQUAL, expression.getOperator())
        );
    }

    @Test
    void parseComplexExpression() {
        // given
        String filter = "firstName=John:AND:lastName:LIKE:a";

        // when
        Expression<?, ?> expression = apiFilterParser.parseExpression(filter, Client.class);

        // then
        assertAll(
                () -> assertNotNull(expression),
                () -> assertInstanceOf(CompareExpression.class, expression.getLeft()),
                () -> assertInstanceOf(CompareExpression.class, expression.getRight()),
                () -> assertEquals(LogicalOperator.AND, expression.getOperator()),
                () -> assertEquals("firstName", ((Expression<?, ?>) expression.getLeft()).getLeft()),
                () -> assertEquals("John", ((Expression<?, ?>) expression.getLeft()).getRight()),
                () -> assertEquals(CompareOperator.EQUAL, ((Expression<?, ?>) expression.getLeft()).getOperator()),
                () -> assertEquals("lastName", ((Expression<?, ?>) expression.getRight()).getLeft()),
                () -> assertEquals("a", ((Expression<?, ?>) expression.getRight()).getRight()),
                () -> assertEquals(CompareOperator.LIKE, ((Expression<?, ?>) expression.getRight()).getOperator())
        );
    }

    @Test
    void parseFailsWithUnknownFields() {
        // given
        String filter = "unknownField=John";

        // then
        assertThrows(FilterParseException.class, () -> apiFilterParser.parseExpression(filter, Client.class));
    }
}