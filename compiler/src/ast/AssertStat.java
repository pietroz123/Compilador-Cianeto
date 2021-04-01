
/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * AssertStat ::= "assert" Expression "," StringValue
 */
public class AssertStat extends Statement {

    public AssertStat(Expression expr, String message) {
        this.expr = expr;
        this.message = message;
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
    private String message;
}
