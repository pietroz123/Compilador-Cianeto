package ast;

/**
 * "self" "." Id
 * "self" "." Id "." Id
 */
public class UnaryMessagePassingToSelf extends Expression {

    private TypeCianetoClass currentClass;
    private Variable var;
    private TypeCianetoClass cianetoClass;

    // "self" "." Id
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, Variable var) {
        this.currentClass = currentClass;
        this.var = var;
    }

    // "self" "." Id "." Id
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, TypeCianetoClass cianetoClass, Variable var) {
        this.currentClass = currentClass;
        this.cianetoClass = cianetoClass;
        this.var = var;
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

}
