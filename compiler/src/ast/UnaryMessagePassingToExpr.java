package ast;

/**
 * Id "." Id
 */
public class UnaryMessagePassingToExpr extends Expression {

    private TypeCianetoClass cianetoClass;
    private Variable var;

    public UnaryMessagePassingToExpr(TypeCianetoClass cianetoClass, Variable var) {
        this.cianetoClass = cianetoClass;
        this.var = var;
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