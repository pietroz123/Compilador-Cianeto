/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import lexer.Token;

public class CompositeExpr extends Expression {

    public CompositeExpr(Expression left, Token oper, Expression right) {
        this.left = left;
        this.oper = oper;
        this.right = right;
    }

    // Getters
    public Token getOper() {
        return oper;
    }

    @Override
    public void genC(PW pw, boolean putParenthesis) {
        if (oper == Token.PLUSPLUS) {
            char first = left.getType().getName().toLowerCase().charAt(0);
            char second = right.getType().getName().toLowerCase().charAt(0);
            String funcName = "plusplus_" + first + second;
            pw.print(funcName + "(");
            left.genC(pw);
            pw.print(", ");
            right.genC(pw);
            pw.print(")");
        } else {
            left.genC(pw);
            pw.print(" " + oper + " ");
            right.genC(pw);
        }
    }
    @Override
	public void genJava(PW pw) {
        left.genJava(pw);

        if (oper == Token.PLUSPLUS) {
            pw.print(" + ");
        } else {
            pw.print(" " + oper + " ");
        }

		right.genJava(pw);
	}

    @Override
    public Type getType() {

        if (oper == Token.EQ || oper == Token.NEQ || oper == Token.LE || oper == Token.LT || oper == Token.GE || oper == Token.GT) {
            return Type.booleanType;
        }
        else if (oper == Token.AND || oper == Token.OR) {
            return Type.booleanType;
        }
        else if (oper == Token.PLUSPLUS) {
            return Type.stringType;
        }
        else {
            return Type.intType;
        }

    }

    private Expression left, right;
    private Token oper;
}
