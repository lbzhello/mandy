package xyz.lius.andy.expression;

import xyz.lius.andy.expression.context.AbstractContext;

import java.util.List;

/**
 * 栈帧,方法运行时数据结构
 */
public class StackFrame extends AbstractContext<Name, Expression> implements Expression {
    //方法字节吗
    private List<Expression> codes;

    /**
     * @param complex 相当于类文件
     * @param argsContext 实参上下文
     * @param args 实参
     */
    public StackFrame(Complex complex, Context<Name, Expression> argsContext, List<Expression> args) {
        //动态链接, 对运行时常量池的引用
        super(complex.getContext());
        this.codes = complex.getCodes();
        //参数放入局部变量表
        for (int i = 0; i < args.size(); i++) {
            add(ExpressionFactory.symbol("$" + i), args.get(i).eval(argsContext));
        }
    }

    /**
     * 无参栈帧
     * @param complex
     */
    public StackFrame(Complex complex) {
        super(complex.getContext());
        this.codes = complex.getCodes();
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
