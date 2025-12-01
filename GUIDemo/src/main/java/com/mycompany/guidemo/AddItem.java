/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.guidemo;


import java.awt.GridLayout;
import javax.swing.*;

/**
 * Simple dialog for adding a new item to the Smart Bag prototype.
 * Returns name + required flag to MainFrame.
 */
public class AddItem extends JDialog {

    private JTextField txtName;
    private JCheckBox chkRequired;
    private boolean confirmed = false;

    public AddItem(JFrame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    private void initComponents() {
        setTitle("Add Item");
        setSize(300, 180);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtName = new JTextField();
        chkRequired = new JCheckBox("Required item");

        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnOk.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        btnCancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.add(new JLabel("Item name:"));
        panel.add(txtName);
        panel.add(chkRequired);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);

        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));
        getContentPane().add(panel, java.awt.BorderLayout.CENTER);
        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getItemName() {
        return txtName.getText();
    }

    public boolean isRequired() {
        return chkRequired.isSelected();
    }
}

