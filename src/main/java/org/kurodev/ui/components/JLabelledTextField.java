package org.kurodev.ui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class JLabelledTextField extends JPanel {
    private final JTextField textField;

    public JLabelledTextField(String label, JTextField textField, int width) {
        Border border = BorderFactory.createEmptyBorder(5, 3, 5, 3);
        this.setBorder(border);
        this.textField = textField;
        JLabel text = new JLabel(label);
        text.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(text);
        add(textField);
        textField.setMaximumSize(new Dimension(width, 20));
    }

    public JTextField getTextField() {
        return textField;
    }
}
