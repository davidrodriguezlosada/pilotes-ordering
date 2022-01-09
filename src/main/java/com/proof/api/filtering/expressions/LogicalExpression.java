package com.proof.api.filtering.expressions;

import com.proof.api.filtering.operators.LogicalOperator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogicalExpression<K, V> implements Expression<K, V> {
    private final K left;
    private final V right;
    private final LogicalOperator operator;
}
