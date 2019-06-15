package xyz.lius.andy.expression;

import java.util.Arrays;

public abstract class AbstractContainer implements Container<Expression> {
    protected static final Expression[] EMPTY_ELEMENT_DATA = {};

    private static final int DEFAULT_INITIAL_CAPACITY = 8;

    //元素总数
    private int count;

    //剩余空间
    private int free;

    private Expression[] elementData;

    public AbstractContainer() {
        elementData = EMPTY_ELEMENT_DATA;
    }

    public AbstractContainer(int initialCapacity) {
        free = initialCapacity;
        elementData = new Expression[free];
    }

    public AbstractContainer(Expression[] elementData) {
        this.elementData = elementData;
        count = elementData.length;
    }

    @Override
    public void add(Expression element) {
        if (free == 0) {
            Expression[] oldElementData = elementData;
            int len = count == 0 ? DEFAULT_INITIAL_CAPACITY : (count << 1);
            elementData = new Expression[len];
            System.arraycopy(oldElementData, 0, elementData, 0, count);
            free = len - count;
        }
        elementData[count] = element;
        count++; free--;
    }

    @Override
    public Expression get(int i) {
        return i < count ? elementData[i] : ExpressionType.NIL;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public Expression[] toArray() {
        return Arrays.copyOf(elementData, count);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (size() != 0) {
            for (int i = 0; i < count; i++) {
                sb.append(elementData[i] + " ");
            }
            sb.replace(sb.length()-1, sb.length(), "");
        }
        return sb.toString();
    }

    public void addAll(Expression[] a) {
        int len = count + a.length;
        if (a.length > free) {
            elementData = Arrays.copyOf(elementData, len);
            System.arraycopy(a, 0, elementData, count, a.length);
            count = len; free = 0;
        } else {
            System.arraycopy(a, 0, elementData, count, a.length);
            count = len; free -= a.length;
        }
    }

    /**
     * lisp表达式
     * @param name
     * @Param oprands
     * @return
     */
    protected String show(String name, String oprands) {
        return "(" + name + " " + oprands + ")";
    }

}
