/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

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
        String methodToCall = this.methodCalled.getId();

        TypeCianetoClass current = this.sourceClass;
        MethodDec theMethod;

        // Sobe na hierarquia de classes até achar a classe com o método
        while ((theMethod = current.searchAllMethods(methodToCall)) == null) {
            current = current.getSuperclass();
        }

        pw.print("_"+theMethod.getCurrentClass().getName()+"_"+this.methodCalled.getId()+"(_"+this.instanceVar.getId()+")");

        // // Antes
        // pw.print("_"+theMethod.getCurrentClass().getName()+"_"+theMethod.getId()+"((_class_"+theMethod.getCurrentClass().getName()+"*) self)");
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
