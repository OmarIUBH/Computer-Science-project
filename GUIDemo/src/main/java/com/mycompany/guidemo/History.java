/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guidemo;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * History window for the Smart Bag prototype.
 * Displays scan history in a JTable (local, in-memory).
 * Real system: this would be stored in a cloud database instead.
 */
public class History extends javax.swing.JFrame {

    private JTable historyTable;
    private DefaultTableModel tableModel;

    public History(List<MainFrame.HistoryEntry> entries) {
        super("Scan History (Prototype)");
        initComponents(entries);
    }

    private void initComponents(List<MainFrame.HistoryEntry> entries) {
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Timestamp", "Missing Required Items"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only history
            }
        };

        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);

        // Fill table with history rows
        for (MainFrame.HistoryEntry entry : entries) {
            String timestamp = entry.getFormattedTimestamp();
            String missing = (entry.getMissingItems() == null || entry.getMissingItems().isEmpty())
                    ? "None"
                    : String.join(", ", entry.getMissingItems());
            tableModel.addRow(new Object[]{timestamp, missing});
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
