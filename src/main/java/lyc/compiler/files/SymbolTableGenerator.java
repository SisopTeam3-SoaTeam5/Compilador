package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;


public class SymbolTableGenerator implements FileGenerator{

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("------- Symbol Table -------\n");
        fileWriter.write("--------------------------------------------------------------------------------------------------\n");
        fileWriter.write(String.format("%-30s","Name")+"|| "+String.format("%-20s","Type")+"|| "+String.format("%-20s","Value")+"|| "+String.format("%-20s","Size")+"\n");
        fileWriter.write("--------------------------------------------------------------------------------------------------\n");
        for (String symbol : SymbolTable.symbolMap.keySet()){
            SymbolInfo s=SymbolTable.symbolMap.get(symbol);
            String name = String.format("%-30s",symbol);
            String type = String.format("%-20s",s.getDataType());
            String value = String.format("%-20s",s.getValue()!=null?s.getValue():"xxValuexx");
            String size = String.format("%-20s",s.getLength()!=0?s.getLength():"xxSizexx");
            fileWriter.write(name+"|| "+type+"|| "+ value+"|| "+size+"\n");
        }
    }
}
