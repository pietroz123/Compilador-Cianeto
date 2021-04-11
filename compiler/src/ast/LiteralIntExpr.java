/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class LiteralIntExpr extends Expression {

    public LiteralIntExpr( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public void genC( PW pw, boolean putParenthesis ) {
        pw.printIdent("" + value);
    }
    @Override
    public void genJava(PW pw) {
        pw.print("" + value);
    }

    public Type getType() {
        return Type.intType;
    }

    private int value;
}
