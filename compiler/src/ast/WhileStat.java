package ast;

public class WhileStat extends Statement {

    public WhileStat(Expr e, Statement s) {
        this.expr = e;
        this.statement = s;
    }

    /**
     * Getters
     */
    public Expr getExpr() {
        return expr;
    }
    public Statement getStatement() {
        return statement;
    }

    @Override
    public void genC(PW pw) {
        // TODO Auto-generated method stub
    }
    @Override
    public void genJava(PW pw) {
        // TODO Auto-generated method stub
    }

    private Expr expr;
    private Statement statement;
}
