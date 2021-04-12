/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import lexer.Token;

public class SignalExpr extends Expression {

    public SignalExpr(Token signal, Expression factor) {
        this.signal = signal;
        this.factor = factor;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }
    @Override
    public Type getType() {
        return factor.getType();
    }
    @Override
    public void genJava(PW pw) {
        pw.print(signal.toString());

        if (factor != null) {
            factor.genJava(pw);
        }
    }

    private Token signal;
    private Expression factor;
}
