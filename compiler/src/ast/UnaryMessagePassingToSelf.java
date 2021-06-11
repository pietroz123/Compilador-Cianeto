/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * "self" "." Id
 * "self" "." Id "." Id
 */
public class UnaryMessagePassingToSelf extends Expression {

    // "self" "." Id
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, Variable instanceVar) {
        this.messageType = "SELF_INSTANCE";
        this.currentClass = currentClass;
        this.instanceVar = instanceVar;
    }
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, MethodDec methodCalled) {
        this.messageType = "SELF_METHOD";
        this.currentClass = currentClass;
        this.methodCalled = methodCalled;
    }

    // "self" "." Id "." Id
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, Variable instanceVar, MethodDec methodCalled) {
        this.messageType = "SELF_INSTANCE_METHOD";
        this.currentClass = currentClass;
        this.instanceVar = instanceVar;
        this.methodCalled = methodCalled;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        switch (messageType) {
            case "SELF_INSTANCE":
                pw.print("self->_" + currentClass.getName() + "_" + instanceVar.getId());
                break;

            case "SELF_METHOD":
                String methodToCall = this.methodCalled.getId();

                TypeCianetoClass current = this.currentClass;
                MethodDec theMethod;

                // Sobe na hierarquia de classes até achar a classe com o método
                while ((theMethod = current.searchAllMethods(methodToCall)) == null) {
                    current = current.getSuperclass();
                }

                pw.print("_"+theMethod.getCurrentClass().getName()+"_"+theMethod.getId()+"((_class_"+theMethod.getCurrentClass().getName()+"*) self)");

                // Integer idx = methodCalled.getCTableIndex();
                // pw.print("( ("+methodCalled.getReturnType().getCname()+" (*)(_class_"+currentClass.getName()+" *) ) self->vt["+idx+"] )(self)");
                break;

            case "SELF_INSTANCE_METHOD":
                // pw.print("SELF_INSTANCE_METHOD");
                pw.print("_"+this.instanceVar.getType().getName()+"_"+this.methodCalled.getId()+"(self)");
                // pw.print("self->_" + instanceVar.getId() + "." + methodCalled.getId() + "()");
                break;
        }
    }

    @Override
    public void genJava(PW pw) {
        switch (messageType) {
            case "SELF_INSTANCE":
                pw.print("this." + instanceVar.getId());
                break;

            case "SELF_METHOD":
                pw.print("this." + methodCalled.getId() + "()");
                break;

            case "SELF_INSTANCE_METHOD":
                pw.print("this." + instanceVar.getId() + "." + methodCalled.getId() + "()");
                break;
        }
    }

    @Override
    public Type getType() {
        switch (messageType) {
            case "SELF_INSTANCE":
                return instanceVar.getType();

            case "SELF_METHOD":
                return methodCalled.getReturnType();

            case "SELF_INSTANCE_METHOD":
                return methodCalled.getReturnType();

            default:
                return null;
        }
    }

    private String messageType;
    private TypeCianetoClass currentClass; // currentClass existe para caso seja necessário verificação de tipos
    private Variable instanceVar; // lembrete: se precisar da classe é só fazer instanceVar.getType()
    private MethodDec methodCalled;
}
