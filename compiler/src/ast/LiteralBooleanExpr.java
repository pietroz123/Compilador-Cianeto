/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class LiteralBooleanExpr extends Expression {

    public LiteralBooleanExpr( boolean value ) {
        this.value = value;
    }

    @Override
	public void genC( PW pw, boolean putParenthesis ) {
        pw.print( value ? "1" : "0" );
    }
    @Override
    public void genJava(PW pw) {
        pw.print( value ? "true" : "false" );
    }

    @Override
	public Type getType() {
        return Type.booleanType;
    }

    public static LiteralBooleanExpr True  = new LiteralBooleanExpr(true);
    public static LiteralBooleanExpr False = new LiteralBooleanExpr(false);

    private boolean value;

}
