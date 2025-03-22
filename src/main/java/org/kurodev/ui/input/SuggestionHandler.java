package org.kurodev.ui.input;

import org.kurodev.polynomials.Polynomial;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class SuggestionHandler implements KeyListener {
    private final JPopupMenu suggestionPopup = new JPopupMenu();
    private final JTextField inputField;
    private List<Polynomial> suggestions;
    private int currentSelection = -1;

    public SuggestionHandler(JTextField inputField) {
        this.inputField = inputField;
        inputField.addKeyListener(this);
        suggestionPopup.setFocusable(false);
        addDocumentListener();
    }

    /**
     * Sets the list of polynomial suggestions.
     *
     * @param suggestions List of Polynomial suggestions to display.
     */
    public void setSuggestions(List<Polynomial> suggestions) {
        this.suggestions = suggestions;
        updateSuggestions();
    }

    /**
     * Updates and displays the suggestion popup based on the current input.
     */
    void updateSuggestions() {
        if (suggestions == null) return;
        suggestionPopup.removeAll();  // Clear previous suggestions
        currentSelection = -1;  // Reset selection

        String inputText = inputField.getText().replaceAll(" ", "");

        if (!suggestions.isEmpty()) {
            suggestions.stream()
                    .filter(poly -> {
                        String polyString = poly.toNonSuperscriptString().replaceAll(" ", "");
                        String polyString2 = poly.toString().replaceAll(" ", "");
                        return polyString.startsWith(inputText) || polyString2.startsWith(inputText);  // Filter suggestions
                    })
                    .limit(10)
                    .forEach(suggestion -> {
                        JMenuItem item = new JMenuItem(suggestion.toString());
                        item.addActionListener(e -> inputField.setText(suggestion.toString()));  // Set text when clicked
                        suggestionPopup.add(item);
                    });

            // Adjust the size of the popup based on the number of items
            int itemCount = suggestionPopup.getComponentCount();
            if (itemCount == 0 || !inputField.hasFocus()) {
                suggestionPopup.setVisible(false);
                return;
            }
            int popupHeight = Math.min(itemCount, 10) * suggestionPopup.getComponent(0).getPreferredSize().height;

            suggestionPopup.setPreferredSize(new Dimension(inputField.getWidth(), popupHeight));
            suggestionPopup.show(inputField, 0, inputField.getHeight());  // Show the updated popup
            suggestionPopup.revalidate();  // Revalidate the popup after adding new items
            suggestionPopup.repaint();  // Repaint the popup
        } else {
            suggestionPopup.setVisible(false);  // Hide popup if no suggestions
        }
    }

    /**
     * Highlights the currently selected suggestion in the popup.
     */
    private void highlightSelection() {
        int itemCount = suggestionPopup.getComponentCount();
        for (int i = 0; i < itemCount; i++) {
            JMenuItem item = (JMenuItem) suggestionPopup.getComponent(i);
            item.setArmed(i == currentSelection);  // Highlight the selected item
        }
    }

    /**
     * Adds a document listener to the input field for more accurate text changes.
     */
    private void addDocumentListener() {
        inputField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();  // Trigger update when text is inserted
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();  // Trigger update when text is removed
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();  // Trigger update for attribute changes (rare in this case)
            }
        });
    }

    /**
     * Shows the suggestions popup when the input field gains focus.
     */
    public void focusGained() {
        updateSuggestions();
        suggestionPopup.setVisible(true);
    }

    /**
     * Hides the suggestions popup when the input field loses focus.
     */
    public void focusLost() {
        SwingUtilities.invokeLater(() -> {
            if (!suggestionPopup.isFocusOwner()) {
                suggestionPopup.setVisible(false);
            }
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!suggestionPopup.isVisible()) return;

        int itemCount = suggestionPopup.getComponentCount();
        if (itemCount == 0) return;

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Arrow down: move selection down
            currentSelection = (currentSelection + 1) % itemCount;
            highlightSelection();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Arrow up: move selection up
            currentSelection = (currentSelection - 1 + itemCount) % itemCount;
            highlightSelection();
        } else if (e.getKeyCode() == KeyEvent.VK_TAB || e.getKeyCode() == KeyEvent.VK_ENTER) {
            // Tab or Enter: select the highlighted item
            if (currentSelection >= 0) {
                JMenuItem selectedItem = (JMenuItem) suggestionPopup.getComponent(currentSelection);
                inputField.setText(selectedItem.getText());
                suggestionPopup.setVisible(false);  // Hide the popup after selection
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
