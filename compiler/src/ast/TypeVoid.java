/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class TypeVoid extends Type {

   public TypeVoid() { super("void"); }

   @Override
   public String getCname() {
      return "void";
   }

   @Override
   public String getJavaName() {
      return "void";
   }
   @Override
   public String getCspecifier() {
      return "";
   }

}
