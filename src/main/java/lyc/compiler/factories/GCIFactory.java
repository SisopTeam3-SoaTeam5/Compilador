package lyc.compiler.factories;

import lyc.compiler.model.Terceto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GCIFactory {
    private static ArrayList<Terceto> tercetos = new ArrayList<>();
    public HashMap<String, String> GCIVariables = new HashMap<>();
    public Stack<String> tStack = new Stack<>();
    public Stack<String> eStack = new Stack<>();
    public Stack<Integer> cellStack = new Stack<>();

    private String comparator;
    private Stack<String> logical = new Stack<>();
    private HashMap<String, String> reverseComparator = new HashMap<>();

    public GCIFactory() {
        reverseComparator.put("BNE", "BEQ");
        reverseComparator.put("BEQ", "BNE");
        reverseComparator.put("BGT", "BLE");
        reverseComparator.put("BLT", "BGE");
        reverseComparator.put("BGE", "BLT");
        reverseComparator.put("BLE", "BGT");
    }

    public void insertarTerceto(String varInd, Terceto t) {
        GCIVariables.put(varInd, "[" + tercetos.size() + "]");
        tercetos.add(t);
    }

    public void insertarTerceto(Terceto t) {
        tercetos.add(t);
    }

    public void asignarVarind(String var1, String var2) {
        GCIVariables.put(var1, GCIVariables.get(var2));
    }

    public void asignarVarind(String var1, Stack<String> stack) {
        GCIVariables.put(var1, stack.pop());
    }

    public void printTercetos() {
        for (int i = 0; i < tercetos.size(); i++) {
            Terceto t = tercetos.get(i);
            System.out.println("[" + i + "]: " + "(" + t.getCelda1() + "," + t.getCelda2() + "," + t.getCelda3() + ")");
        }
    }

    public String getVariable(String var) {
        return GCIVariables.get(var);
    }

    public void setComparator(String comp) {
        comparator = comp;
    }

    public void revertComparator() {
        System.out.println("revertt");
        comparator = reverseComparator.get(comparator);
    }

    public void pushLogical(String log) {
        logical.push(log);
    }

    public void pushCell() {
        cellStack.push(tercetos.size());
    }

    public void insertBranch() {
        tercetos.add(new Terceto(comparator, null, null));
    }

    public void insertBranch(int n) {
        if (logical.peek() == "or" && n == 1)
            revertComparator();
        tercetos.add(new Terceto(comparator, null, null));
    }

    public void startIf() {
        if (logical.peek() == "or")     // si fue or guardo posicion de inicio del if
            cellStack.push(tercetos.size());
    }

    public void endIf() {
        String log = logical.pop(); //el conector del if que esta cerrando
        if (log == null)
            tercetos.get(cellStack.pop()).setCelda2("[" + tercetos.size() + "]");
        else if (log == "or") {
            int startIf = cellStack.pop();
            int cond2 = cellStack.pop();
            int cond1 = cellStack.pop();
            tercetos.get(cond1).setCelda2("[" + startIf + "]");
            tercetos.get(cond2).setCelda2("[" + tercetos.size() + "]");
        } else {
            int cond2 = cellStack.pop();
            int cond1 = cellStack.pop();
            tercetos.get(cond1).setCelda2("[" + tercetos.size() + "]");
            tercetos.get(cond2).setCelda2("[" + tercetos.size() + "]");
        }
    }



    public static String writeTercetos() {
        String result = "";
        for (int i = 0; i < tercetos.size(); i++) {
            Terceto t = tercetos.get(i);
            result += "[" + i + "]: " + "(" + t.getCelda1() + "," + t.getCelda2() + "," + t.getCelda3() + ")\n";
        }
        return result;
    }

}
