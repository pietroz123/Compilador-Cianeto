package ast;

import java.util.ArrayList;

/**
 * IdList ::= Id { "," Id }
 */
public class IdList {
    public IdList() {
        this.idList = new ArrayList<>();
    }

    /**
     * Getters
     */
    public ArrayList<String> getIdList() {
        return idList;
    }

    /**
     * Adiciona um Id (string) na lista de ids
     * @param id
     */
    public void addId(String id) {
        this.idList.add(id);
    }

    private ArrayList<String> idList;
}
