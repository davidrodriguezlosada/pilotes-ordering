package com.proof.api.filtering.expressions;

import com.proof.api.filtering.operators.CompareOperator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompareExpression<K, V> implements Expression<K, V> {

    private final K left;
    private final V right;
    private final CompareOperator operator;
}
