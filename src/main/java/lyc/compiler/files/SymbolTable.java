package lyc.compiler.files;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    private static HashMap<String, SymbolInfo> symbolMap = new HashMap<String, SymbolInfo>();
    private static Stack<String> pila = new Stack<String>();

    public static void insertPila(String valor) {
        pila.push(valor);
    }

    public static void pilaToTable(String dataType) {
        while (!pila.isEmpty()) {
            String id = pila.pop();
            symbolMap.put(id, new SymbolInfo(null, dataType));
        }
    }

    public static void insertId(String name) {
        if (!symbolMap.containsKey(name))
            symbolMap.put(name, new SymbolInfo());
    }

    public static void insertString(String name) {
        if (!symbolMap.containsKey(name))
            symbolMap.put(name, new SymbolInfo(name.length(), name));
    }

    public static void insertNumber(String name, String dataType) {
        if (!symbolMap.containsKey(name))
            symbolMap.put(name, new SymbolInfo(name, dataType));
    }

    public static String findVariableType(String variable) throws Exception {
        System.out.println(variable);
        SymbolInfo info = symbolMap.get(variable);
        if(info != null) {
            return info.getDataType();
        }else{
            throw new Exception(variable + " nunca jamas definida");
        }
    }

    public static HashMap<String, SymbolInfo> getSymbolable() {
        return symbolMap;
    }
}

