/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;

public class ExpressionList {

    public ExpressionList() {
        this.exprList = new ArrayList<>();
    }

    /**
     * Getters
     */
    public ArrayList<Expression> getExprList() {
        return exprList;
    }

    /**
     * Adiciona uma Expression na lista
     * @param expr
     */
    public void addExpr(Expression expr) {
        exprList.add(expr);
    }

    public void genJava(PW pw) {
        for (Expression expr : exprList) {
            expr.genJava(pw);
        }
    }

    ArrayList<Expression> exprList;
}
