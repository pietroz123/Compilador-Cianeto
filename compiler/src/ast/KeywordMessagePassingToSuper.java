package ast;

/**
 * "super" "." IdColon ExpressionList
 */
public class KeywordMessagePassingToSuper extends Expression {

    private TypeCianetoClass currentClass;
    private MethodDec superclassMethod;
    private ExpressionList expressionList;

    public KeywordMessagePassingToSuper(TypeCianetoClass currentClass, MethodDec superclassMethod, ExpressionList expressionList) {
        this.currentClass = currentClass;
        this.superclassMethod = superclassMethod;
        this.expressionList = expressionList;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

}
