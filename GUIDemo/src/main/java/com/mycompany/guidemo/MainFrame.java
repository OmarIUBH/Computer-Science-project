/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guidemo;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Main window for the Smart Bag prototype.
 * Simulates RFID-based item detection and manages items + scan history.
 */
public class MainFrame extends javax.swing.JFrame {

    private JTable itemTable;
    private DefaultTableModel itemModel;

    // In-memory scan history for the prototype (no database / no cloud)
    private final List<HistoryEntry> historyEntries = new ArrayList<>();

    public MainFrame() {
        super("Smart Bag Prototype – RFID-Based Item Tracking (Simulation)");
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);

        String[] columns = {"Item Name", "Required", "Present (Simulated Scan)"};
        itemModel = new DefaultTableModel(columns, 0) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1 || columnIndex == 2) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Allow editing the name + "Required" flag, but not "Present"
                return column != 2;
            }
        };

        itemTable = new JTable(itemModel);
        JScrollPane scrollPane = new JScrollPane(itemTable);

        addDemoItems();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JButton btnAdd = new JButton("Add Item");
        JButton btnRemove = new JButton("Remove Item");
        JButton btnSimulateScan = new JButton("Simulate Scan");
        JButton btnSaveScan = new JButton("Save Scan Result");
        JButton btnHistory = new JButton("View History");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnSimulateScan);
        buttonPanel.add(btnSaveScan);
        buttonPanel.add(btnHistory);

        btnAdd.addActionListener(e -> onAddItem());
        btnRemove.addActionListener(e -> onRemoveItem());
        btnSimulateScan.addActionListener(e -> onSimulateScan());
        btnSaveScan.addActionListener(e -> onSaveScan());
        btnHistory.addActionListener(e -> onViewHistory());

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addDemoItems() {
        itemModel.addRow(new Object[]{"Wallet", true, false});
        itemModel.addRow(new Object[]{"Keys", true, false});
        itemModel.addRow(new Object[]{"ID Card", true, false});
        itemModel.addRow(new Object[]{"Notebook", false, false});
    }

    private void onAddItem() {
        AddItem dialog = new AddItem(this, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String name = dialog.getItemName();
            boolean required = dialog.isRequired();
            if (name != null && !name.trim().isEmpty()) {
                itemModel.addRow(new Object[]{name.trim(), required, false});
            }
        }
    }

    private void onRemoveItem() {
        int row = itemTable.getSelectedRow();
        if (row >= 0) {
            itemModel.removeRow(row);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an item to remove.",
                    "No selection",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void onSimulateScan() {
        if (itemModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "There are no items to scan.",
                    "Nothing to scan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Random random = new Random();

        for (int i = 0; i < itemModel.getRowCount(); i++) {
            boolean present = random.nextBoolean();
            itemModel.setValueAt(present, i, 2);
        }

        JOptionPane.showMessageDialog(
                this,
                "Simulated RFID scan completed.\n"
                        + "This is a software-only prototype, no real RC522 reader is used.",
                "Simulation",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void onSaveScan() {
        if (itemModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No items available to save.",
                    "Nothing to save",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        List<String> missingItems = new ArrayList<>();
        for (int i = 0; i < itemModel.getRowCount(); i++) {
            String name = (String) itemModel.getValueAt(i, 0);
            boolean required = (Boolean) itemModel.getValueAt(i, 1);
            boolean present = (Boolean) itemModel.getValueAt(i, 2);

            if (required && !present) {
                missingItems.add(name);
            }
        }

        HistoryEntry entry = new HistoryEntry(LocalDateTime.now(), missingItems);
        historyEntries.add(entry);

        JOptionPane.showMessageDialog(
                this,
                missingItems.isEmpty()
                        ? "Scan saved. All required items are present."
                        : "Scan saved. Missing required items: " + missingItems,
                "Scan Saved",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void onViewHistory() {
        History historyFrame = new History(historyEntries);
        historyFrame.setLocationRelativeTo(this);
        historyFrame.setVisible(true);
    }

    /**
     * Internal representation of a scan event for the prototype.
     * In a real system, this would later be stored in a cloud database.
     */
    public static class HistoryEntry {
        private final LocalDateTime timestamp;
        private final List<String> missingItems;

        public HistoryEntry(LocalDateTime timestamp, List<String> missingItems) {
            this.timestamp = timestamp;
            this.missingItems = missingItems;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public List<String> getMissingItems() {
            return missingItems;
        }

        public String getFormattedTimestamp() {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return fmt.format(timestamp);
        }
    }
}
