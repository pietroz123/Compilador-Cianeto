package ast;

public class ReadExpr extends Expression {

    public ReadExpr(String readName) {
        this.readName = readName;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    private String readName;
}