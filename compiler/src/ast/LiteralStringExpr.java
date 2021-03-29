package ast;

public class LiteralStringExpr extends Expression {

    public LiteralStringExpr( String literalString ) {
        this.literalString = literalString;
    }

    public void genC( PW pw, boolean putParenthesis ) {
        pw.print(literalString);
    }
    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    public Type getType() {
        return Type.stringType;
    }

    private String literalString;
}
