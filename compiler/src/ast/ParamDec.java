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
        if (var.getType() instanceof TypeCianetoClass) {
            pw.print("_class_" + var.getType().getCname() + " _" + var.getId());
        } else {
            pw.print(var.getType().getCname() + " _" + var.getId());
        }
    }
    public void genJava(PW pw) {
        pw.print(var.getType().getJavaName() + " " + var.getId());
    }

    private Variable var;
}
