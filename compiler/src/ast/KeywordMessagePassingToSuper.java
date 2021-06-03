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
        pw.print("_"+this.currentClass.getSuperclass().getName()+"_"+this.superclassMethod.getId()+"( (_class_"+this.currentClass.getSuperclass().getName()+"*) self");

        if (!this.expressionList.getExprList().isEmpty()) {
            pw.print(",");
        }

        Iterator<Expression> it = expressionList.getExprList().iterator();
        while (it.hasNext()) {
            Expression expr = it.next();
            expr.genC(pw);

            if (it.hasNext()) {
                pw.print(", ");
            }
        }

        pw.print(")");
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
