/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

/**
 * Id "." Id
 */
public class UnaryMessagePassingToExpr extends Expression {

    public UnaryMessagePassingToExpr(Variable instanceVar, TypeCianetoClass sourceClass, MethodDec methodCalled) {
        this.instanceVar = instanceVar;
        this.sourceClass = sourceClass;
        this.methodCalled = methodCalled;
    }

    @Override
    public Type getType() {
        return methodCalled.getReturnType();
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        Pairs.MethodIndex methodIndex = this.sourceClass.searchMethodInVirtualTable(this.methodCalled.getId());
        pw.print("( ");
        methodIndex.method.printCSignature(pw);
        pw.print(" _"+this.instanceVar.getId()+"->vt[" + methodIndex.index + "]");
        pw.print(" )");

        pw.print("(_"+this.instanceVar.getId()+")");
    }

    @Override
    public void genJava(PW pw) {
        pw.print(instanceVar.getId());
        pw.print(".");
        pw.print(methodCalled.getId() + "()");
    }

    private Variable instanceVar;
    private TypeCianetoClass sourceClass;
    private MethodDec methodCalled;
}
