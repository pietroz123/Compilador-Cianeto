/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * "self"
 */
public class SelfExpression extends Expression {

    public SelfExpression(TypeCianetoClass currentClass) {
        this.currentClass = currentClass;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        pw.print("this");
    }

    @Override
    public Type getType() {
        return currentClass;
    }

    private TypeCianetoClass currentClass;
}
