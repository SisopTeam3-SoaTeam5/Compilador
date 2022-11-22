package lyc.compiler.files;

import lyc.compiler.factories.GCIFactory;
import lyc.compiler.model.Terceto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AsmCodeGenerator implements FileGenerator {

    public static HashMap<String, String> branchNames = new HashMap<>();
    public AsmCodeGenerator(){
        branchNames.put("BNE", "JNE");
        branchNames.put("BEQ", "JE");
        branchNames.put("BGT", "JA");
        branchNames.put("BLT", "JB");
        branchNames.put("BGE", "JAE");
        branchNames.put("BLE", "JNA");
    }

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
        fileWriter.write("\n.CODE \n");


        ArrayList<Terceto> tercetos= GCIFactory.tercetos;
        String tabs = "\t";
        for(Terceto t : tercetos){
            if(t.getCelda2() == null && t.getCelda3() == null && !t.getCelda1().startsWith("et_")){ // Apilar variables y ctes
                fileWriter.write(tabs+"fld "+t.getCelda1()+ "\n");
            }
            else if(t.getCelda1().contains("CMP")){ // Comparar
                fileWriter.write(tabs+"fxch\n");
                fileWriter.write(tabs+"fcom\n");
                fileWriter.write(tabs+"fstsw ax\n");
                fileWriter.write(tabs+"sahf\n");
            }
            else if(GCIFactory.reverseComparator.containsKey(t.getCelda1())){ //Branchs con etiqs
                fileWriter.write(branchNames.get(t.getCelda1()) + " " + tercetos.get(Integer.parseInt(t.getCelda2().replace("[","").replace("]",""))).getCelda1() + System.lineSeparator());
            }
            else if(t.getCelda1().startsWith("et_")){
                fileWriter.write(t.getCelda1()+":\n");
            }
            else if(t.getCelda1().equals("+")){
                fileWriter.write(tabs+"fadd\n");
            }
            else if(t.getCelda1().equals("-")){
                fileWriter.write(tabs+"fsub\n");
            }
            else if(t.getCelda1().equals("/")){
                fileWriter.write(tabs+"fdiv\n");
            }
            else if(t.getCelda1().equals("*")){
                fileWriter.write(tabs+"fmul\n");
            }
            else if(t.getCelda1().equals("=")){
                if(t.getCelda2().equals("@allEq")){
                    fileWriter.write(tabs+"fld " + t.getCelda3() + "\n");
                }
                fileWriter.write(tabs+"fstp " + t.getCelda2()+"\n");
            }

        }

        fileWriter.write("\n\nmov ax,4c00h");
        fileWriter.write("\nint\t21h");
        fileWriter.write("\nEnd");
    }

    private boolean isConstant(String id) {
        return id.startsWith("_") || id.startsWith("\"");
    }

//    private String createBranch()

}
