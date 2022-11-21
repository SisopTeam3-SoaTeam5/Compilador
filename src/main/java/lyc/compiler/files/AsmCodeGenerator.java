package lyc.compiler.files;

import lyc.compiler.factories.GCIFactory;
import lyc.compiler.model.Terceto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AsmCodeGenerator implements FileGenerator {

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        HashMap<String, SymbolInfo> symbolMap = SymbolTable.symbolMap;
        fileWriter.write("include macros2.asm\ninclude number.asm\n\n.MODEL  LARGE");
        fileWriter.write(".386\n.STACK 200h\n\nMAXTEXTSIZE equ 50\n\n.DATA\n");
        for (String id : symbolMap.keySet()) {
            if (!isConstant(id)) {
                fileWriter.write("\t" + id + "\tdd\t?\n");
            } else {
                fileWriter.write("\t" + id + "\tdd\t?\t" + symbolMap.get(id).getValue() + "\n");
            }
        }
        fileWriter.write("\n.CODE");


        // ArrayList<Terceto> tercetos= GCIFactory.tercetos;


        //  for(Terceto t : tercetos){

        fileWriter.write("\n\nmov ax,4c00h");
        fileWriter.write("\nint\t21h");
        fileWriter.write("\nEnd");
    }

    private boolean isConstant(String id) {
        return id.startsWith("_") || id.startsWith("\"");
    }

}
