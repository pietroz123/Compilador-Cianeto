/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

/*
 * Krakatoa Class
 */
public class TypeCianetoClass extends Type {

   // O construtor de TypeCianetoClass deve ter um unico parametro, o nome da classe
   public TypeCianetoClass( String name ) {
      super(name);
   }

   @Override
   public String getCname() {
      return getName();
   }

   private String name;
   private TypeCianetoClass superclass;
   // private FieldList fieldList;
   // private MethodList publicMethodList, privateMethodList;
   // m�todos p�blicos get e set para obter e iniciar as vari�veis acima,
   // entre outros m�todos
}
