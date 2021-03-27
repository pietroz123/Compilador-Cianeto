package ast;

public class LiteralIntExpr extends Expr {

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
        // TODO Auto-generated method stub
    }

    public Type getType() {
        return Type.intType;
    }

    private int value;
}
