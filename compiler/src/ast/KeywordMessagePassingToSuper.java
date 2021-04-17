/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

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

        Iterator<Expression> it = expressionList.getExprList().iterator();
        while (it.hasNext()) {
            Expression expr = it.next();
            expr.genJava(pw);

            if (it.hasNext()) {
                pw.print(", ");
            }
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
