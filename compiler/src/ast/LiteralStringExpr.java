/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class LiteralStringExpr extends Expression {

    public LiteralStringExpr( String literalString ) {
        this.literalString = literalString;
    }

    public void genC( PW pw, boolean putParenthesis ) {
        pw.print("\"" + literalString + "\"");
    }
    @Override
    public void genJava(PW pw) {
        pw.print("\"" + literalString + "\"");
    }

    public Type getType() {
        return Type.stringType;
    }

    private String literalString;
}
