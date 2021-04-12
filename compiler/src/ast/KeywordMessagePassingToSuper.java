/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * "super" "." IdColon ExpressionList
 */
public class KeywordMessagePassingToSuper extends Expression {

    public KeywordMessagePassingToSuper(TypeCianetoClass currentClass, MethodDec superclassMethod, ExpressionList expressionList) {
        this.currentClass = currentClass;
        this.superclassMethod = superclassMethod;
        this.expressionList = expressionList;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        pw.print("super." + superclassMethod.getId());
        pw.print("(");

        if (expressionList != null) {
            expressionList.genJava(pw);
        }

        pw.print(")");
    }

    @Override
    public Type getType() {
        return superclassMethod.getReturnType();
    }

    private TypeCianetoClass currentClass;
    private MethodDec superclassMethod;
    private ExpressionList expressionList;
}
