package org.kurodev.ui.components.table;

import org.kurodev.fields.Table;
import org.kurodev.polynomials.Polynomial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

import static javax.swing.UIManager.getDefaults;

public class TableComponent extends JPanel {
    private final TableType type;
    JTable vHeader = new JTable();
    JTable content = new JTable();

    public TableComponent(TableType type) {
        this.type = type;
        init();
    }

    private void init() {
        content.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        vHeader.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        vHeader.getTableHeader().setReorderingAllowed(false);
        content.getTableHeader().setReorderingAllowed(false);

        JScrollPane vHeaderPane = new JScrollPane(vHeader);
        JScrollPane contentPane = new JScrollPane(content);
        contentPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        vHeaderPane.setPreferredSize(new Dimension(70, 0));

        vHeaderPane.setHorizontalScrollBar(null);
        contentPane.setVerticalScrollBar(vHeaderPane.getVerticalScrollBar());

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        add(contentPane, BorderLayout.CENTER);
        add(vHeaderPane, BorderLayout.WEST);

        synchronizeTables();
    }

    private void synchronizeTables() {
        content.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int selectedRow = content.getSelectedRow();
            vHeader.setRowSelectionInterval(selectedRow, selectedRow);
        });

        vHeader.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int selectedRow = vHeader.getSelectedRow();
            content.setRowSelectionInterval(selectedRow, selectedRow);
        });
    }

    public void setData(Table data) {
        Polynomial[] headerValues = data.baseValues();
        TableModel verticalModel = new DefaultTableModel(headerValues.length, 1);
        TableModel horizontalModel = new DefaultTableModel(1, headerValues.length);
        TableModel contentModel = new DefaultTableModel(headerValues.length, headerValues.length);
        vHeader.setModel(verticalModel);
        content.setModel(contentModel);
        vHeader.getColumnModel().getColumn(0).setHeaderValue(data.symbol());
        for (int i = 0; i < headerValues.length; i++) {
            content.getColumnModel().getColumn(i).setHeaderValue(headerValues[i]);
            horizontalModel.setValueAt(headerValues[i], 0, i);
            verticalModel.setValueAt(headerValues[i], i, 0);
        }
        Polynomial[][] table = data.table();
        for (int row = 0; row < table.length; row++) {
            for (int col = 0; col < table[row].length; col++) {
                contentModel.setValueAt(table[row][col], row, col);
            }
        }
        vHeader.getColumnModel().getColumn(0).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel(value.toString());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            if (isSelected) {
                label.setBackground(UIManager.getColor("Table.selectionBackground"));
                label.setForeground(UIManager.getColor("Table.selectionForeground"));
            } else if (hasFocus) {
                label.setBackground(UIManager.getColor("TableHeader.focusCellBackground"));
                label.setForeground(UIManager.getColor("TableHeader.foreground"));
            } else {
                label.setBackground(UIManager.getColor("TableHeader.background"));
                label.setForeground(UIManager.getColor("TableHeader.foreground"));
            }

            label.setFont(getDefaults().getFont("TableHeader.font"));
            label.setBorder(getDefaults().getBorder("TableHeader.cellBorder"));
            label.setOpaque(true);
            return label;
        });
        for (int col = 0; col < table.length; col++) {
            content.getColumnModel().getColumn(col).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
                JLabel label = new JLabel(value.toString());
                if (this.type == TableType.MULTIPLICATION) {
                    if (value.toString().equals("0")){
                        label.setBackground(Color.red);
                    }
                }
                label.setOpaque(true);
                return label;
            });
        }


    }

}
