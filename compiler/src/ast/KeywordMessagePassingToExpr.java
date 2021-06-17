/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

/**
 * Id "." IdColon ExpressionList
 */
public class KeywordMessagePassingToExpr extends Expression {

    public KeywordMessagePassingToExpr(Variable instanceVar, TypeCianetoClass sourceClass, MethodDec methodCalled, ExpressionList exprList) {
        this.instanceVar = instanceVar;
        this.sourceClass = sourceClass;
        this.methodCalled = methodCalled;
        this.exprList = exprList;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        Pairs.MethodIndex methodIndex = this.sourceClass.searchMethodInVirtualTable(this.methodCalled.getId());
        pw.print("( ");
        methodIndex.method.printCSignature(pw);
        pw.print(" _"+this.instanceVar.getId()+"->vt[" + methodIndex.index + "]");
        pw.print(" )");
        pw.print("(_"+this.instanceVar.getId()+"");

        if (!this.exprList.getExprList().isEmpty()) {
            pw.print(", ");
        }

        Iterator<Expression> it2 = exprList.getExprList().iterator();
        while (it2.hasNext()) {
            Expression expr = it2.next();
            expr.genC(pw);

            if (it2.hasNext()) {
                pw.print(", ");
            }
        }

        pw.print(")");
    }

    @Override
    public Type getType() {
        return methodCalled.getReturnType();
    }

    @Override
    public void genJava(PW pw) {
        pw.print(instanceVar.getId() + "." + methodCalled.getId());
        pw.print("(");

        Iterator<Expression> it = exprList.getExprList().iterator();
        while (it.hasNext()) {
            Expression expr = it.next();
            expr.genJava(pw);

            if (it.hasNext()) {
                pw.print(", ");
            }
        }

        pw.print(")");
    }

    private Variable instanceVar;
    private TypeCianetoClass sourceClass;
    private MethodDec methodCalled;
    private ExpressionList exprList;
}
