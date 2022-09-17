package lyc.compiler.files;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    public static HashMap<String,SymbolInfo> symbolMap = new HashMap<String,SymbolInfo>();
    public static Stack<String> symbolStack = new Stack<String>();

    public static void insert(String dataType){
        while( !symbolStack.isEmpty()){
            String name=symbolStack.pop();
            System.out.println("NAAAAMMMMEEEE: "+name);
            //if(symbolMap.containsKey(name))
              //  ERROR->variable re-declarada;
            symbolMap.put(name, new SymbolInfo(dataType));
        }
    }

}
