package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class SymbolTableGenerator implements FileGenerator{

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("------- Symbol Table -------\n");
        fileWriter.write("--------------------------------------------------------------------------------------------------\n");
        fileWriter.write(String.format("%-42s","Name")+"|| "+String.format("%-20s","Type")+"|| "+String.format("%-42s","Value")+"|| "+String.format("%-20s","Size")+"\n");
        fileWriter.write("--------------------------------------------------------------------------------------------------\n");
        HashMap<String, SymbolInfo> symbolMap=SymbolTable.getSymbolable();
        for (String symbol : symbolMap.keySet()){
            SymbolInfo s=symbolMap.get(symbol);
            String name = String.format("%-42s",symbol);
            String type = String.format("%-20s",s.getDataType()!=null?s.getDataType():"-");
            String value = String.format("%-42s",s.getValue()!=null?s.getValue():"-");
            String size = String.format("%-20s",s.getLength()!=0?s.getLength():"-");
            fileWriter.write(name+"|| "+type+"|| "+ value+"|| "+size+"\n");
        }
    }
}
