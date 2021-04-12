/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * "super" "." Id
 */
public class UnaryMessagePassingToSuper extends Expression {

    public UnaryMessagePassingToSuper(TypeCianetoClass currentClass, MethodDec methodCalled) {
        this.currentClass = currentClass;
        this.methodCalled = methodCalled;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        pw.print("super." + methodCalled.getId() + "()");
    }

    @Override
    public Type getType() {
        return methodCalled.getReturnType();
    }

    private TypeCianetoClass currentClass;
    private MethodDec methodCalled;
}
