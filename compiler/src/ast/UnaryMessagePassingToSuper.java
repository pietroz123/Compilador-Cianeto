package ast;

public class UnaryMessagePassingToSuper extends Expression {

    private TypeCianetoClass currentClass;
    private Variable var;

    public UnaryMessagePassingToSuper(TypeCianetoClass currentClass, Variable var) {
        this.currentClass = currentClass;
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
