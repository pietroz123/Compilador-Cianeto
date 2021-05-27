/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Ex: v + 1 => v é o VariableExpr
 */
public class VariableExpr extends Expression {

    public VariableExpr(Variable var) {
        this.var = var;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        pw.print("_" + var.getId());
    }

    @Override
    public void genJava(PW pw) {
        pw.print(var.getId());
    }

    @Override
    public Type getType() {
        return var.getType();
    }

    private Variable var;
}
