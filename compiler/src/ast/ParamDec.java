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

    public Variable getVar() {
        return var;
    }

    public void genC(PW pw) {
        pw.print(var.getType().getCname() + " ");

        // necessário ponteiro em variáveis de classes
        if (var.getType() instanceof TypeCianetoClass) {
            pw.print("*");
        }

        pw.print("_" + var.getId());
    }
    public void genJava(PW pw) {
        pw.print(var.getType().getJavaName() + " " + var.getId());
    }

    private Variable var;
}
