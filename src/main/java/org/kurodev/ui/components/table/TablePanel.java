package org.kurodev.ui.components.table;

import org.kurodev.fields.FiniteField;
import org.kurodev.ui.components.userinput.CalculationRequest;
import org.kurodev.ui.util.AsyncPolynomialOperations;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JTabbedPane {
    private final TableComponent addition = new TableComponent(TableType.ADDITION);
    private final TableComponent multiplication = new TableComponent(TableType.MULTIPLICATION);

    // Custom Tab Components to hold title + loading indicator
    private final JPanel additionTab = new JPanel(new BorderLayout());
    private final JPanel multiplicationTab = new JPanel(new BorderLayout());

    private final JLabel additionLoading = new JLabel(new ImageIcon("path/to/loading.gif"));
    private final JLabel multiplicationLoading = new JLabel(new ImageIcon("path/to/loading.gif"));

    public TablePanel() {
        init();
    }

    private void init() {
        // Setup the tab components
        setupTab(additionTab, "Additionstabelle", addition, additionLoading);
        setupTab(multiplicationTab, "Multiplikationstabelle", multiplication, multiplicationLoading);
    }

    private void setupTab(JPanel tabPanel, String title, TableComponent tableComponent, JLabel loadingLabel) {
        // Create the tab with the table component
        tabPanel.add(tableComponent, BorderLayout.CENTER);

        // Add the loading icon to the panel
        JPanel loadingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loadingPanel.add(loadingLabel);

        // Add the loading label to the left of the title
        tabPanel.add(loadingPanel, BorderLayout.WEST);

        // Add the tab to the tabbed pane with the custom component
        addTab(title, tabPanel);
    }

    public void acceptFiniteField(CalculationRequest request) {
        FiniteField field = request.getField();
        setLoading("Additionstabelle", true);
        setLoading("Multiplikationstabelle", true);

        AsyncPolynomialOperations.calculateTable(field, TableType.ADDITION).whenComplete((table, throwable) -> {
            if (throwable == null) {
                SwingUtilities.invokeLater(() -> addition.setData(table));
            }
            setLoading("Additionstabelle", false);
        });

        AsyncPolynomialOperations.calculateTable(field, TableType.MULTIPLICATION).whenComplete((table, throwable) -> {
            if (throwable == null) {
                SwingUtilities.invokeLater(() -> multiplication.setData(table));
            }
            setLoading("Multiplikationstabelle", false);
        });
    }

    private void setLoading(String tabName, boolean loading) {
        if (tabName.equals("Additionstabelle")) {
            additionLoading.setVisible(loading);
        } else if (tabName.equals("Multiplikationstabelle")) {
            multiplicationLoading.setVisible(loading);
        }
    }
}
