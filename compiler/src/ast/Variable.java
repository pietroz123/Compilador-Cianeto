/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/**
 * Classe para as variáveis
 */
public class Variable {
    private String name;
    private Type type;

    /**
     * Getters
     */
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }

    /**
     * Setters
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setType(Type type) {
        this.type = type;
    }
}
