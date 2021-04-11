/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * "self" "." Id
 * "self" "." Id "." Id
 */
public class UnaryMessagePassingToSelf extends Expression {

    private TypeCianetoClass currentClass;
    private Object var; // pode ser um campo ou um método
    private TypeCianetoClass cianetoClass;

    // "self" "." Id
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, Object var) {
        this.currentClass = currentClass;
        this.var = var;
    }

    // "self" "." Id "." Id
    public UnaryMessagePassingToSelf(TypeCianetoClass currentClass, TypeCianetoClass cianetoClass, Object var) {
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
        if (var instanceof FieldDec) {
            return ((FieldDec) var).getType();
        }
        else if (var instanceof MethodDec) {
            return ((MethodDec) var).getReturnType();
        }
        else if (var instanceof Variable) {
            return ((Variable) var).getType();
        }

        return null;
    }

    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub

    }

}
