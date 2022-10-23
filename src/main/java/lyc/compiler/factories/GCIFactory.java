package lyc.compiler.factories;

import lyc.compiler.model.Terceto;

import java.util.ArrayList;
import java.util.HashMap;

public class GCIFactory {
    private static ArrayList<Terceto> tercetos = new ArrayList<>() ;
    private static HashMap<String, String> GCIVariables = new HashMap<>();

    private static void insertarTerceto(Terceto t){
        tercetos.add(t);
    }


    public static void pintTercetos(){
        for(int i=0;i<tercetos.size();i++){
            Terceto t=tercetos.get(i);
            System.out.println("["+i+"]: "+ "("+t.getCelda1()+","+t.getCelda2()+","+t.getCelda3()+")");
        }
    }

}
