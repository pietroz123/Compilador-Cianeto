package ast;

public class UnaryMessagePassingToSelf extends Expression {

    private TypeCianetoClass currentClass;
    private Variable var;
    private TypeCianetoClass cianetoClass;

    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, Variable var) {
        this.currentClass = currentClass;
        this.var = var;
    }

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
