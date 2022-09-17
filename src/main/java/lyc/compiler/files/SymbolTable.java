package lyc.compiler.files;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    public static HashMap<String, SymbolInfo> symbolMap = new HashMap<String, SymbolInfo>();
    public static Stack<String> symbolStack = new Stack<String>();


    public static void insert(String dataType) {
        while (!symbolStack.isEmpty()) {
            String name = symbolStack.pop();
            if(symbolMap.containsKey(name))
              throw new RuntimeException("Variable " + name + " ya declarada");
            symbolMap.put(name, new SymbolInfo(dataType));
        }
    }

    public static void insertString(String id,String value){
        SymbolInfo data=symbolMap.get(id);
        if(data == null)
            throw new RuntimeException("No existe variable: " + id);
        else if (data.getDataType() != "String")
            throw new RuntimeException("La variable: "+id+" es de tipo "+data.getDataType()+", usted esta asignando tipo String");
        data.setValue(value);
        data.setLength(value.length());
        symbolMap.put(id,data);
    }

}
