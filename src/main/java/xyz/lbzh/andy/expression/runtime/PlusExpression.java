package xyz.lbzh.andy.expression.runtime;

import xyz.lbzh.andy.expression.*;
import xyz.lbzh.andy.expression.ast.ErrorExpression;

import java.math.BigDecimal;
import java.util.List;

@RoundBracketed
public class PlusExpression extends NativeExpression {

    @Override
    public Expression parameters(List<Expression> list) {
        return new PlusExpression().list(list);
    }

    @Override
    public Expression eval(Context<Name, Expression> context) {
        BigDecimal accu = BigDecimal.ZERO;
        for (Expression expression : list()) {
            Expression factor = expression.eval(context);
            if (!(ExpressionUtils.isNumber(factor))) {
                return new ErrorExpression(factor, "Unsupport Operand Type!");
            }
            accu = accu.add(((BigDecimal) factor));
        }
        return ExpressionFactory.number(accu.doubleValue());
    }

}
