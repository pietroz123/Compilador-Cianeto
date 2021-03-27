/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/*
 * Krakatoa Class
 */
public class TypeCianetoClass extends Type {

    // O construtor de TypeCianetoClass deve ter um unico parametro, o nome da
    // classe
    public TypeCianetoClass(String name) {
        super(name);
    }

    @Override
    public String getCname() {
        return getName();
    }

    public TypeCianetoClass getSuperclass() {
        return superclass;
    }
    public void setSuperclass(TypeCianetoClass superclass) {
        this.superclass = superclass;
    }

    /**
     * Verifica se é subclasse
     * @param other
     * @return
     */
    public boolean isSubclassOf(Type other) {
        TypeCianetoClass current = this;

        while (current != other) {
            current = current.getSuperclass();
            if (current == null) {
                return false;
            }
        }

        return true;
    }

    private String name;
    private TypeCianetoClass superclass;
    // private FieldList fieldList;
    // private MethodList publicMethodList, privateMethodList;
    // m�todos p�blicos get e set para obter e iniciar as vari�veis acima,
    // entre outros m�todos
}
