/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * ObjectCreation ::= Id "." "new"
 */
public class ObjectCreation extends PrimaryExpr {

    public ObjectCreation(TypeCianetoClass cianetoClass) {
        this.cianetoClass = cianetoClass;
    }

    @Override
    public Type getType() {
        return cianetoClass;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        pw.print("new_" + cianetoClass.getName()  + "()");
    }
    @Override
    public void genJava(PW pw) {
        pw.print("new " + cianetoClass.getName() + "()");
    }

    private TypeCianetoClass cianetoClass;
}
