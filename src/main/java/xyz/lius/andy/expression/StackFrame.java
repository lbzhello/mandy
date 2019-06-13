package xyz.lius.andy.expression;

import xyz.lius.andy.expression.context.AbstractContext;

import java.util.List;

public class StackFrame extends AbstractContext<Name, Expression> implements Expression {

    private List<Expression> codes;

    public StackFrame(Complex complex, Context<Name, Expression> argsContext, List<Expression> args) {
        super(complex.getContext());
        this.codes = complex.getCodes();
        List<Expression> params = complex.getParameters();
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                add(complex.getParameters().get(i).getName(), args.get(i).eval(argsContext));
            }
        }
    }

    @Override
    public Expression eval(Context<Name, Expression> self) {
        Expression rstValue = ExpressionType.NIL;
        for (Expression expression : this.codes) {
            rstValue = expression.eval(this);
            if (ExpressionUtils.isReturn(rstValue)) {
                return ExpressionUtils.asReturn(rstValue).getValue();
            }
            if (ExpressionUtils.hasError(rstValue)) {
                return rstValue.eval(this);
            }
        }
        return rstValue;
    }

    @Override
    public Expression lookup(Name key) {
        Expression o = super.lookup(key);
        return o == null ? ExpressionType.NIL : o;
    }

}