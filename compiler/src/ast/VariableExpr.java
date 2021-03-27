/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Ex: v + 1 => v é o VariableExpr
 */
public class VariableExpr extends Expr {

    private VariableExpr(Variable var) {
        this.var = var;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }
    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    @Override
    public Type getType() {
        return var.getType();
    }

    private Variable var;
}
