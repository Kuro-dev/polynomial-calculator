package org.kurodev;

import org.kurodev.ui.MainWindow;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main {
    static {
        UIManager.put("TableHeader.background", Color.lightGray);
        UIManager.put("TableHeader.foreground", Color.black);

        UIManager.put("TableHeader.selectionBackground", Color.cyan);
        UIManager.put("TableHeader.selectionForeground", Color.black);

        UIManager.put("Table.font", new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));


        Border border = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.gray);
        UIManager.put("TableHeader.cellBorder", border);


    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow("Körperwelten");
        window.setVisible(true);
    }
}