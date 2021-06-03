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
        // pw.print("UnaryMessagePassingToSuper");
        // pw.print("_SUPERCLASSNAME_SUPERMETHOD((_class_SUPER*) CURRENTCLASS)");
        pw.print("_"+this.currentClass.getSuperclass().getName()+"_"+this.methodCalled.getId()+"((_class_"+this.currentClass.getSuperclass().getName()+"*) self)");
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
