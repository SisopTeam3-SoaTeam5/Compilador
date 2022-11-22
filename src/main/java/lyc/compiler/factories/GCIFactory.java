package lyc.compiler.factories;

import lyc.compiler.model.Terceto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GCIFactory {
    public static ArrayList<Terceto> tercetos = new ArrayList<>();
    public HashMap<String, String> GCIVariables = new HashMap<>();
    public Stack<String> tStack = new Stack<>();
    public Stack<String> eStack = new Stack<>();
    public Stack<Integer> cellStack = new Stack<>();
    public Stack<Integer> allEqStack = new Stack<>();
    public Stack<Integer> whileStack = new Stack<>();
    public Stack<Integer> switchStack = new Stack<>();
    public Stack<Integer> switchInconditionalStack = new Stack<>();
    private Stack<Integer> casesCount = new Stack<>();
    private Stack<String> switchIds = new Stack<>();
    public ArrayList<Integer> allEqListA = new ArrayList<>();
    public ArrayList<Integer> allEqListB = new ArrayList<>();

    private String comparator;
    private Stack<String> logical = new Stack<>();
    public static HashMap<String, String> reverseComparator = new HashMap<>();
    public boolean listAFull = false;
    private int ifCount = 0;
    public static int whileCount = 0;
    public int allEqCount = 0;
    private int switchCount = 0;

    public void setListAFull(boolean listAFull) {
        this.listAFull = listAFull;
    }

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

    public void compararListasExp() {
        if (allEqListA.size() != allEqListB.size()) {
            System.out.println("Error, no seas nabo usa listas con la misma cant de elementos");
            return;
        }
        for (int i = 0; i < allEqListA.size(); i++) {
            tercetos.add(new Terceto("CMP", "[" + allEqListA.get(i) + "]", "[" + allEqListB.get(i) + "]"));
            tercetos.add(new Terceto("BNE"));
            allEqStack.push(tercetos.size() - 1);
        }
        allEqListB.clear();
    }

    public void resolverAllEq() {
        tercetos.add(new Terceto("=", "@allEq", "1"));
        tercetos.add(new Terceto("BI"));
        Integer i = tercetos.size() - 1;
        tercetos.add(new Terceto("et_AllEq_"+ allEqCount++));
        tercetos.add(new Terceto("=","@allEq", "0"));
        Integer tercetoFalse = tercetos.size() - 2;
//        tercetos.add(new Terceto());
        Integer skip = tercetos.size();
        tercetos.add(new Terceto("et_AllEq_"+ allEqCount++));
        tercetos.get(i).setCelda2("[" + skip + "]");

        while (!allEqStack.empty()) {
            tercetos.get(allEqStack.pop()).setCelda2("[" + tercetoFalse + "]");
        }
        allEqListA.clear();
        allEqListB.clear();
        listAFull = false;
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

    public void addToList(Integer i) {
        if (!listAFull) {
            allEqListA.add(i);
        } else {
            allEqListB.add(i);
        }
    }

    public String getVariable(String var) {
        return GCIVariables.get(var);
    }

    public void setComparator(String comp) {
        comparator = comp;
    }

    public void revertComparator() {
        comparator = reverseComparator.get(comparator);
    }

    public void pushLogical(String log) {
        logical.push(log);
    }

    public void pushCell() {
        cellStack.push(tercetos.size());
    }

    public void pushSwitchId(String id) {
        switchIds.push(id);
    }

    public void validateSwitchId() throws Exception {
        String id = tercetos.get(tercetos.size() - 3).getCelda1();
        if (!id.equals(switchIds.peek()))
            throw new Exception("Las comparaciones de un case deben tener a la variable utilizada en su definición del lado izquierdo\n" +
                    "Switch declarado para variable " + switchIds.peek() + " compara a la variable " + id);
    }

    public void newSwitch() {
        casesCount.push(1);
    }

    public void incCasesCount() {
        casesCount.push(casesCount.pop() + 1);
    }

    public void setSwitchBranch() {
        tercetos.get(switchStack.pop()).setCelda2(tercetos.size() + "");
        insertarTerceto(new Terceto("et_SWITCH_" + switchCount++));
    }

    public void pushSwitchCell() {
        switchStack.push(tercetos.size());
    }

    public void pushSwitchInconditionalCell() {
        switchInconditionalStack.push(tercetos.size());
    }

    public void pushSwitchEndBlockCell() {
        switchIds.pop();
        int casesCant = casesCount.pop();
        for (int i = 0; i < casesCant; i++)
            tercetos.get(switchInconditionalStack.pop()).setCelda2(tercetos.size() + "");
        insertarTerceto(new Terceto("et_SWITCH_"+ switchCount++));
    }

    public void insertBranch() {
        if (comparator != null)
            tercetos.add(new Terceto(comparator, null, null));
    }

    public void insertBranch(int n) {
        if (logical.peek() == "or" && n == 1)
            revertComparator();
        tercetos.add(new Terceto(comparator, null, null));
    }

    public void startIf() {
        if (logical.peek() == "or") {     // si fue or guardo posicion de inicio del if
            cellStack.push(tercetos.size());
            insertarTerceto(new Terceto("et_IF_" + ifCount++));
        }
    }

    public void endIf(int sum,boolean insertEtiq) {
        String log = logical.pop(); //el conector del if que esta cerrando
        if (log == null)
            tercetos.get(cellStack.pop()).setCelda2("[" + (tercetos.size() + sum) + "]");
        else if (log == "or") {
            int startIf = cellStack.pop();
            int cond2 = cellStack.pop();
            int cond1 = cellStack.pop();
            tercetos.get(cond1).setCelda2("[" + startIf + "]");
            tercetos.get(cond2).setCelda2("[" + (tercetos.size() + sum) + "]");
        } else {
            int cond2 = cellStack.pop();
            int cond1 = cellStack.pop();
            tercetos.get(cond1).setCelda2("[" + (tercetos.size() + sum) + "]");
            tercetos.get(cond2).setCelda2("[" + (tercetos.size() + sum) + "]");
        }
        if(insertEtiq)
            insertarTerceto(new Terceto("et_IF_" + ifCount++));
    }

    public void beforeElse() {
        insertarTerceto(new Terceto("BI"));
        insertarTerceto(new Terceto("et_IF_" + ifCount++));
    }

    public void endElse() {
        tercetos.get(cellStack.pop()).setCelda2("[" + tercetos.size() + "]");
        insertarTerceto(new Terceto("et_IF_" + ifCount++));
    }

    public void startLoop() {
        if (logical.peek() == "or") {  // si fue or guardo posicion de inicio del if
            cellStack.push(tercetos.size());
        }
    }

    public void endLoop() {
        String log = logical.pop(); //el conector del if que esta cerrando
        if (log == null)
            tercetos.get(cellStack.pop()).setCelda2("[" + (tercetos.size() + 1) + "]");
        else if (log == "or") {
            int startLoop = cellStack.pop();
            int cond2 = cellStack.pop();
            int cond1 = cellStack.pop();
            tercetos.get(cond1).setCelda2("[" + startLoop + "]");
            tercetos.get(cond2).setCelda2("[" + (tercetos.size() + 1) + "]");
        } else {
            int cond2 = cellStack.pop();
            int cond1 = cellStack.pop();
            tercetos.get(cond1).setCelda2("[" + (tercetos.size() + 1) + "]");
            tercetos.get(cond2).setCelda2("[" + (tercetos.size() + 1) + "]");
        }
        tercetos.add(new Terceto("BI", "[" + (whileStack.pop() - 1) + "]"));
        insertarTerceto(new Terceto("et_WHILE_" + whileCount++));
    }

    public void assignCond(String id) {
        startIf();
        insertarTerceto(new Terceto("=", id, "1"));
        insertarTerceto(new Terceto("BI", "[" + (tercetos.size() + 3) + "]"));
        endIf(0,true);
        insertarTerceto(new Terceto("=", id, "0"));
        insertarTerceto(new Terceto("et_IF_" + ifCount++));
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
