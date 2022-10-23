package lyc.compiler.factories;

import lyc.compiler.model.Terceto;

import java.util.ArrayList;
import java.util.HashMap;

public class GCIFactory {
    private static ArrayList<Terceto> tercetos = new ArrayList<>() ;
    public static HashMap<String, Integer> GCIVariables = new HashMap<>();

    public static void insertarTerceto(String varInd, Terceto t){
        GCIVariables.put(varInd,tercetos.size());
        tercetos.add(t);
    }

    public static void asignarVarind(String var1, String var2){
       GCIVariables.put(var1, GCIVariables.get(var2));
    }

}
