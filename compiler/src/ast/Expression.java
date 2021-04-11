/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

abstract public class Expression extends Statement {
    abstract public void genC( PW pw, boolean putParenthesis );
	@Override
	public void genC(PW pw) {
		this.genC(pw, false);
	}

      // new method: the type of the expression
    abstract public Type getType();
}