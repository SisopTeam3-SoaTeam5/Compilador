package lyc.compiler.model;

public class Terceto {
    private static int count = 0;
    private Integer tercetoId;
    private String celda1;
    private String celda2;
    private String celda3;

    public Terceto( String celda1, String celda2, String celda3) {
        count++;
        tercetoId = count;
        this.celda1 = celda1;
        this.celda2 = celda2;
        this.celda3 = celda3;
    }

    public Terceto( String celda1) {
        count++;
        tercetoId = count;
        this.celda1 = celda1;
        this.celda2 = null;
        this.celda3 = null;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Terceto.count = count;
    }

    public Integer getTercetoId() {
        return tercetoId;
    }

    public void setTercetoId(Integer tercetoId) {
        this.tercetoId = tercetoId;
    }

    public String getCelda1() {
        return celda1;
    }

    public void setCelda1(String celda1) {
        this.celda1 = celda1;
    }

    public String getCelda2() {
        return celda2;
    }

    public void setCelda2(String celda2) {
        this.celda2 = celda2;
    }

    public String getCelda3() {
        return celda3;
    }

    public void setCelda3(String celda3) {
        this.celda3 = celda3;
    }
}
