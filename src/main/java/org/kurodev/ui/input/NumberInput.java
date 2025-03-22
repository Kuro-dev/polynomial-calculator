package org.kurodev.ui.input;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

public class NumberInput extends ValidatedTextField {

    private final int minVal;

    public NumberInput(int minVal) {
        this.minVal = minVal;
        addKeyListener(this);
    }

    public NumberInput() {
        this(0);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() >= ('0') && e.getKeyChar() <= '9') {
            return;
        }
        e.consume();
    }


}
