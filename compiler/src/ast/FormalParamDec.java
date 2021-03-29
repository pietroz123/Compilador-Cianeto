/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;

/**
 * FormalParamDec ::= ParamDec { "," ParamDec }
 */
public class FormalParamDec {
    public FormalParamDec() {
        this.paramList = new ArrayList<>();
    }

    public void addParam(ParamDec paramDec) {
        this.paramList.add(paramDec);
    }

    private ArrayList<ParamDec> paramList;
}
