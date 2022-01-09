package com.proof.persistence.filtering;

import com.proof.api.filtering.expressions.CompareExpression;
import com.proof.api.filtering.expressions.Expression;
import com.proof.api.filtering.expressions.LogicalExpression;
import com.proof.api.filtering.operators.CompareOperator;
import com.proof.api.filtering.operators.LogicalOperator;
import com.proof.exceptions.FilterCriteriaException;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class ExpressionToCriteria<T> {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts the given {@link Expression} to a hibernate {@link CriteriaQuery} that represents the same conditions
     * as the original expression. This CriteriaQuery can be added to other criterias or directly execute it to return
     * the desired results.
     *
     * @param expression {@link Expression} representing some filterings against an Entity
     * @param clazz the {@link javax.persistence.Entity} to filter
     * @return The CriteriaQuery with the same conditions than the original expresssion
     */
    public CriteriaQuery createCriteria(Expression<?, ?> expression, Class<T> clazz) {
        // Load criteria builder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Create criteria query
        CriteriaQuery<T> cr = cb.createQuery(clazz);
        Root<T> root = cr.from(clazz);

        // Construct where clause
        Predicate predicate = getPredicate(expression, cb, root);

        // Compose query
        return cr.select(root).where(predicate);
    }

    private Predicate getPredicate(Expression<?, ?> expression, CriteriaBuilder cb, Root<T> root) {
        if (expression.getOperator() instanceof LogicalOperator) {
            return getPredicateFromLogicalExpression((LogicalExpression<?, ?>) expression, cb, root);
        } else {
            return getPredicateFromCompareExpression((CompareExpression<String, String>) expression, cb, root);
        }
    }

    private Predicate getPredicateFromLogicalExpression(LogicalExpression<?, ?> expression, CriteriaBuilder cb, Root<T> root) {
        LogicalOperator logicalOperator = expression.getOperator();

        if (logicalOperator.equals(LogicalOperator.AND)) {
            return cb.and(
                    getPredicate((Expression<?, ?>) expression.getLeft(), cb, root),
                    getPredicate((Expression<?, ?>) expression.getRight(), cb, root)
            );
        } else {
            throw new FilterCriteriaException("Operation not supported");
        }
    }

    private Predicate getPredicateFromCompareExpression(CompareExpression<String, String> expression, CriteriaBuilder cb, Root<T> root) {
        CompareOperator compareOperator = expression.getOperator();

        switch (compareOperator) {
            case EQUAL:
                return cb.equal(root.get(expression.getLeft()), expression.getRight());

            case DISTINCT:
                return cb.notEqual(root.get(expression.getLeft()), expression.getRight());

            case LIKE:
                return cb.like(root.get(expression.getLeft()), expression.getRight());

            default:
                throw new FilterCriteriaException("Operation not supported");
        }
    }
}
