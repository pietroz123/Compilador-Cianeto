package ast;

public class WhileStat extends Statement {

    public WhileStat(Expression e, Statement s) {
        this.expr = e;
        this.statement = s;
    }

    /**
     * Getters
     */
    public Expression getExpr() {
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

    private Expression expr;
    private Statement statement;
}
