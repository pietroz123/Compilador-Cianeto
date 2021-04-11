/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

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
        pw.print("\"\"");
        // TODO
        // if (exprList != null) {
        //     exprList.genJava(pw);
        // }

        pw.println(");");
    }

    private String printName;
    private ExpressionList exprList;
}
