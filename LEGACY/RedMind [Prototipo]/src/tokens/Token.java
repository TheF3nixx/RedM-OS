/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tokens;

/**
 *
 * @author Usuario
 */
public class Token {
    private boolean canVisualize;
    private boolean canEdit;
    private boolean canExecute;

    public Token(boolean v, boolean d, boolean x) {
        this.canVisualize = v;
        this.canEdit = d;
        this.canExecute = x;
    }

    public static Token fromString(String tokenStr) {
        if (tokenStr.length() != 3) throw new IllegalArgumentException("Formato inválido de token: " + tokenStr);
        return new Token(
            tokenStr.charAt(0) == 'V',
            tokenStr.charAt(1) == 'D',
            tokenStr.charAt(2) == 'X'
        );
    }

    public String toTokenString() {
        return (canVisualize ? "V" : "-") + (canEdit ? "D" : "-") + (canExecute ? "X" : "-");
    }

    public void merge(Token other) {
        this.canVisualize |= other.canVisualize;
        this.canEdit |= other.canEdit;
        this.canExecute |= other.canExecute;
    }

    public void remove(Token other) {
        if (other.canVisualize) this.canVisualize = false;
        if (other.canEdit) this.canEdit = false;
        if (other.canExecute) this.canExecute = false;
    }
}

