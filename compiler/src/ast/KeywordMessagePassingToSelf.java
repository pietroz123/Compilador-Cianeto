package ast;

public class KeywordMessagePassingToSelf extends Expression {

    private TypeCianetoClass currentClass;
    private MethodDec classMethod;
    private ExpressionList exprList;
    private TypeCianetoClass cianetoClass;

    public KeywordMessagePassingToSelf(TypeCianetoClass currentClass, MethodDec classMethod, ExpressionList exprList) {
        this.currentClass = currentClass;
        this.classMethod = classMethod;
        this.exprList = exprList;
    }

    public KeywordMessagePassingToSelf(TypeCianetoClass currentClass, TypeCianetoClass cianetoClass,
            MethodDec classMethod, ExpressionList exprList) {
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
