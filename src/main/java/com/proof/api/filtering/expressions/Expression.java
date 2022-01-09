package com.proof.api.filtering.expressions;

import com.proof.api.filtering.operators.Operator;

public interface Expression<K,V> {
    K getLeft();
    V getRight();
    Operator getOperator();
}
