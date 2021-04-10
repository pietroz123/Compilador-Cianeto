package ast;

/**
 * ObjectCreation ::= Id "." "new"
 */
public class ObjectCreation extends PrimaryExpr {

    public ObjectCreation(TypeCianetoClass cianetoClass) {
        this.cianetoClass = cianetoClass;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }

    @Override
    public Type getType() {
        return cianetoClass;
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    private TypeCianetoClass cianetoClass;
}
