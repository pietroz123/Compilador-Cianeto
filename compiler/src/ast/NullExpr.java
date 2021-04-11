/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

public class NullExpr extends Expression {

   public void genC( PW pw, boolean putParenthesis ) {
      pw.printIdent("NULL");
   }
   @Override
   public void genJava(PW pw) {
      // TODO Auto-generated method stub
   }

   public Type getType() {
      //# corrija
      return null;
   }
}