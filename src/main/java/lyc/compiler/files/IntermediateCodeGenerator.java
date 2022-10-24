package lyc.compiler.files;

import lyc.compiler.factories.GCIFactory;

import java.io.FileWriter;
import java.io.IOException;

public class IntermediateCodeGenerator implements FileGenerator {

    @Override
    public void generate(FileWriter fileWriter) throws IOException {
        fileWriter.write(GCIFactory.writeTercetos());
    }
}
