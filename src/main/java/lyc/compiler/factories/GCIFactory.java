package lyc.compiler.factories;

import lyc.compiler.model.Terceto;

import java.util.ArrayList;
import java.util.HashMap;

public class GCIFactory {
    private ArrayList<Terceto> tercetos = new ArrayList<>() ;
    private HashMap<String, String> GCIVariables = new HashMap<>();

    private void insertarTerceto(Terceto t){
        tercetos.add(t);
    }


}
