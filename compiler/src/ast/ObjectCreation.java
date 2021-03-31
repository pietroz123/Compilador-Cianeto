package ast;

/**
 * ObjectCreation ::= Id "." "new"
 */
public class ObjectCreation extends PrimaryExpr {

    public ObjectCreation(String id) {
        this.id = id;
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

    private String id;
}
