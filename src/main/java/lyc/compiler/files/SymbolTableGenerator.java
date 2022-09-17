package lyc.compiler.files;

import java.io.FileWriter;
import java.io.IOException;

public class SymbolTableGenerator implements FileGenerator{

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write("TODO");
        for (String symbol : SymbolTable.symbolMap.keySet()){
            SymbolInfo s=SymbolTable.symbolMap.get(symbol);
            System.out.println(symbol + ", " + s.getDataType());
        }
    }
}
