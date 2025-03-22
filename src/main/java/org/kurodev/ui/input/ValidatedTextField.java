package org.kurodev.ui.input;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ValidatedTextField extends JTextField implements FocusListener, KeyListener, DocumentListener {
    List<Consumer<String>> changeListeners = new ArrayList<>();
    List<Consumer<Boolean>> focusListeners = new ArrayList<>();

    public ValidatedTextField() {
        getDocument().addDocumentListener(this);
        addFocusListener(this);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        notifyObservers();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        notifyObservers();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //not used in text fields
    }


    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void addChangeListener(Consumer<String> listener) {
        changeListeners.add(listener);
    }

    private void notifyObservers() {
        changeListeners.forEach(listener -> listener.accept(getText()));
    }

    public void addFocusListener(Consumer<Boolean> onFocusChange) {
        focusListeners.add(onFocusChange);
    }

    @Override
    public void focusLost(FocusEvent e) {
        focusListeners.forEach(consumer -> consumer.accept(false));
    }

    @Override
    public void focusGained(FocusEvent e) {
        focusListeners.forEach(consumer -> consumer.accept(true));
    }

}
