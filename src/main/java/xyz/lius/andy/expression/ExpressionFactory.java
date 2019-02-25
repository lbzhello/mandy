package xyz.lius.andy.expression;

import xyz.lius.andy.core.ApplicationFactory;
import xyz.lius.andy.expression.ast.*;
import xyz.lius.andy.expression.runtime.*;
import xyz.lius.andy.expression.runtime.ColonExpression;
import xyz.lius.andy.expression.template.XmlTagExpression;
import xyz.lius.andy.expression.template.LineExpression;
import xyz.lius.andy.expression.template.TemplateExpression;
import xyz.lius.andy.expression.template.XmlExpression;

public class ExpressionFactory {
    public static BracketExpression bracket(Expression... expressions) {
        return new BracketExpression(expressions);
    }

    public static BracketExpression roundBracket(Expression... expressions) {
        return new RoundBracketExpression(expressions);
    }

    public static SquareBracketExpression squareBracket(Expression...expressions) {
        return new SquareBracketExpression(expressions);
    }

    public static CurlyBracketExpression curlyBracket() {
        return new CurlyBracketExpression();
    }

    public static TokenExpression token(Object token) {
        return new TokenExpression(token);
    }

    public static TokenExpression token(Object token, int lineNumber) {
        return new TokenExpression(token, lineNumber);
    }

    public static SymbolExpression symbol(String value) {
        return new SymbolExpression(value);
    }

    public static SymbolExpression symbol(String value, int lineNumber) {
        return new SymbolExpression(value, lineNumber);
    }

    public static StringExpression string(String value) {
        return new StringExpression(value);
    }

    public static StringExpression string(String value, int lineNumber) {
        return new StringExpression(value, lineNumber);
    }

    public static NumberExpression number(String val) {
        return new NumberExpression(val);
    }

    public static NumberExpression number(String val, int lineNumber) {
        return new NumberExpression(val, lineNumber);
    }

    public static NumberExpression number(double val) {
        return new NumberExpression(val);
    }

    public static NumberExpression number(double val, int lineNumber) {
        return new NumberExpression(val, lineNumber);
    }

    public static DelimiterExpression delimiter(String value) {
        return new DelimiterExpression(value);
    }

    public static DelimiterExpression delimiter(String value, int lineNumber) {
        return new DelimiterExpression(value, lineNumber);
    }

    public static BracketExpression lambda(BracketExpression bracket, CurlyBracketExpression curlyBracket) {
        return new LambdaExpression(bracket, curlyBracket);
    }

    public static BracketExpression define(Expression key, CurlyBracketExpression value) {
        //a{...} => a(){...}
        if (key.getName() == Name.NIL && key instanceof BracketExpression) { //it's a lambda
            return lambda((BracketExpression) key, value);
        }
        return new DefineExpression(key instanceof Name ? ExpressionFactory.roundBracket(key) : (BracketExpression)key, value);
    }

    public static BracketExpression colon(Expression key, Expression value) {
        return new ColonExpression(key, value);
    }

    public static BracketExpression comma(Expression... expressions) {
        return new CommaExpression(expressions);
    }

    public static BracketExpression point(Expression left, Expression right) {
        return new PointExpression(left, right);
    }

    public static ComplexExpression complex(Context<Name, Expression> context) {
        return new ComplexExpression(context);
    }

    public static ObjectExpression object(Object o) {
        return new ObjectExpression(o);
    }

    public static MethodExpression method(Object methodObject, String methodName) {
        return new MethodExpression(methodObject, methodName);
    }

    public static ArrayMethodExpression arrayMethod(Expression methodObject, String methodName) {
        return new ArrayMethodExpression(methodObject, methodName);
    }

    public static XmlExpression xml() {
        return new XmlExpression();
    }

    public static XmlTagExpression xmlTag() {
        return new XmlTagExpression();
    }

    public static LineExpression line() {
        return new LineExpression();
    }

    public static TemplateExpression template() {
        return new TemplateExpression();
    }

    public static ErrorExpression error(String message) {
        return new ErrorExpression(message);
    }

    public static ErrorExpression error(Expression expression, String message) {
        return new ErrorExpression(expression, message);
    }

    public static Expression getExpression(String name) {
        return ApplicationFactory.get(name, Expression.class);
    }

}