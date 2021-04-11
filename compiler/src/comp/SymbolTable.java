/**
 * Integrantes:
 * Pietro Zuntini Bonfim    RA: 743588
 */

package comp;

import java.util.Hashtable;

/**
 * Tabela de símbolos, contém:
 *      - tabela global de classes
 *      - tabela local de variáveis
 *      - tabela de funções
 */
public class SymbolTable {

    public SymbolTable() {
        globalTable = new Hashtable<String, Object>();
        localTable = new Hashtable<String, Object>();
    }

    /**
     * Inserções nas tabelas
     */
    public void putInGlobal(String key, Object value) {
        globalTable.put(key, value);
    }
    public void putInLocal(String key, Object value) {
        localTable.put(key, value);
    }
    public void putInFunc(String key, Object value) {
	    funcTable.put(key, value);
	}

    /**
     * Getters
     */
    public Object getInLocal(Object key) {
        return localTable.get(key);
    }
    public Object getInGlobal(Object key) {
        return globalTable.get(key);
    }
    public Object getInFunc(Object key) {
		return funcTable.get(key);
	}

    /**
     * Limpeza das tabelas
     */
    public void clearLocal() {
        localTable.clear();
    }
    public void clearFunc() {
        funcTable.clear();
    }

    private Hashtable<String, Object> globalTable;
    private Hashtable<String, Object> localTable;
    private Hashtable<String, Object> funcTable;
}
