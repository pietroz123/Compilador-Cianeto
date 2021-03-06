/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * ReturnStat ::= "return" Expression
 */
public class ReturnStat extends Statement {

    public ReturnStat(Expression expr) {
        this.expr = expr;
    }

    @Override
    public void genC(PW pw) {
        pw.printIdent("return ");
        expr.genC(pw);
        pw.println(";");
    }

    @Override
    public void genJava(PW pw) {
        pw.printIdent("return ");
        expr.genJava(pw);
        pw.println(";");
    }

    private Expression expr;

}
