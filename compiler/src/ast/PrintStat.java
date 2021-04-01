package ast;

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
        // TODO Auto-generated method stub
    }

    private String printName;
    private ExpressionList exprList;
}
