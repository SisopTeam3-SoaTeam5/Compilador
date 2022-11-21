package lyc.compiler.files;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    public static HashMap<String, SymbolInfo> symbolMap = new HashMap<String, SymbolInfo>();
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

    public static void insertString(String name) {
        String nameNoSpaces=quitSpaces(name);
        if (!symbolMap.containsKey(name))
            symbolMap.put(nameNoSpaces, new SymbolInfo(name.length(), name));
    }

    public static void insertNumber(String name, String dataType) {
        if (!symbolMap.containsKey(name))
            symbolMap.put("_"+name, new SymbolInfo(name, dataType));
    }

    public static String findVariableType(String variable) throws Exception {
        variable=quitSpaces(variable);
        System.out.println(variable);
        SymbolInfo info = symbolMap.get(variable);
        if(info != null) {
            return info.getDataType();
        }else{
            throw new Exception(variable + " nunca jamas definida");
        }
    }

    private static String quitSpaces(String id){
        String r=id.replace("\"","_").replace(" ","_");
        System.out.println(r);
        return r;
    }

    public static HashMap<String, SymbolInfo> getSymbolable() {
        return symbolMap;
    }
}

