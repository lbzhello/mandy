package xyz.lius.andy.expression.operator;

import xyz.lius.andy.expression.*;
import xyz.lius.andy.expression.ast.RoundBracketExpression;

public class PointExpression extends AbstractContainer implements Operator {

    public PointExpression() {
        super(2);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        Expression leftValue = get(0).eval(context);
        Expression rightValue = get(1) instanceof RoundBracketExpression ? get(1).eval(context) : get(1);
        if (ExpressionUtils.isComplex(leftValue)) { //e.g. get(0) = { name:"liu" age:22 }  get(0).name
            return ExpressionUtils.asComplex(leftValue).getContext().lookup(rightValue.getName());
        } else if (ExpressionUtils.isJavaObject(leftValue)) {
            return ExpressionFactory.javaMethod(ExpressionUtils.asJavaObject(leftValue).getObject(), rightValue.getName().toString());
        } else if (ExpressionUtils.isSquareBracket(leftValue)) { //e.g. get(0) = [1 2 3 4]  get(0).map
            return ExpressionFactory.arrayMethod(leftValue, rightValue.getName().toString());
        } else if (ExpressionUtils.isString(leftValue)) {
            return ExpressionFactory.javaMethod(leftValue, rightValue.getName().toString());
        } else if (leftValue == ExpressionType.ARRAY) { //e.g. Array.fromString()
            return ExpressionFactory.error(leftValue, "Not Realized");
        } else if (leftValue == ExpressionType.NIL) {
            return ExpressionType.NIL;
        } else {
            return ExpressionFactory.error(leftValue, "Unsupport operation type!");
        }
    }

    @Override
    public String toString() {
        return get(0).toString() + "." + get(1).toString();
    }
}