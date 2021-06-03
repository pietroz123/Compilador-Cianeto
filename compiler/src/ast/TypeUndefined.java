/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class TypeUndefined extends Type {
    // variables that are not declared have this type

   public TypeUndefined() { super("undefined"); }

   @Override
   public String getCname() {
      return "int";
   }
   @Override
   public String getJavaName() {
      return "naosei";
   }
   @Override
   public String getCspecifier() {
      return "";
   }

}
