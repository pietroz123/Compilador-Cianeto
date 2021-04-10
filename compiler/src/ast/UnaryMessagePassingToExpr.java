package ast;

/**
 * Id "." Id
 */
public class UnaryMessagePassingToExpr extends Expression {

    private TypeCianetoClass cianetoClass;
    private MethodDec var;

    public UnaryMessagePassingToExpr(TypeCianetoClass cianetoClass, MethodDec var) {
        this.cianetoClass = cianetoClass;
        this.var = var;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub

    }

    @Override
    public Type getType() {
        return var.getReturnType();
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub

    }

}
