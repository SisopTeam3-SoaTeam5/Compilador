package lyc.compiler.files;

public class SymbolInfo {

    private String dataType;
    private String value;
    private int length;

    public SymbolInfo(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
