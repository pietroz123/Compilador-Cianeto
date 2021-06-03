/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class TypeInt extends Type {

    public TypeInt() {
        super("int");
    }

    @Override
    public String getCname() {
        return "int";
    }
    @Override
    public String getJavaName() {
        return "Integer";
    }
    @Override
    public String getCspecifier() {
        return "%d";
    }

}