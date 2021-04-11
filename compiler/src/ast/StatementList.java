/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;

/**
 * StatementList ::= { Statement }
 */
public class StatementList {

    public StatementList() {
        this.statementList = new ArrayList<Statement>();
    }

    /**
     * Adiciona um Statement na lista de Statement's
     * @param statement
     */
    public void addStatement(Statement statement) {
        statementList.add(statement);
    }

    public void genJava(PW pw) {
        for (Statement statement : statementList) {
            statement.genJava(pw);
        }
    }

    private ArrayList<Statement> statementList;
}
