package ast;

import java.util.ArrayList;

/**
 * IdList ::= Id { "," Id }
 */
public class IdList {
    public IdList() {
        this.idList = new ArrayList<>();
    }

    public void addId(String id) {
        this.idList.add(id);
    }

    private ArrayList<String> idList;
}
