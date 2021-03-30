/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import lexer.Token;

public class CompositeExpr extends Expression {

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        // TODO Auto-generated method stub
    }
    @Override
	public void genJava(PW pw) {
		// TODO Auto-generated method stub
	}

    @Override
    public Type getType() {

        if (oper == Token.EQ || oper == Token.NEQ || oper == Token.LE || oper == Token.LT || oper == Token.GE || oper == Token.GT) {
            return Type.booleanType;
        }
        else if (oper == Token.AND || oper == Token.OR) {
            return Type.booleanType;
        }
        else {
            return Type.intType;
        }

    }

    private Expression left, right;
    private Token oper;

}