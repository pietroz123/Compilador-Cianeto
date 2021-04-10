package ast;

/**
 * "super" "." Id
 */
public class UnaryMessagePassingToSuper extends Expression {

    private TypeCianetoClass currentClass;
    private MethodDec var;

    public UnaryMessagePassingToSuper(TypeCianetoClass currentClass, MethodDec var) {
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
