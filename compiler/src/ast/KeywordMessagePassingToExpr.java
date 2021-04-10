package ast;

/**
 * Id "." IdColon ExpressionList
 */
public class KeywordMessagePassingToExpr extends Expression {

    private TypeCianetoClass cianetoClass;
    private MethodDec classMethod;
    private ExpressionList exprList;

    public KeywordMessagePassingToExpr(TypeCianetoClass cianetoClass, MethodDec classMethod, ExpressionList exprList) {
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
        return classMethod.getReturnType();
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

}
