package xyz.lius.andy.interpreter;

import xyz.lius.andy.core.Definition;
import xyz.lius.andy.expression.Complex;
import xyz.lius.andy.expression.Expression;
import xyz.lius.andy.expression.ExpressionFactory;
import xyz.lius.andy.expression.ast.CurlyBracketExpression;
import xyz.lius.andy.expression.context.ExpressionContext;
import xyz.lius.andy.interpreter.parser.Parser;
import xyz.lius.andy.interpreter.parser.support.DefaultParser;
import xyz.lius.andy.interpreter.tokenizer.Token;
import xyz.lius.andy.interpreter.tokenizer.Tokenizer;
import xyz.lius.andy.interpreter.tokenizer.support.FileTokenizer;
import xyz.lius.andy.io.support.FileCharIterator;

import java.io.File;
import java.io.IOException;

public class FileScriptSession implements ScriptSession {
    private FileCharIterator iterator;
    private Tokenizer<Token> tokenizer;
    private Parser<Expression> parser;

    public FileScriptSession() {
        this.iterator = new FileCharIterator();
        this.tokenizer = new FileTokenizer(iterator);
        this.parser = new DefaultParser(iterator, tokenizer);
    }


    @Override
    public void setSource(Object source) {
        if (source instanceof String) {
            iterator.setFile((String) source);
        } else if (source instanceof File) {
            iterator.setFile((File) source);
        }
    }

    @Override
    public void refresh() {
        iterator.refresh();
    }

    @Override
    public Complex parse() throws IOException {
        CurlyBracketExpression curlyBracketExpression = ExpressionFactory.curlyBracket();
        try {
            while (parser.hasNext()) {
                curlyBracketExpression.add(parser.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ExpressionContext context = new ExpressionContext();
        context.add(Definition.FILE_DIRECTORY, ExpressionFactory.symbol(iterator.getFile().getParentFile().getCanonicalPath()));
        return curlyBracketExpression.eval(context);
    }
}
