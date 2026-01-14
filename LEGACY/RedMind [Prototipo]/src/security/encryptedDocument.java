package security;

import java.io.Serializable;

public class encryptedDocument implements Serializable {
    private String id;
    private String encryptedContent;
    private String key;
    private String title;
    private String level; // e.g., "Alpha", "Omega"

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEncryptedContent() {
        return encryptedContent;
    }

    public void setEncryptedContent(String encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean verifyKey(String inputKey) {
        return this.key.equals(inputKey);
    }

    public String getDecryptedContent(String inputKey) {
        return verifyKey(inputKey) ? encryptedContent : "[ACCESS DENIED]";
    }

    // Métodos para cifrar, establecer nueva clave, cambiar nivel...
}
