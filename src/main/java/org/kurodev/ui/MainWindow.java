package org.kurodev.ui;

import org.kurodev.ui.components.userinput.InputPanel;
import org.kurodev.ui.components.table.TablePanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow(String title) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(title);
        setLocationRelativeTo(null);
        setSize(800, 600);
        init();
    }

    private void init() {
        BorderLayout MainWindowLayout = new BorderLayout();
        JPanel panel = new JPanel(MainWindowLayout);
        TablePanel tablePanel = new TablePanel();
        panel.add(new InputPanel(tablePanel::acceptFiniteField), BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        add(panel);
    }

}
