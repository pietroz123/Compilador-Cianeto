/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.Iterator;

/**
 * PrintStat ::= "Out" "." ( "print:" | "println:" ) Expression { "," Expression }
 */
public class PrintStat extends Statement {

    public PrintStat(String printName, ExpressionList exprList) {
        this.printName = printName;
        this.exprList = exprList;
    }

    @Override
    public void genC(PW pw) {
        // TODO Auto-generated method stub
    }

    @Override
    public void genJava(PW pw) {
        pw.printIdent("");

        if (printName.equals("print")) {
            pw.print("System.out.print(");
        }
        else {
            pw.print("System.out.println(");
        }

        // Expression List
        if (exprList != null) {

            Iterator<Expression> it = exprList.getExprList().iterator();
            while (it.hasNext()) {
                Expression expr = it.next();
                expr.genJava(pw);

                if (it.hasNext()) {
                    pw.print(" + ");
                }
            }

        }
        else {
            pw.print("\"\"");
        }

        pw.println(");");
    }

    private String printName;
    private ExpressionList exprList;
}
