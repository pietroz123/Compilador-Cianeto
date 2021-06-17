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
        // Pairs.MethodIndex methodIndex = this.currentClass.searchMethodInVirtualTable(this.superclassMethod.getId());
        // pw.print("( ");
        // methodIndex.method.printCSignature(pw);
        // pw.print(" self->vt[" + methodIndex.index + "]");
        // pw.print(" )");

        // pw.print("(self");

        // if (!this.expressionList.getExprList().isEmpty()) {
        //     pw.print(",");
        // }

        // Iterator<Expression> it2 = expressionList.getExprList().iterator();
        // while (it2.hasNext()) {
        //     Expression expr = it2.next();
        //     expr.genC(pw);

        //     if (it2.hasNext()) {
        //         pw.print(", ");
        //     }
        // }

        // pw.print(")");

        String methodToCall = this.superclassMethod.getId();

        TypeCianetoClass current = this.currentClass.getSuperclass();
        MethodDec theMethod;

        // Sobe na hierarquia de classes até achar a classe com o método
        while ((theMethod = current.searchAllMethods(methodToCall)) == null) {
            current = current.getSuperclass();
        }

        pw.print("_"+theMethod.getCurrentClass().getName()+"_"+theMethod.getId()+"( (_class_"+theMethod.getCurrentClass().getName()+"*) self");

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
