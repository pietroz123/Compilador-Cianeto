/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

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
        // Pairs.MethodIndex methodIndex = this.currentClass.searchMethodInVirtualTable(this.methodCalled.getId());
        // pw.print("( ");
        // methodIndex.method.printCSignature(pw);
        // pw.print(" self->vt[" + methodIndex.index + "]");
        // pw.print(" )");

        // pw.print("(self)");

        // ANTES

        String methodToCall = this.methodCalled.getId();

        TypeCianetoClass current = this.currentClass.getSuperclass();
        MethodDec theMethod;

        // Sobe na hierarquia de classes até achar a classe com o método
        while ((theMethod = current.searchAllMethods(methodToCall)) == null) {
            current = current.getSuperclass();
        }

        pw.print("_"+theMethod.getCurrentClass().getName()+"_"+theMethod.getId()+"((_class_"+theMethod.getCurrentClass().getName()+"*) self)");
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
