/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class BreakStat extends Statement {

    @Override
    public void genC(PW pw) {
        pw.printIdent("break;");
    }

    @Override
    public void genJava(PW pw) {
        pw.printIdent("break;");
    }

}
