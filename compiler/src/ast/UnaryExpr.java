/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import lexer.Token;

/**
 * Utilizado em situações como
 *      a = !a
 */
public class UnaryExpr extends Expression {

    public UnaryExpr(Token token, Expression expr) {
        this.token = token;
        this.expr = expr;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        if (token == Token.NOT) {
            pw.print("!");
        }

        expr.genC(pw);
    }

    @Override
    public void genJava(PW pw) {
        if (token == Token.NOT) {
            pw.print("!");
        }

        expr.genJava(pw);
    }

    @Override
    public Type getType() {
        return expr.getType();
    }

    private Token token;
    private Expression expr;
}
