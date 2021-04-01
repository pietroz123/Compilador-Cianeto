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
        // TODO Auto-generated method stub

    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub

    }

    private Expression expr;

}
