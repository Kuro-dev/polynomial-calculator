package org.kurodev.ui.input;

import org.kurodev.polynomials.Polynomial;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PolynomialInput extends ValidatedTextField {
    private static final List<Character> CHARACTER_LIST = new ArrayList<>();
    private final SuggestionHandler suggestionHandler;

    static {
        for (int i = 0; i <= 9; i++) {
            CHARACTER_LIST.add((char) (0x30 + i));
        }
        CHARACTER_LIST.add('x');
        CHARACTER_LIST.add('^');
        CHARACTER_LIST.add('+');
        CHARACTER_LIST.add('-');
        CHARACTER_LIST.add(' ');
        CHARACTER_LIST.addAll(Arrays.stream(Polynomial.SUPERSCRIPT_DIGITS).map(s -> s.toCharArray()[0]).toList());
    }

    public PolynomialInput() {
        suggestionHandler = new SuggestionHandler(this);  // Initialize suggestion handler
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (CHARACTER_LIST.contains(e.getKeyChar())) {
            // Update suggestions when the user types valid characters
            suggestionHandler.updateSuggestions();
            return;
        }
        e.consume();
    }

    public void setSuggestions(List<Polynomial> suggestions) {
        System.out.println("setting suggestions");
        suggestionHandler.setSuggestions(suggestions);
    }

    @Override
    public void focusGained(FocusEvent e) {
        super.focusGained(e);
        suggestionHandler.focusGained();
    }

    @Override
    public void focusLost(FocusEvent e) {
        super.focusLost(e);
        suggestionHandler.focusLost();
    }
}
