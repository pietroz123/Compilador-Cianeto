/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Id "." IdColon ExpressionList
 */
public class KeywordMessagePassingToExpr extends Expression {

    public KeywordMessagePassingToExpr(Variable instanceVar, TypeCianetoClass sourceClass, MethodDec methodCalled, ExpressionList exprList) {
        this.instanceVar = instanceVar;
        this.sourceClass = sourceClass;
        this.methodCalled = methodCalled;
        this.exprList = exprList;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public Type getType() {
        return methodCalled.getReturnType();
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    private Variable instanceVar;
    private TypeCianetoClass sourceClass;
    private MethodDec methodCalled;
    private ExpressionList exprList;
}
