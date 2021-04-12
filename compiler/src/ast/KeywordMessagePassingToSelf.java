/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * "self" "." IdColon ExpressionList
 * "self" "." Id "." IdColon ExpressionList
 */
public class KeywordMessagePassingToSelf extends Expression {

    // "self" "." IdColon ExpressionList
    public KeywordMessagePassingToSelf(TypeCianetoClass currentClass, MethodDec classMethod, ExpressionList exprList) {
        this.messageType = "SELF_METHOD";
        this.currentClass = currentClass;
        this.classMethod = classMethod;
        this.exprList = exprList;
    }

    // "self" "." Id "." IdColon ExpressionList
    public KeywordMessagePassingToSelf(TypeCianetoClass currentClass, Variable instanceVar, MethodDec classMethod, ExpressionList exprList) {
        this.messageType = "SELF_INSTANCE_METHOD";
        this.currentClass = currentClass;
        this.instanceVar = instanceVar;
        this.classMethod = classMethod;
        this.exprList = exprList;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        switch (messageType) {
            case "SELF_METHOD":
                pw.print("this." + classMethod.getId());
                pw.print("(");

                if (exprList != null) {
                    exprList.genJava(pw);
                }

                pw.print(")");
                break;

            case "SELF_INSTANCE_METHOD":
                pw.print("this." + instanceVar.getId() + "." + classMethod.getId());
                pw.print("(");

                if (exprList != null) {
                    exprList.genJava(pw);
                }

                pw.print(")");
                break;
        }
    }

    @Override
    public Type getType() {
        switch (messageType) {
            case "SELF_METHOD":
                return classMethod.getReturnType();
            case "SELF_INSTANCE_METHOD":
                return classMethod.getReturnType();
            default:
                return null;
        }
    }


    private String messageType;
    private TypeCianetoClass currentClass; // currentClass existe para caso seja necessário verificação de tipos
    private Variable instanceVar; // lembrete: se precisar da classe é só fazer instanceVar.getType()
    private MethodDec classMethod;
    private ExpressionList exprList;
}
