package ast;

import java.util.ArrayList;

public class ExpressionList {

    public ExpressionList() {
        this.exprList = new ArrayList<>();
    }

    /**
     * Adiciona uma Expression na lista
     * @param expr
     */
    public void addExpr(Expression expr) {
        exprList.add(expr);
    }

    ArrayList<Expression> exprList;
}
