/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * FormalParamDec ::= ParamDec { "," ParamDec }
 */
public class FormalParamDec {
    public FormalParamDec() {
        this.paramList = new ArrayList<>();
    }

    public ArrayList<ParamDec> getParamList() {
        return paramList;
    }

    public void addParam(ParamDec paramDec) {
        this.paramList.add(paramDec);
    }

    public void genJava(PW pw) {
        Iterator<ParamDec> it = paramList.iterator();
        while (it.hasNext()) {
            ParamDec paramDec = it.next();
            paramDec.genJava(pw);

            if (it.hasNext()) {
                pw.print(", ");
            }
        }
    }

    private ArrayList<ParamDec> paramList;
}
