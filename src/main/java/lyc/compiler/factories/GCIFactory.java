package lyc.compiler.factories;

import lyc.compiler.model.Terceto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GCIFactory {
    private static ArrayList<Terceto> tercetos = new ArrayList<>() ;
    public static HashMap<String, String> GCIVariables = new HashMap<>();
    public  static Stack<String> tStack = new Stack<>();
    public  static Stack<String> eStack = new Stack<>();
    public static Stack<Integer> cellStack=new Stack<>();

    private static String comparator;

    public static void insertarTerceto(String varInd, Terceto t){
        GCIVariables.put(varInd,"[" + tercetos.size() + "]");
        tercetos.add(t);
    }

    public static void insertarTerceto(Terceto t){
        tercetos.add(t);
    }

    public static void asignarVarind(String var1, String var2){
       GCIVariables.put(var1, GCIVariables.get(var2));
    }

    public static void asignarVarind(String var1, Stack<String> stack){
        GCIVariables.put(var1, stack.pop());
    }

    public static void printTercetos(){
        for(int i=0;i<tercetos.size();i++){
            Terceto t=tercetos.get(i);
            System.out.println("["+i+"]: "+ "("+t.getCelda1()+","+t.getCelda2()+","+t.getCelda3()+")");
        }
    }

    public static String getVariable(String var){
        return GCIVariables.get(var);
    }

    public static void setComparator(String comp){
        comparator=comp;
    }

    public static String getComparator(){
        return comparator;
    }

    public static void pushCell(){
        cellStack.push(tercetos.size());
    }

    public static String writeTercetos(){
        String result= "";
        for(int i=0;i<tercetos.size();i++){
            Terceto t=tercetos.get(i);
            result += "["+i+"]: "+ "("+t.getCelda1()+","+t.getCelda2()+","+t.getCelda3()+")\n";
        }
        return result;
    }

}
