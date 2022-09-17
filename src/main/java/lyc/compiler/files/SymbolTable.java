package lyc.compiler.files;

import java.util.HashMap;

public class SymbolTable {

    public static HashMap<String,SymbolInfo> symbolMap = new HashMap<String,SymbolInfo>();

    public static void insert(String names, String dataType){
        for( String name : names.split(", ")){
            //if(symbolMap.containsKey(name))
            //  ERROR->variable re-declarada;
            symbolMap.put(name, new SymbolInfo(dataType));
        }
    }

}
