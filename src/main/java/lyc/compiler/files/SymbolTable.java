package lyc.compiler.files;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    private static HashMap<String, SymbolInfo> symbolMap = new HashMap<String, SymbolInfo>();


    public static void insertId(String name) {
        if(!symbolMap.containsKey(name))
            symbolMap.put(name, new SymbolInfo());
    }


    public static void insertString(String name) {
        if(!symbolMap.containsKey(name))
            symbolMap.put(name, new SymbolInfo(name,name.length()));
    }

    public static void insertNumber(String name) {
        if(!symbolMap.containsKey(name))
            symbolMap.put(name, new SymbolInfo((name)));
    }

    public static HashMap<String, SymbolInfo> getSymbolable() {
        return symbolMap;
    }
}

