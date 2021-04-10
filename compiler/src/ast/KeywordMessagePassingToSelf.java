package ast;

/**
 * "self" "." IdColon ExpressionList
 * "self" "." Id "." IdColon ExpressionList
 */
public class KeywordMessagePassingToSelf extends Expression {

    private TypeCianetoClass currentClass;
    private MethodDec classMethod;
    private ExpressionList exprList;
    private TypeCianetoClass cianetoClass;

    // "self" "." IdColon ExpressionList
    public KeywordMessagePassingToSelf(TypeCianetoClass currentClass, MethodDec classMethod, ExpressionList exprList) {
        this.currentClass = currentClass;
        this.classMethod = classMethod;
        this.exprList = exprList;
    }

    // "self" "." Id "." IdColon ExpressionList
    public KeywordMessagePassingToSelf(TypeCianetoClass currentClass, TypeCianetoClass cianetoClass, MethodDec classMethod, ExpressionList exprList) {
        this.currentClass = currentClass;
        this.cianetoClass = cianetoClass;
        this.classMethod = classMethod;
        this.exprList = exprList;
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
