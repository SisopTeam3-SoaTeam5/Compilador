package lyc.compiler.files;

public class SymbolInfo {

    private String dataType;
    private String value;
    private int length;

    public SymbolInfo() {
    }

    public SymbolInfo(String value, int length) {
        this.value = value;
        this.length = length;
    }

    public SymbolInfo(int length, String value) {
        this.value = value;
        this.length = length;
        this.dataType = "String";
    }

    public SymbolInfo(String value, String dataType) {
        this.value = value;
        this.dataType = dataType;
    }


    public String getDataType() {
        return dataType;
    }

    public int getLength() {
        return length;
    }

    public String getValue() {
        return value;
    }
}
