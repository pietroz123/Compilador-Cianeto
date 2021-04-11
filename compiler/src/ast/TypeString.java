/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class TypeString extends Type {

    public TypeString() {
        super("String");
    }

    @Override
    public String getCname() {
        return "char *";
    }
    @Override
    public String getJavaName() {
        return "String";
    }

}