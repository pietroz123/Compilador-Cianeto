/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * ParamDec ::= Type Id
 */
public class ParamDec {

    public ParamDec(Variable var) {
        this.var = var;
    }

    public void genJava(PW pw) {
        pw.print(var.getType().getJavaName() + " " + var.getId());
    }

    private Variable var;
}
