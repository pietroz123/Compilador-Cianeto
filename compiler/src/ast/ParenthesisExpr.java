/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class ParenthesisExpr extends Expression {

    public ParenthesisExpr( Expression expr ) {
        this.expr = expr;
    }

    @Override
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print("(");
        expr.genC(pw, false);
        pw.printIdent(")");
    }

    @Override
    public void genJava(PW pw) {
        pw.print("(");
        expr.genJava(pw);
        pw.print(")");
    }

    @Override
    public Type getType() {
        return expr.getType();
    }

    private Expression expr;
}