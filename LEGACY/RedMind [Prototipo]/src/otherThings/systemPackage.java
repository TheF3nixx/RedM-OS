package otherThings;

import security.encryptedDocument;
import java.io.Serializable;
import java.util.List;
import traces.Trace;

public class systemPackage implements Serializable {
    private String packageId;
    private List<Trace> traces;
    private List<encryptedDocument> documents;

    public void addTrace(Trace t) {
        traces.add(t);
    }

    public void addDocument(encryptedDocument doc) {
        documents.add(doc);
    }

    public void exportAsZip(String location) {
        // Bonus: método futuro para crear ZIP real
    }

    // Métodos de resumen, buscar dentro, etc...
}

