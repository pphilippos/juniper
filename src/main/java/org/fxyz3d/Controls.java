/*
 * Copyright (C) 2024  Philippos Papaphilippou
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * 
 */
package org.fxyz3d; // (official way of using FXyz)

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape3D;
import javafx.util.Pair;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.fxyz3d.shapes.primitives.Text3DMesh;

/** 
 * This is the window that contains all controls related to the plot as GUI elements
 * (around the first half of the code is generated with NetBeans' Form Editor).
 *
 * @author Philippos Papaphilippou
 */
public class Controls extends javax.swing.JFrame {
    Juniper parent;
    File datasetf;
    File screenshotf;
    String[] variableNames; // Column names from the dataset
    
    // A map of string occurances and their insertion number, per dimension, for non-numerical attributes
    ArrayList<Map<String, Integer>> stringtypes = new ArrayList<Map<String, Integer>>();
    
    // Numerical part of the datset, per available dimension
    ArrayList<double[]> dataset = new ArrayList<double[]>();
    
    // Same but as strings, e.g. for labels on points
    ArrayList<String[]> datasetStringed = new ArrayList<String[]>();
    
    String[] columns = {"Variable Name", "x", "y","z","z2","z3","z4"};
    
    /**
     * Creates new form Controls. It contains code for manual initialisation, like
     * the combo box options.
     */
    public Controls() {
        initComponents();
        
        // Colour code the x, y, z columns in the assignment table to match plot
        DefaultTableCellRenderer colortext=new DefaultTableCellRenderer();   
        colortext.setForeground(Color.RED);
        colortext.setHorizontalAlignment( JLabel.CENTER );
        dimAssignmentTable.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(colortext);
        colortext=new DefaultTableCellRenderer();    
        colortext.setForeground(Color.BLUE);
        colortext.setHorizontalAlignment( JLabel.CENTER );
        dimAssignmentTable.getTableHeader().getColumnModel().getColumn(2).setHeaderRenderer(colortext);
        colortext=new DefaultTableCellRenderer();    
        colortext.setForeground(Color.GREEN);
        colortext.setHorizontalAlignment( JLabel.CENTER );
        dimAssignmentTable.getTableHeader().getColumnModel().getColumn(3).setHeaderRenderer(colortext);
        
        // Introduce point types in their combo box
        pTypeComboBox.removeAllItems();
        pTypeComboBox.addItem("Sphere");
        pTypeComboBox.addItem("Box");
        pTypeComboBox.addItem("Bar");
        pTypeComboBox.setSelectedIndex(1);

        // Enter data delimiters in their combo box
        fsepComboBox.removeAllItems();
        fsepComboBox.addItem("[Space]");
        fsepComboBox.addItem("[Tab]");
        fsepComboBox.addItem(","); // e.g. "," for traditional comma-separated-value files
        fsepComboBox.addItem(";");
        fsepComboBox.addItem("[Space|Tab]");
        fsepComboBox.setSelectedIndex(1);
        
        // Enter grid types in their combo box
        grid_type.removeAllItems();
        grid_type.addItem("Line");
        grid_type.addItem("Surface");
        grid_type.addItem("(Exclusive)"); // see documentation for hybrid types
        grid_type.addItem("(Presel. 1st)");
        grid_type.setSelectedIndex(0);
        
        // Enter shadow types in their combo box
        shadow_type.removeAllItems();
        shadow_type.addItem("Projection");
        shadow_type.addItem("Light path");
        shadow_type.addItem("(Both)");
        shadow_type.addItem("(Exclusive)");
        shadow_type.addItem("(Preselect 1st)");        
        shadow_type.setSelectedIndex(0);
        
        // Organise radio buttons
        button_group_viewer_sources.add(viewRecRadioButton);
        button_group_viewer_sources.add(viewPointsRadioButton);
        
        picXdimSpinner.setEditor(new JSpinner.NumberEditor(picXdimSpinner, "#"));
        picYdimSpinner.setEditor(new JSpinner.NumberEditor(picYdimSpinner, "#")); 
        
        // Enter top-N selection critertia for z4 in their combo box
        z4cb1.removeAllItems();
        z4cb1.addItem("Upper");
        z4cb1.addItem("Lower");
        z4cb1.addItem("All");
        
        z4cb2.removeAllItems();
                
        aboutLabel.setBackground(new java.awt.Color(255, 255, 255, 160));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button_group_viewer_sources = new javax.swing.ButtonGroup();
        button_group_ratios = new javax.swing.ButtonGroup();
        jTabbedPane = new javax.swing.JTabbedPane();
        dataPanel = new javax.swing.JPanel();
        fileSelectLabel = new javax.swing.JLabel();
        loadButton = new javax.swing.JButton();
        fileSepLabel = new javax.swing.JLabel();
        fsepComboBox = new javax.swing.JComboBox<>();
        namesFromLineCheckBox = new javax.swing.JCheckBox();
        exitButton = new javax.swing.JButton();
        pasteButton = new javax.swing.JButton();
        dimPanel = new javax.swing.JPanel();
        dimAssignmentScrollPane = new javax.swing.JScrollPane();
        dimAssignmentTable = new javax.swing.JTable();
        plotButton = new javax.swing.JButton();
        zdimLegendLabel = new javax.swing.JLabel();
        z4LayeredPane = new javax.swing.JLayeredPane();
        z4l1 = new javax.swing.JLabel();
        z4cb1 = new javax.swing.JComboBox<>();
        z4l2 = new javax.swing.JLabel();
        z4js = new javax.swing.JSpinner();
        z4l3 = new javax.swing.JLabel();
        z4cb2 = new javax.swing.JComboBox<>();
        viewPanel = new javax.swing.JPanel();
        axes_s = new javax.swing.JCheckBox();
        pSizeSlider = new javax.swing.JSlider();
        pSizeLabel = new javax.swing.JLabel();
        pTypeLabel = new javax.swing.JLabel();
        pTypeComboBox = new javax.swing.JComboBox<>();
        depthLayeredPane = new javax.swing.JLayeredPane();
        fovLabel = new javax.swing.JLabel();
        fovSlider = new javax.swing.JSlider();
        yaw_stuck = new javax.swing.JCheckBox();
        depthcolors = new javax.swing.JCheckBox();
        fogLabel = new javax.swing.JLabel();
        fog_slider = new javax.swing.JSlider();
        shadowLabel = new javax.swing.JLabel();
        shadowx1 = new javax.swing.JCheckBox();
        shadowy1 = new javax.swing.JCheckBox();
        shadowz1 = new javax.swing.JCheckBox();
        shadowTypeLabel = new javax.swing.JLabel();
        shadow_type = new javax.swing.JComboBox<>();
        sOpacityLabel = new javax.swing.JLabel();
        shadow_opacity = new javax.swing.JSlider();
        depthPercLabel = new javax.swing.JLabel();
        labelsLayeredPane = new javax.swing.JLayeredPane();
        xlabel = new javax.swing.JTextField();
        ylabel = new javax.swing.JTextField();
        zlabel = new javax.swing.JTextField();
        xlabel_d = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        ylabel_d = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        xlabel_s = new javax.swing.JCheckBox();
        ylabel_s = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        xtic_d = new javax.swing.JSpinner();
        ytic_d = new javax.swing.JSpinner();
        ztic_d = new javax.swing.JSpinner();
        xtic_s = new javax.swing.JCheckBox();
        ytic_s = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        zlabel_d = new javax.swing.JSpinner();
        zlabel_s = new javax.swing.JCheckBox();
        labels_sync = new javax.swing.JCheckBox();
        xtic_h = new javax.swing.JCheckBox();
        ytic_h = new javax.swing.JCheckBox();
        ztic_s = new javax.swing.JCheckBox();
        ztic_h = new javax.swing.JCheckBox();
        tics_sync = new javax.swing.JCheckBox();
        zlabel_h = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        lsize = new javax.swing.JSpinner();
        z4l4 = new javax.swing.JLabel();
        z4cb3 = new javax.swing.JSpinner();
        axesm_s = new javax.swing.JCheckBox();
        tics_marks = new javax.swing.JCheckBox();
        showLabel = new javax.swing.JLabel();
        gridLabel = new javax.swing.JLabel();
        gridx = new javax.swing.JCheckBox();
        gridy = new javax.swing.JCheckBox();
        gridz = new javax.swing.JCheckBox();
        gridTypeLabel = new javax.swing.JLabel();
        grid_type = new javax.swing.JComboBox<>();
        opacityLabel = new javax.swing.JLabel();
        grid_opacity = new javax.swing.JSlider();
        labels_rgb = new javax.swing.JCheckBox();
        label_border = new javax.swing.JCheckBox();
        viewerPanel = new javax.swing.JPanel();
        viewerOptPanel = new javax.swing.JPanel();
        dataViewerLoadButton = new javax.swing.JButton();
        viewRecRadioButton = new javax.swing.JRadioButton();
        viewPointsRadioButton = new javax.swing.JRadioButton();
        dataViewerPane = new javax.swing.JScrollPane();
        dataViewerTable = new javax.swing.JTable();
        exportPanel = new javax.swing.JPanel();
        imageLayeredPane = new javax.swing.JLayeredPane();
        resLabel = new javax.swing.JLabel();
        resTimesLabel = new javax.swing.JLabel();
        ratioLabel = new javax.swing.JLabel();
        ratiosLayeredPane = new javax.swing.JLayeredPane();
        ratio1 = new javax.swing.JRadioButton();
        ratio2 = new javax.swing.JRadioButton();
        ratio3 = new javax.swing.JRadioButton();
        ratio4 = new javax.swing.JRadioButton();
        ratio5 = new javax.swing.JRadioButton();
        ratio6 = new javax.swing.JRadioButton();
        imgExportButton = new javax.swing.JButton();
        picYdimSpinner = new javax.swing.JSpinner();
        picXdimSpinner = new javax.swing.JSpinner();
        imageLabel = new javax.swing.JLabel();
        aboutPanel = new javax.swing.JPanel();
        aboutLabel = new javax.swing.JLabel();
        licenseScrollPane = new javax.swing.JScrollPane();
        licenseTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controls");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fileSelectLabel.setText("File selection");

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        fileSepLabel.setText("File Separator");

        fsepComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fsepComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fsepComboBoxActionPerformed(evt);
            }
        });

        namesFromLineCheckBox.setSelected(true);
        namesFromLineCheckBox.setText("The first line contains the parameter names");

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        pasteButton.setText("Paste");
        pasteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dataPanelLayout.createSequentialGroup()
                        .addComponent(fileSepLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fsepComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(namesFromLineCheckBox)
                    .addGroup(dataPanelLayout.createSequentialGroup()
                        .addComponent(fileSelectLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pasteButton)))
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton)
                .addContainerGap())
        );
        dataPanelLayout.setVerticalGroup(
            dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataPanelLayout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileSepLabel)
                    .addComponent(fsepComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(namesFromLineCheckBox)
                .addGap(18, 18, 18)
                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileSelectLabel)
                    .addComponent(loadButton)
                    .addComponent(pasteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                .addComponent(exitButton)
                .addContainerGap())
        );

        jTabbedPane.addTab("Dataset", dataPanel);

        dimPanel.setToolTipText("");

        dimAssignmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Variable Name", "x", "y", "z", "z1", "z2", "z3", "z4", "log"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dimAssignmentTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        dimAssignmentTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dimAssignmentTablePropertyChange(evt);
            }
        });
        dimAssignmentScrollPane.setViewportView(dimAssignmentTable);
        if (dimAssignmentTable.getColumnModel().getColumnCount() > 0) {
            dimAssignmentTable.getColumnModel().getColumn(1).setPreferredWidth(4);
            dimAssignmentTable.getColumnModel().getColumn(2).setPreferredWidth(4);
            dimAssignmentTable.getColumnModel().getColumn(3).setPreferredWidth(4);
            dimAssignmentTable.getColumnModel().getColumn(4).setPreferredWidth(4);
            dimAssignmentTable.getColumnModel().getColumn(5).setPreferredWidth(4);
            dimAssignmentTable.getColumnModel().getColumn(6).setPreferredWidth(4);
            dimAssignmentTable.getColumnModel().getColumn(7).setPreferredWidth(5);
        }

        plotButton.setText("Plot");
        plotButton.setActionCommand("");
        plotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plotButtonActionPerformed(evt);
            }
        });

        zdimLegendLabel.setText("<html>z1: Color Palette<br>z2: Point Size<br>z3: Transparency<br>z4: Labels");

        z4LayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        z4l1.setText("z4 subset");
        z4l1.setEnabled(false);

        z4cb1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        z4cb1.setEnabled(false);
        z4cb1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                z4cb1ItemStateChanged(evt);
            }
        });

        z4l2.setText("cardinality");
        z4l2.setEnabled(false);

        z4js.setEnabled(false);
        z4js.setValue(10);
        z4js.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                z4jsStateChanged(evt);
            }
        });

        z4l3.setText("based on variable");
        z4l3.setEnabled(false);

        z4cb2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        z4cb2.setEnabled(false);

        z4LayeredPane.setLayer(z4l1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        z4LayeredPane.setLayer(z4cb1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        z4LayeredPane.setLayer(z4l2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        z4LayeredPane.setLayer(z4js, javax.swing.JLayeredPane.DEFAULT_LAYER);
        z4LayeredPane.setLayer(z4l3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        z4LayeredPane.setLayer(z4cb2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout z4LayeredPaneLayout = new javax.swing.GroupLayout(z4LayeredPane);
        z4LayeredPane.setLayout(z4LayeredPaneLayout);
        z4LayeredPaneLayout.setHorizontalGroup(
            z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(z4LayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(z4l1)
                    .addComponent(z4cb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(z4LayeredPaneLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(z4l2))
                    .addGroup(z4LayeredPaneLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(z4js, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(z4LayeredPaneLayout.createSequentialGroup()
                        .addComponent(z4l3)
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addComponent(z4cb2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        z4LayeredPaneLayout.setVerticalGroup(
            z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(z4LayeredPaneLayout.createSequentialGroup()
                .addGroup(z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(z4l1)
                    .addComponent(z4l2)
                    .addComponent(z4l3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(z4LayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(z4cb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(z4js, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(z4cb2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dimPanelLayout = new javax.swing.GroupLayout(dimPanel);
        dimPanel.setLayout(dimPanelLayout);
        dimPanelLayout.setHorizontalGroup(
            dimPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dimPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dimPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dimPanelLayout.createSequentialGroup()
                        .addComponent(zdimLegendLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(z4LayeredPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(plotButton))
                    .addComponent(dimAssignmentScrollPane))
                .addContainerGap())
        );
        dimPanelLayout.setVerticalGroup(
            dimPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dimPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dimAssignmentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dimPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(plotButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(zdimLegendLabel)
                    .addComponent(z4LayeredPane, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        plotButton.getAccessibleContext().setAccessibleName("jButton1");

        jTabbedPane.addTab("Dimensions", dimPanel);

        axes_s.setSelected(true);
        axes_s.setText("Axes");
        axes_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                axes_sActionPerformed(evt);
            }
        });

        pSizeSlider.setMajorTickSpacing(10);
        pSizeSlider.setMinorTickSpacing(1);
        pSizeSlider.setPaintLabels(true);
        pSizeSlider.setPaintTicks(true);
        pSizeSlider.setValue(5);
        pSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pSizeSliderStateChanged(evt);
            }
        });

        pSizeLabel.setText("Point Size:");

        pTypeLabel.setText("Point Type:");

        pTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pTypeComboBoxItemStateChanged(evt);
            }
        });
        pTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pTypeComboBoxActionPerformed(evt);
            }
        });

        depthLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        fovLabel.setText("Field of View (FoV):");

        fovSlider.setMajorTickSpacing(15);
        fovSlider.setMaximum(115);
        fovSlider.setMinimum(3);
        fovSlider.setMinorTickSpacing(1);
        fovSlider.setPaintLabels(true);
        fovSlider.setPaintTicks(true);
        fovSlider.setValue(30);
        fovSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fovSliderStateChanged(evt);
            }
        });

        yaw_stuck.setSelected(true);
        yaw_stuck.setText("Frictionless Spin");
        yaw_stuck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yaw_stuckActionPerformed(evt);
            }
        });

        depthcolors.setText("Depth-Map Point Colors");
        depthcolors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depthcolorsActionPerformed(evt);
            }
        });

        fogLabel.setText("Fog:");

        fog_slider.setMajorTickSpacing(10);
        fog_slider.setPaintTicks(true);
        fog_slider.setSnapToTicks(true);
        fog_slider.setValue(0);
        fog_slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fog_sliderStateChanged(evt);
            }
        });

        shadowLabel.setText("Shadows:");
        shadowLabel.setToolTipText("");

        shadowx1.setText("xz");
        shadowx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shadowx1ActionPerformed(evt);
            }
        });

        shadowy1.setText("yz");
        shadowy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shadowy1ActionPerformed(evt);
            }
        });

        shadowz1.setText("xy");
        shadowz1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shadowz1ActionPerformed(evt);
            }
        });

        shadowTypeLabel.setText("Type:");

        shadow_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        shadow_type.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                shadow_typeItemStateChanged(evt);
            }
        });

        sOpacityLabel.setText("Opacity:");

        shadow_opacity.setMajorTickSpacing(10);
        shadow_opacity.setMaximum(95);
        shadow_opacity.setPaintTicks(true);
        shadow_opacity.setValue(33);
        shadow_opacity.setEnabled(false);
        shadow_opacity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                shadow_opacityStateChanged(evt);
            }
        });

        depthLayeredPane.setLayer(fovLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(fovSlider, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(yaw_stuck, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(depthcolors, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(fogLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(fog_slider, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadowLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadowx1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadowy1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadowz1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadowTypeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadow_type, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(sOpacityLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        depthLayeredPane.setLayer(shadow_opacity, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout depthLayeredPaneLayout = new javax.swing.GroupLayout(depthLayeredPane);
        depthLayeredPane.setLayout(depthLayeredPaneLayout);
        depthLayeredPaneLayout.setHorizontalGroup(
            depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                        .addComponent(fovLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fovSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                        .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, depthLayeredPaneLayout.createSequentialGroup()
                                .addComponent(shadowLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shadowx1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shadowy1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shadowz1)
                                .addGap(2, 2, 2)
                                .addComponent(shadowTypeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shadow_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                                .addComponent(yaw_stuck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(depthcolors)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fogLabel)))
                        .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(sOpacityLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(shadow_opacity, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fog_slider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        depthLayeredPaneLayout.setVerticalGroup(
            depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(fovSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(depthLayeredPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fovLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yaw_stuck)
                        .addComponent(depthcolors)
                        .addComponent(fogLabel))
                    .addComponent(fog_slider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(depthLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(shadowLabel)
                        .addComponent(shadowx1)
                        .addComponent(shadowy1)
                        .addComponent(shadowz1)
                        .addComponent(shadowTypeLabel)
                        .addComponent(shadow_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sOpacityLabel))
                    .addComponent(shadow_opacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        depthPercLabel.setText("Depth Perception");

        labelsLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        xlabel.setText("x label");
        xlabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                xlabelFocusLost(evt);
            }
        });
        xlabel.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                xlabelInputMethodTextChanged(evt);
            }
        });
        xlabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xlabelActionPerformed(evt);
            }
        });
        xlabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                xlabelKeyTyped(evt);
            }
        });

        ylabel.setText("y label");
        ylabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ylabelFocusLost(evt);
            }
        });
        ylabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ylabelKeyTyped(evt);
            }
        });

        zlabel.setText("z label");
        zlabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                zlabelFocusLost(evt);
            }
        });
        zlabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                zlabelKeyTyped(evt);
            }
        });

        xlabel_d.setValue(15);
        xlabel_d.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xlabel_dStateChanged(evt);
            }
        });
        xlabel_d.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                xlabel_dPropertyChange(evt);
            }
        });

        jLabel11.setText("Dist. %");

        ylabel_d.setEnabled(false);
        ylabel_d.setValue(15);
        ylabel_d.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ylabel_dStateChanged(evt);
            }
        });

        jLabel12.setText("Labels");

        jLabel13.setText("x:");

        jLabel14.setText("y:");

        jLabel15.setText("z:");

        xlabel_s.setSelected(true);
        xlabel_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xlabel_sActionPerformed(evt);
            }
        });

        ylabel_s.setSelected(true);
        ylabel_s.setEnabled(false);
        ylabel_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ylabel_sActionPerformed(evt);
            }
        });

        jLabel16.setText("Horiz. Show");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel17.setText("Ticks Horiz. Show");

        xtic_d.setValue(5);
        xtic_d.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xtic_dStateChanged(evt);
            }
        });

        ytic_d.setEnabled(false);
        ytic_d.setValue(5);
        ytic_d.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ytic_dStateChanged(evt);
            }
        });

        ztic_d.setEnabled(false);
        ztic_d.setValue(5);
        ztic_d.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ztic_dStateChanged(evt);
            }
        });

        xtic_s.setSelected(true);
        xtic_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xtic_sActionPerformed(evt);
            }
        });

        ytic_s.setSelected(true);
        ytic_s.setEnabled(false);
        ytic_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ytic_sActionPerformed(evt);
            }
        });

        jLabel18.setText("Dist. %");

        zlabel_d.setEnabled(false);
        zlabel_d.setValue(15);
        zlabel_d.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zlabel_dStateChanged(evt);
            }
        });

        zlabel_s.setSelected(true);
        zlabel_s.setEnabled(false);
        zlabel_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zlabel_sActionPerformed(evt);
            }
        });

        labels_sync.setSelected(true);
        labels_sync.setText("Sync.");
        labels_sync.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labels_syncActionPerformed(evt);
            }
        });

        xtic_h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xtic_hActionPerformed(evt);
            }
        });

        ytic_h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ytic_hActionPerformed(evt);
            }
        });

        ztic_s.setSelected(true);
        ztic_s.setEnabled(false);
        ztic_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ztic_sActionPerformed(evt);
            }
        });

        ztic_h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ztic_hActionPerformed(evt);
            }
        });

        tics_sync.setSelected(true);
        tics_sync.setText("Sync.");
        tics_sync.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tics_syncActionPerformed(evt);
            }
        });

        zlabel_h.setSelected(true);
        zlabel_h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zlabel_hActionPerformed(evt);
            }
        });

        jLabel29.setText("Size:");

        lsize.setValue(12);
        lsize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lsizeStateChanged(evt);
            }
        });

        z4l4.setText("z4:");
        z4l4.setEnabled(false);

        z4cb3.setEnabled(false);
        z4cb3.setValue(10);
        z4cb3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                z4cb3StateChanged(evt);
            }
        });

        labelsLayeredPane.setLayer(xlabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ylabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(zlabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(xlabel_d, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ylabel_d, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel12, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel13, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel14, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel15, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(xlabel_s, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ylabel_s, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel16, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jSeparator2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel17, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(xtic_d, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ytic_d, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ztic_d, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(xtic_s, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ytic_s, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel18, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(zlabel_d, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(zlabel_s, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(labels_sync, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(xtic_h, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ytic_h, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ztic_s, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(ztic_h, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(tics_sync, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(zlabel_h, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(jLabel29, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(lsize, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(z4l4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        labelsLayeredPane.setLayer(z4cb3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout labelsLayeredPaneLayout = new javax.swing.GroupLayout(labelsLayeredPane);
        labelsLayeredPane.setLayout(labelsLayeredPaneLayout);
        labelsLayeredPaneLayout.setHorizontalGroup(
            labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, labelsLayeredPaneLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(xlabel)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, labelsLayeredPaneLayout.createSequentialGroup()
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ylabel)
                                    .addComponent(zlabel))))
                        .addGap(16, 16, 16))
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lsize, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(z4l4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(z4cb3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                .addComponent(ylabel_s)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ylabel_d, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                .addComponent(xlabel_s)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(xlabel_d, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labels_sync)
                            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                .addComponent(zlabel_h)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zlabel_s)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zlabel_d, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)))
                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18))
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelsLayeredPaneLayout.createSequentialGroup()
                                    .addComponent(xtic_h)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(xtic_s)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(xtic_d, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelsLayeredPaneLayout.createSequentialGroup()
                                    .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                            .addComponent(ztic_h)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ztic_s))
                                        .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                            .addComponent(ytic_h)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ytic_s)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ytic_d, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ztic_d, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(tics_sync))))
                .addContainerGap())
        );
        labelsLayeredPaneLayout.setVerticalGroup(
            labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel12))
                    .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel17)
                        .addComponent(jLabel11)))
                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ztic_h)
                            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(xtic_d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(xtic_s))
                                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ytic_d, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(ytic_h)
                                                    .addComponent(ytic_s)))))
                                    .addComponent(xtic_h))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ztic_d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ztic_s))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tics_sync))
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(labelsLayeredPaneLayout.createSequentialGroup()
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(xlabel_d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(xlabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel13))
                                    .addComponent(xlabel_s))
                                .addGap(9, 9, 9)
                                .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(ylabel_d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ylabel_s)))
                            .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ylabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14)))
                        .addGap(9, 9, 9)
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(zlabel_d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(zlabel_s)
                            .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(zlabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15))
                            .addComponent(zlabel_h))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labels_sync)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, labelsLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lsize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29)
                                .addComponent(z4l4)
                                .addComponent(z4cb3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        axesm_s.setSelected(true);
        axesm_s.setText("Axis Mirrors");
        axesm_s.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                axesm_sActionPerformed(evt);
            }
        });

        tics_marks.setSelected(true);
        tics_marks.setText("Tick Marks");
        tics_marks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tics_marksActionPerformed(evt);
            }
        });

        showLabel.setText("Show:");

        gridLabel.setText("Grid:");

        gridx.setText("x");
        gridx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridxActionPerformed(evt);
            }
        });

        gridy.setText("y");
        gridy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridyActionPerformed(evt);
            }
        });

        gridz.setText("z");
        gridz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gridzActionPerformed(evt);
            }
        });

        gridTypeLabel.setText("Type:");

        grid_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        grid_type.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                grid_typeItemStateChanged(evt);
            }
        });

        opacityLabel.setText("Opacity:");

        grid_opacity.setMajorTickSpacing(10);
        grid_opacity.setMaximum(90);
        grid_opacity.setPaintTicks(true);
        grid_opacity.setValue(33);
        grid_opacity.setEnabled(false);
        grid_opacity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                grid_opacityStateChanged(evt);
            }
        });

        labels_rgb.setSelected(true);
        labels_rgb.setText("RGB");
        labels_rgb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labels_rgbActionPerformed(evt);
            }
        });

        label_border.setText("Label Borders");
        label_border.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                label_borderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(depthLayeredPane)
                    .addComponent(labelsLayeredPane)
                    .addGroup(viewPanelLayout.createSequentialGroup()
                        .addComponent(pSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pSizeSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pTypeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(viewPanelLayout.createSequentialGroup()
                        .addComponent(gridLabel)
                        .addGap(18, 18, 18)
                        .addComponent(gridx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gridy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(gridz)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gridTypeLabel)
                        .addGap(6, 6, 6)
                        .addComponent(grid_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(opacityLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(grid_opacity, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(viewPanelLayout.createSequentialGroup()
                        .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(depthPercLabel)
                            .addGroup(viewPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(showLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(axes_s)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labels_rgb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(axesm_s)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tics_marks)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(label_border)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pSizeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pTypeLabel))
                    .addComponent(pSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(axes_s)
                        .addComponent(labels_rgb))
                    .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(showLabel)
                        .addComponent(tics_marks)
                        .addComponent(axesm_s)
                        .addComponent(label_border)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gridLabel)
                        .addComponent(gridx)
                        .addComponent(gridy)
                        .addComponent(gridz)
                        .addComponent(gridTypeLabel)
                        .addComponent(grid_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(opacityLabel))
                    .addComponent(grid_opacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelsLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(depthPercLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(depthLayeredPane)
                .addContainerGap())
        );

        jTabbedPane.addTab("View", viewPanel);

        viewerOptPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        dataViewerLoadButton.setText("Load");
        dataViewerLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataViewerLoadButtonActionPerformed(evt);
            }
        });

        viewRecRadioButton.setSelected(true);
        viewRecRadioButton.setText("Records");

        viewPointsRadioButton.setText("Plotted Points");

        javax.swing.GroupLayout viewerOptPanelLayout = new javax.swing.GroupLayout(viewerOptPanel);
        viewerOptPanel.setLayout(viewerOptPanelLayout);
        viewerOptPanelLayout.setHorizontalGroup(
            viewerOptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewerOptPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewRecRadioButton)
                .addGap(18, 18, 18)
                .addComponent(viewPointsRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dataViewerLoadButton)
                .addContainerGap())
        );
        viewerOptPanelLayout.setVerticalGroup(
            viewerOptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewerOptPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewerOptPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataViewerLoadButton)
                    .addComponent(viewRecRadioButton)
                    .addComponent(viewPointsRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataViewerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        dataViewerTable.setShowGrid(false);
        dataViewerPane.setViewportView(dataViewerTable);

        javax.swing.GroupLayout viewerPanelLayout = new javax.swing.GroupLayout(viewerPanel);
        viewerPanel.setLayout(viewerPanelLayout);
        viewerPanelLayout.setHorizontalGroup(
            viewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dataViewerPane, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addComponent(viewerOptPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        viewerPanelLayout.setVerticalGroup(
            viewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewerOptPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dataViewerPane, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Data Viewer", viewerPanel);

        imageLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        resLabel.setText("Resolution:");

        resTimesLabel.setText("x");

        ratioLabel.setText("Ratio:");

        ratiosLayeredPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        button_group_ratios.add(ratio1);
        ratio1.setText("1:1");
        ratio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratio1ActionPerformed(evt);
            }
        });

        button_group_ratios.add(ratio2);
        ratio2.setText("4:3");
        ratio2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratio2ActionPerformed(evt);
            }
        });

        button_group_ratios.add(ratio3);
        ratio3.setText("16:10");
        ratio3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratio3ActionPerformed(evt);
            }
        });

        button_group_ratios.add(ratio4);
        ratio4.setSelected(true);
        ratio4.setText("φ");
        ratio4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratio4ActionPerformed(evt);
            }
        });

        button_group_ratios.add(ratio5);
        ratio5.setText("16:9");
        ratio5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ratio5ActionPerformed(evt);
            }
        });

        button_group_ratios.add(ratio6);
        ratio6.setText("Custom");

        ratiosLayeredPane.setLayer(ratio1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        ratiosLayeredPane.setLayer(ratio2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        ratiosLayeredPane.setLayer(ratio3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        ratiosLayeredPane.setLayer(ratio4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        ratiosLayeredPane.setLayer(ratio5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        ratiosLayeredPane.setLayer(ratio6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout ratiosLayeredPaneLayout = new javax.swing.GroupLayout(ratiosLayeredPane);
        ratiosLayeredPane.setLayout(ratiosLayeredPaneLayout);
        ratiosLayeredPaneLayout.setHorizontalGroup(
            ratiosLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ratiosLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ratio1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ratio2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ratio3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ratio4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ratio5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ratio6)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        ratiosLayeredPaneLayout.setVerticalGroup(
            ratiosLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ratiosLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ratio1)
                .addComponent(ratio2)
                .addComponent(ratio3)
                .addComponent(ratio4)
                .addComponent(ratio5)
                .addComponent(ratio6))
        );

        imgExportButton.setText("Export");
        imgExportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imgExportButtonActionPerformed(evt);
            }
        });

        picYdimSpinner.setValue(1200);
        picYdimSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                picYdimSpinnerStateChanged(evt);
            }
        });

        picXdimSpinner.setValue(1942);
        picXdimSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                picXdimSpinnerStateChanged(evt);
            }
        });

        imageLayeredPane.setLayer(resLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLayeredPane.setLayer(resTimesLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLayeredPane.setLayer(ratioLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLayeredPane.setLayer(ratiosLayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLayeredPane.setLayer(imgExportButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLayeredPane.setLayer(picYdimSpinner, javax.swing.JLayeredPane.DEFAULT_LAYER);
        imageLayeredPane.setLayer(picXdimSpinner, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout imageLayeredPaneLayout = new javax.swing.GroupLayout(imageLayeredPane);
        imageLayeredPane.setLayout(imageLayeredPaneLayout);
        imageLayeredPaneLayout.setHorizontalGroup(
            imageLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imageLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imageLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, imageLayeredPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(imgExportButton))
                    .addGroup(imageLayeredPaneLayout.createSequentialGroup()
                        .addComponent(resLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(picXdimSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resTimesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(picYdimSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(imageLayeredPaneLayout.createSequentialGroup()
                        .addComponent(ratioLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ratiosLayeredPane)))
                .addContainerGap())
        );
        imageLayeredPaneLayout.setVerticalGroup(
            imageLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imageLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imageLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resLabel)
                    .addComponent(resTimesLabel)
                    .addComponent(picYdimSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(picXdimSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(imageLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ratiosLayeredPane)
                    .addComponent(ratioLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imgExportButton)
                .addContainerGap())
        );

        imgExportButton.getAccessibleContext().setAccessibleName("");
        imgExportButton.getAccessibleContext().setAccessibleDescription("");

        imageLabel.setText("Image");

        javax.swing.GroupLayout exportPanelLayout = new javax.swing.GroupLayout(exportPanel);
        exportPanel.setLayout(exportPanelLayout);
        exportPanelLayout.setHorizontalGroup(
            exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageLayeredPane)
                    .addGroup(exportPanelLayout.createSequentialGroup()
                        .addComponent(imageLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        exportPanelLayout.setVerticalGroup(
            exportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(304, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Export", exportPanel);

        aboutLabel.setBackground(new java.awt.Color(255, 255, 255));
        aboutLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        aboutLabel.setText("<html>Author: Philippos Papaphilippou<br>Website: www.philippos.info<br>Copyright: 2024");
        aboutLabel.setOpaque(true);

        licenseTextArea.setEditable(false);
        licenseTextArea.setColumns(20);
        licenseTextArea.setLineWrap(true);
        licenseTextArea.setRows(5);
        licenseTextArea.setText("Juniper\n\n     Copyright (C) 2024  Philippos Papaphilippou\n\n     This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.\n\n     This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.\n\n     You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.\n");
        licenseTextArea.setWrapStyleWord(true);
        licenseScrollPane.setViewportView(licenseTextArea);

        javax.swing.GroupLayout aboutPanelLayout = new javax.swing.GroupLayout(aboutPanel);
        aboutPanel.setLayout(aboutPanelLayout);
        aboutPanelLayout.setHorizontalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aboutLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                    .addComponent(licenseScrollPane))
                .addContainerGap())
        );
        aboutPanelLayout.setVerticalGroup(
            aboutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aboutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(licenseScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("About", aboutPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane)
                .addContainerGap())
        );

        jTabbedPane.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Invoke plot() in Juniper class (in a separate thread).
     */
    private void plotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotButtonActionPerformed
        if (parent.draw == false)
            
            // See https://stackoverflow.com/questions/21083945/how-to-avoid-not-on-fx-application-thread-currentthread-javafx-application-th
            Platform.runLater(new Runnable() { 
                @Override
                public void run() {
                    parent.plot();
                    label_borderActionPerformed(evt);
                    
                    // Apply tick rotation, since this is stored per newly-generated tick
                    rotate_tics(xtic_h.isSelected(),0); 
                    rotate_tics(ytic_h.isSelected(),2);
                    rotate_tics(ztic_h.isSelected(),1);               
                }
            });        
    }//GEN-LAST:event_plotButtonActionPerformed

    /**
     * Update point size.
     */
    private void pSizeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pSizeSliderStateChanged
        parent.pointsize = pSizeSlider.getValue();
        
        // Replot points after there is a point size change (as it also impacts projections etc.)
        plotButtonActionPerformed(null);
    }//GEN-LAST:event_pSizeSliderStateChanged
    
    /**
     * Keep track of the selected checkboxes inside the dimension assignment table.
     */
    private void dimAssignmentTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dimAssignmentTablePropertyChange
        int current_col = ((JTable)evt.getSource()).getEditingColumn();
        int current_row = ((JTable)evt.getSource()).getEditingRow();
        
        if (evt.getOldValue()==null || (current_col==parent.dimensions+1)) 
            return;
        
        // Radio-button behaviour for checkboxes (no multiple variables for 1 dimension)
        for (int i=0; i<variableNames.length; i++){
            if (i!=current_row){
                dimAssignmentTable.getModel().setValueAt(false, i, ((JTable)evt.getSource()).getEditingColumn());
            }            
        }
        
        // Unfreeze/freeze label dimension pane components according to z4 assignment.
        if (current_col==7){
            boolean z4_show = (boolean)(dimAssignmentTable.getModel().getValueAt(current_row, current_col));
            boolean z4_card_applicable = z4cb1.getSelectedIndex()!=2;
            z4l1.setEnabled(z4_show); z4l2.setEnabled(z4_show&&z4_card_applicable); z4l3.setEnabled(z4_show&&z4_card_applicable);
            z4cb1.setEnabled(z4_show); z4js.setEnabled(z4_show&&z4_card_applicable); z4cb2.setEnabled(z4_show&&z4_card_applicable);
            z4l4.setEnabled(z4_show); z4cb3.setEnabled(z4_show);
        }
    }//GEN-LAST:event_dimAssignmentTablePropertyChange

    /**
     * Uses the assignment information to update axis labels and summarise the 
     * selection.
     * 
     * @return a list summarising the dimension assignment information with integers. 
     */
    public int[] variable_selection(){
        int[] t = new int[parent.dimensions];
        
        parent.xlabel.setText3D("");
        parent.ylabel.setText3D("");
        parent.zlabel.setText3D("");
        
        // Update axis labels according to the assigned variables per axis 
        for (int j=0; j<parent.dimensions; j++){        
            t[j]=-1; // leave assignment value to -1, if no ckeckbox is selected for this dimension
                        
            for (int i=0; i<variableNames.length; i++){
                if (dimAssignmentTable.getModel().getValueAt(i, j+1)!=null &&
                    dimAssignmentTable.getModel().getValueAt(i, j+1).equals(true)){
                    t[j] = i;
                    System.out.println(variableNames[i]);
                    
                    // Update the groups of the corresponding axis labels with the appropriate
                    // text and space translations
                    switch(j){
                        case 0: parent.xlabel.setText3D(variableNames[i]); xlabel.setText(variableNames[i]);
                                parent.xlabel.getParent().setTranslateY(- parent.AXIS_LENGTH*parent.xlabel_dis);
                                parent.xlabel.getParent().setTranslateZ(- parent.AXIS_LENGTH*parent.xlabel_dis);   
                            break;
                        case 1: parent.ylabel.setText3D(variableNames[i]); ylabel.setText(variableNames[i]);
                                parent.ylabel.getParent().setTranslateY(- parent.AXIS_LENGTH*parent.ylabel_dis);
                                parent.ylabel.getParent().setTranslateX(- parent.AXIS_LENGTH*parent.ylabel_dis);
                            break;                    
                        case 2: parent.zlabel.setText3D(variableNames[i]); zlabel.setText(variableNames[i]);
                                parent.zlabel.getParent().setTranslateZ(- parent.AXIS_LENGTH*parent.zlabel_dis);
                                parent.zlabel.getParent().setTranslateX(- parent.AXIS_LENGTH*parent.zlabel_dis);
                            break;
                    }                    
                }
            }            
        }
        
        // Recolor axis labels according to the RGB option
        if (labels_rgb.isSelected()){
            parent.xlabel.setTextureModeNone(javafx.scene.paint.Color.DARKRED);
            parent.ylabel.setTextureModeNone(javafx.scene.paint.Color.DARKBLUE);
            parent.zlabel.setTextureModeNone(javafx.scene.paint.Color.DARKGREEN);
        } else {
            parent.xlabel.setTextureModeNone(javafx.scene.paint.Color.BLACK);
            parent.ylabel.setTextureModeNone(javafx.scene.paint.Color.BLACK);
            parent.zlabel.setTextureModeNone(javafx.scene.paint.Color.BLACK);
        }
        
        // Recenter labels per axis
        parent.xlabel.setTranslateX(-parent.xlabel.getLayoutBounds().getCenterX());
        parent.xlabel.setTranslateY(-parent.xlabel.getLayoutBounds().getCenterY());        
        parent.ylabel.setTranslateX(-parent.ylabel.getLayoutBounds().getCenterX());
        parent.ylabel.setTranslateY(-parent.ylabel.getLayoutBounds().getCenterY());
        parent.zlabel.setTranslateX(-parent.zlabel.getLayoutBounds().getCenterX());
        parent.zlabel.setTranslateY(-parent.zlabel.getLayoutBounds().getCenterY());
        
        // Similarly, adjust borders
        Rectangle r; double height, width; 
        r =  parent.xlabel_b;
        height = parent.xlabel.getLayoutBounds().getHeight();
        width = parent.xlabel.getLayoutBounds().getWidth();
        r.setHeight(height); r.setWidth(width); r.setTranslateX(-width/2); r.setTranslateY(-height/2);
        
        r = parent.ylabel_b; 
        height = parent.ylabel.getLayoutBounds().getHeight();
        width = parent.ylabel.getLayoutBounds().getWidth();
        r.setHeight(height); r.setWidth(width); r.setTranslateX(-width/2); r.setTranslateY(-height/2);
        
        r = parent.zlabel_b; 
        height = parent.zlabel.getLayoutBounds().getHeight();
        width = parent.zlabel.getLayoutBounds().getWidth();
        r.setHeight(height); r.setWidth(width); r.setTranslateX(-width/2); r.setTranslateY(-height/2);        
        return t;
    }
    
    /**
     * Uses the assignment information to summarise the selection (similar to 
     * variable_selection() but without label modification).
     * 
     * @return a list summarising the dimension assignment information with integers. 
     */
    public int[] variable_selection_light(){
        int[] t = new int[parent.dimensions];
               
        for (int j=0; j<parent.dimensions; j++){        
            t[j]=-1;
            for (int i=0; i<variableNames.length; i++){
                if (dimAssignmentTable.getModel().getValueAt(i, j+1)!=null &&
                    dimAssignmentTable.getModel().getValueAt(i, j+1).equals(true)){
                    t[j] = i;
                }
            }            
        } 
        return t;
    }
    
    /**
     * Summarises the logscale options to be per dimension instead of per variable
     * 
     * @return boolean list for the logscale options
     */
    public boolean[] logscale_selection(){
        boolean[] t = new boolean[parent.dimensions];
        for (int j=0; j<parent.dimensions; j++){        
            t[j]=false;
            for (int i=0; i<variableNames.length; i++){
                if (dimAssignmentTable.getModel().getValueAt(i, j+1)!=null &&
                    dimAssignmentTable.getModel().getValueAt(i, j+1).equals(true) && dimAssignmentTable.getModel().getValueAt(i, parent.dimensions+1)!=null){
                    t[j] = dimAssignmentTable.getModel().getValueAt(i, parent.dimensions+1).equals(true);
                    System.out.println(dimAssignmentTable.getModel().getValueAt(i, parent.dimensions+1)+" "+ (parent.dimensions+1)+" "+(i));
                }
            }            
        }
        return t; 
    } 

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);// TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    /**
     * Update point-type based on combo box (with initialisation-aware workaround).
     */
    private void pTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pTypeComboBoxActionPerformed
        try{
            parent.pointtype = pTypeComboBox.getSelectedIndex();
        } catch (Exception e){
        }
    }//GEN-LAST:event_pTypeComboBoxActionPerformed

    private void fsepComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fsepComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fsepComboBoxActionPerformed
    
    /**
     * Parse dataset file contents into memory.
     */
    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        // Invoke Java file chooser
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(this);
        
        String separator = "";
        switch (fsepComboBox.getSelectedIndex()){
            case 4:
            case 0:
                separator = " ";
                break;
            case 1:
                separator = "\t";
                break;
            case 2:
                separator = ",";
                break;
            case 3:
                separator = ";";
                break;
        }
        
        datasetf = fc.getSelectedFile();
        dataset.clear();
        datasetStringed.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(datasetf))) {
            String line;
            int counter = 0;            
            
            // Read file line by line
            while ((line = br.readLine()) != null) {
                String[] components = line.split(separator);
                if (fsepComboBox.getSelectedIndex()==4) components = line.replace("\t", separator).split(separator);
                if (line.trim().length() == 0)
                continue;

                // Use the first line to optionally become the variable names
                if (counter == 0){
                    if (namesFromLineCheckBox.isSelected()) {
                        variableNames = components;
                        for (int i = 0; i < components.length; i++) {
                            stringtypes.add(new HashMap<String, Integer>());
                        }
                    } else {
                        String[] names = new String[components.length];
                        for (int i=0; i<components.length; i++){
                            names[i] = "Parameter " + i;
                        }
                        variableNames = names;
                        for (int i = 0; i < components.length; i++) {
                            stringtypes.add(new HashMap<String, Integer>());
                        }                        
                    }
                    z4cb2.removeAllItems();
                    for (int i = 0; i < components.length; i++){ 
                        z4cb2.addItem(variableNames[i]);
                    }
                } 
                
                // For all other lines
                if (counter > 0 || !namesFromLineCheckBox.isSelected()){
                    double[] fcomp = new double[components.length];
                    for (int i = 0; i < fcomp.length; i++) {
                        String in = components[i].replace(",", "");
                        try {
                            fcomp[i] = Double.parseDouble(in); // Parse the values as doubles
                        } catch (NumberFormatException e) {
                            //if (stringtypes.size()>i){   (only if there is a mismatch between line columns, e.g. 1st line of tab-spaced)
                            if (in.length()==0){
                                fcomp[i] = Double.NaN;
                            } else{ 
                                // But if it has string, use string appearance number as value instead
                                if (stringtypes.get(i).containsKey(in)) {
                                    fcomp[i] = stringtypes.get(i).get(in);
                                } else {
                                    stringtypes.get(i).put(in, stringtypes.get(i).size());
                                    fcomp[i] = stringtypes.get(i).get(in);
                                }
                            }//}
                        }
                    }
                    dataset.add(fcomp);
                    datasetStringed.add(components);
                }
                counter++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: display parsing error messages in the GUI
        }
        
        // Populate assignment table
        for (int i=dimAssignmentTable.getModel().getRowCount()-1; i>=0; i--)
            ((DefaultTableModel)dimAssignmentTable.getModel()).removeRow(i);
        for (String var: variableNames){
            ((DefaultTableModel)dimAssignmentTable.getModel()).addRow(new Object[]{var, false, false, false, false, false, null});
        }
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_loadButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed
    
    /**
     * Point type modification also replots the figure instantly. 
     */
    private void pTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pTypeComboBoxItemStateChanged
        if (parent!=null) { // (early initialisation workaround)
            plotButtonActionPerformed(null);
            boolean allow_shadows = pTypeComboBox.getSelectedIndex()!=2;            
            shadowx1.setEnabled(allow_shadows); shadowy1.setEnabled(allow_shadows); shadowz1.setEnabled(allow_shadows);
            boolean preselected = shadowx1.isSelected() || shadowy1.isSelected() || shadowz1.isSelected();
            shadow_type.setEnabled(allow_shadows);
            shadow_opacity.setEnabled(allow_shadows && preselected);
        }
    }//GEN-LAST:event_pTypeComboBoxItemStateChanged

    /**
     * Loads the dataset into the data viewer table, in the "Viewer" tab. 
     */
    private void dataViewerLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataViewerLoadButtonActionPerformed
        
        // Record-based table (same as parsing the .csv into a spreadsheet)
        if (button_group_viewer_sources.getElements().nextElement().isSelected()) {

            Class[] types = new Class[variableNames.length];
            java.util.Arrays.fill(types, java.lang.String.class);
            Object[][] data = new Object[datasetStringed.size()][variableNames.length];

            for (int i = 0; i < datasetStringed.size(); i++) {
                for (int j = 0; j < variableNames.length; j++) {
                    data[i][j] = datasetStringed.get(i)[j];
                }
            }

            // Inline model instantiation for the table 
            dataViewerTable.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    variableNames
            ) {
                boolean[] canEdit = new boolean[variableNames.length];

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
            dataViewerTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            dataViewerTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                }
            });
            
            for (int i=0; i<variableNames.length; i++){
                dataViewerTable.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(dimAssignmentTable.getTableHeader().getColumnModel().getColumn(0).getHeaderRenderer());
            }
        
        // Only view the plotted points/dimensions
        } else {

            Class[] types = new Class[parent.dimensions];
            String[] names = new String[]{
                "x", "y", "z", "z1", "z2", "z3", "z4"
            };
            java.util.Arrays.fill(types, java.lang.Double.class);

            int count = 0;
            Object[][] array = new Object[dataset.size()][parent.dimensions];

            int[] indexes = variable_selection_light();
            
            for (double[] values : dataset) {

                for (int i = 0; i < parent.dimensions; i += 1) {
                    if (indexes[i] == -1) {
                        array[count][i] = 0;
                    } else {
                        array[count][i] = values[indexes[i]];
                    }
                }
                count++;
            }

            // Inline model instantiation for the table 
            dataViewerTable.setModel(new javax.swing.table.DefaultTableModel(
                    array,
                    names
            ) {

                boolean[] canEdit = new boolean[parent.dimensions];

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
            dataViewerTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
            dataViewerTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                }
            });
            
        dataViewerTable.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(dimAssignmentTable.getTableHeader().getColumnModel().getColumn(1).getHeaderRenderer());
        dataViewerTable.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(dimAssignmentTable.getTableHeader().getColumnModel().getColumn(2).getHeaderRenderer());
        dataViewerTable.getTableHeader().getColumnModel().getColumn(2).setHeaderRenderer(dimAssignmentTable.getTableHeader().getColumnModel().getColumn(3).getHeaderRenderer());
        }
    }//GEN-LAST:event_dataViewerLoadButtonActionPerformed

    /**
     * Use the Java file chooser to export a screenshot. 
     */
    private void imgExportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imgExportButtonActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        fc.setSelectedFile(new File("image_"+timeStamp+".png"));
        int returnVal = fc.showSaveDialog(this);
        if (returnVal==0){
            screenshotf = fc.getSelectedFile();            
            // The final image will be handled by the Juniper thread
            parent.screenshot = true;
        }
    }//GEN-LAST:event_imgExportButtonActionPerformed

    /**
     * Parse dataset file from clipped contents.
     * 
     * @see #loadButtonActionPerformed() same but from a file
     */
    private void pasteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteButtonActionPerformed
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        try {
            String result = (String) clipboard.getData(DataFlavor.stringFlavor);

            String separator = "";
            switch (fsepComboBox.getSelectedIndex()) {
                case 4:
                case 0:
                    separator = " ";
                    break;
                case 1:
                    separator = "\t";
                    break;
                case 2:
                    separator = ",";
                    break;
                case 3:
                    separator = ";";
                    break;                    
            }

            dataset.clear();
            datasetStringed.clear();
            try (BufferedReader br = new BufferedReader(new StringReader(result))) {
                String line;
                int counter = 0;

                while ((line = br.readLine()) != null) {
                    String[] components = line.split(separator);
                    if (fsepComboBox.getSelectedIndex()==4) components = line.replace("\t", separator).split(separator);
                    
                    if (line.trim().length() == 0) {
                        continue;
                    }

                    if (counter == 0) {
                        if (namesFromLineCheckBox.isSelected()) {
                            variableNames = components;
                            for (int i = 0; i < components.length; i++) {
                                stringtypes.add(new HashMap<String, Integer>());
                            }
                        } else {
                            String[] names = new String[components.length];
                            for (int i = 0; i < components.length; i++) {
                                names[i] = "Parameter " + i;
                            }
                            variableNames = names;
                            for (int i = 0; i < components.length; i++) {
                                stringtypes.add(new HashMap<String, Integer>());
                            }
                        }
                        z4cb2.removeAllItems();
                        for (int i = 0; i < components.length; i++){ 
                            z4cb2.addItem(variableNames[i]);
                        }
                    }
                    if (counter > 0 || !namesFromLineCheckBox.isSelected()) {
                        double[] fcomp = new double[components.length];
                        for (int i = 0; i < fcomp.length; i++) {
                            String in = components[i].replace(",", "");
                            try {
                                fcomp[i] = Double.parseDouble(in);
                            } catch (NumberFormatException e) {
                                if (in.length()==0){
                                    fcomp[i] = Double.NaN;
                                } else{
                                    if (stringtypes.get(i).containsKey(in)) {
                                        fcomp[i] = stringtypes.get(i).get(in);
                                    } else {
                                        stringtypes.get(i).put(in, stringtypes.get(i).size());
                                        fcomp[i] = stringtypes.get(i).get(in);
                                    }
                                }
                            }
                        }
                        dataset.add(fcomp);
                        datasetStringed.add(components);
                    }
                    counter++;
                }
            } catch (IOException ex) {
                Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = dimAssignmentTable.getModel().getRowCount() - 1; i >= 0; i--) {
                ((DefaultTableModel) dimAssignmentTable.getModel()).removeRow(i);
            }
            for (String var : variableNames) {
                ((DefaultTableModel) dimAssignmentTable.getModel()).addRow(new Object[]{var, false, false, false, false, false, false, false/*null*/});
            }

            jTabbedPane.setSelectedIndex(1);

        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_pasteButtonActionPerformed

    /**
     * Changes FoV based on the slider, with a thread workaround for seeing the 
     * new FoV immediately in the plot.
     */
    private void fovSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fovSliderStateChanged
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            parent.camera.setFieldOfView(fovSlider.getValue());
            parent.CAMERA_DISTANCE=-100+parent.CAMERA_INITIAL_DISTANCE/(2*Math.tan(fovSlider.getValue()/360.0*(2*Math.PI)));
            parent.camera.setTranslateZ(parent.CAMERA_DISTANCE);
            
            }
        });       
        System.out.println("FoV: "+fovSlider.getValue());
    }//GEN-LAST:event_fovSliderStateChanged

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void axes_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_axes_sActionPerformed
        boolean selected = axes_s.isSelected();
        parent.axisGroup.setVisible(selected);
        parent.axismGroup.setVisible(axesm_s.isSelected());
        if (!selected)
            parent.axismGroup.setVisible(false);
        axesm_s.setEnabled(axes_s.isSelected());
    }//GEN-LAST:event_axes_sActionPerformed

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void ylabel_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ylabel_sActionPerformed
        parent.ylabel.setVisible(ylabel_s.isSelected()); 
        parent.ylabel_b.setVisible(label_border.isSelected() && ylabel_s.isSelected());  
    }//GEN-LAST:event_ylabel_sActionPerformed

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void xtic_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xtic_sActionPerformed
        boolean selected = xtic_s.isSelected();
        parent.ticsGroups[0].setVisible(selected);
        for (Object tic : parent.tics[0].keySet()){
            Node n = ((Group) ((Pair)parent.tics[0].get(tic)).getValue()).getChildren().getLast();
            n.setVisible(selected&&label_border.isSelected());
        }  
        if (tics_sync.isSelected()){
            ytic_s.setSelected(selected);
            ztic_s.setSelected(selected);
            parent.ticsGroups[1].setVisible(selected);
            parent.ticsGroups[2].setVisible(selected);
            for (Object tic : parent.tics[1].keySet()){
                Node n = ((Group) ((Pair)parent.tics[1].get(tic)).getValue()).getChildren().getLast();
                n.setVisible(selected&&label_border.isSelected());
            } 
            for (Object tic : parent.tics[2].keySet()){
                Node n = ((Group) ((Pair)parent.tics[2].get(tic)).getValue()).getChildren().getLast();
                n.setVisible(selected&&label_border.isSelected());
            } 
        }
        
    }//GEN-LAST:event_xtic_sActionPerformed

    /**
     * Checkbox for infinite rotation.
     */
    private void yaw_stuckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yaw_stuckActionPerformed
        parent.deceleration = yaw_stuck.isSelected()?1:0;
    }//GEN-LAST:event_yaw_stuckActionPerformed

    /**
     * Checkbox logic for ticks is handled by a common function call.
     */
    private void xtic_hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xtic_hActionPerformed
        rotate_tics(xtic_h.isSelected(),0);
    }//GEN-LAST:event_xtic_hActionPerformed
    
    /**
     * Checkbox logic for ticks is handled by a common function call.
     */
    private void ytic_hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ytic_hActionPerformed
        rotate_tics(ytic_h.isSelected(),2);
    }//GEN-LAST:event_ytic_hActionPerformed

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void labels_syncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labels_syncActionPerformed
        if (labels_sync.isSelected()){
            ylabel_d.setEnabled(false); ylabel_d.setValue(xlabel_d.getValue());
            zlabel_d.setEnabled(false); zlabel_d.setValue(xlabel_d.getValue());
            
            boolean selected = xlabel_s.isSelected();
            
            // Also propagating the event call to the corresponding actions for simplicity
            ylabel_s.setEnabled(false); ylabel_s.setSelected(selected); ylabel_sActionPerformed(evt);
            zlabel_s.setEnabled(false); zlabel_s.setSelected(selected); zlabel_sActionPerformed(evt);
                        
        } else {
            ylabel_d.setEnabled(true);
            zlabel_d.setEnabled(true);
            
            ylabel_s.setEnabled(true);
            zlabel_s.setEnabled(true);
        }
    }//GEN-LAST:event_labels_syncActionPerformed

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void xlabel_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xlabel_sActionPerformed
        boolean selected = xlabel_s.isSelected(); 
        parent.xlabel.setVisible(selected); 
        parent.xlabel_b.setVisible(label_border.isSelected() && selected);
        
        if (labels_sync.isSelected()){
            ylabel_s.setSelected(selected);
            zlabel_s.setSelected(selected);
            parent.ylabel.setVisible(selected); 
            parent.zlabel.setVisible(selected); 

            parent.ylabel_b.setVisible(label_border.isSelected() && selected);
            parent.zlabel_b.setVisible(label_border.isSelected() && selected);
        }        
    }//GEN-LAST:event_xlabel_sActionPerformed

    private void xlabel_dPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_xlabel_dPropertyChange

    }//GEN-LAST:event_xlabel_dPropertyChange

    /**
     * Checkbox logic following some simple grouping/ freezing logic. Also uses
     * label_update() to propagate the changes in the figure.
     */
    private void xlabel_dStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xlabel_dStateChanged
        parent.xlabel_dis = ((int) xlabel_d.getValue())/100.0;
        label_update(xlabel, parent.xlabel, false);
        
        if (labels_sync.isSelected()){
            ylabel_d.setValue(xlabel_d.getValue()); parent.ylabel_dis =parent.xlabel_dis; label_update(ylabel, parent.ylabel, false); 
            zlabel_d.setValue(xlabel_d.getValue()); parent.zlabel_dis =parent.xlabel_dis; label_update(zlabel, parent.zlabel, false);
        }
    }//GEN-LAST:event_xlabel_dStateChanged

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void tics_syncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tics_syncActionPerformed
        if (tics_sync.isSelected()){
            ytic_d.setEnabled(false); ytic_d.setValue(xtic_d.getValue());
            ztic_d.setEnabled(false); ztic_d.setValue(xtic_d.getValue());
            
            boolean selected = xtic_s.isSelected();
            
            // Also propagating the event call to the corresponding actions for simplicity
            ytic_s.setEnabled(false); ytic_s.setSelected(xtic_s.isSelected()); ytic_sActionPerformed(evt);
            ztic_s.setEnabled(false); ztic_s.setSelected(xtic_s.isSelected()); ztic_sActionPerformed(evt);
            
        } else {
            ytic_d.setEnabled(true);
            ztic_d.setEnabled(true);

            ytic_s.setEnabled(true);
            ztic_s.setEnabled(true);
        }
    }//GEN-LAST:event_tics_syncActionPerformed

    /**
     * Checkbox logic following some simple grouping/ freezing logic. Also uses
     * tic_update() to propagate the changes in the figure.
     */
    private void xtic_dStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xtic_dStateChanged
        parent.xtic_dis = ((int) xtic_d.getValue())/100.0;
        tic_update(0);
        
        if (tics_sync.isSelected()){
            ytic_d.setValue(xtic_d.getValue()); parent.ytic_dis =parent.xtic_dis; tic_update(2);
            ztic_d.setValue(xtic_d.getValue()); parent.ztic_dis =parent.xtic_dis; tic_update(1);
        }
        
    }//GEN-LAST:event_xtic_dStateChanged

    /**
     * Grid event handled by a common function. 
     * @see grid_common()
     */
    private void gridxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridxActionPerformed
        grid_common();
    }//GEN-LAST:event_gridxActionPerformed
    /**
     * Grid event handled by a common function. 
     * @see grid_common()
     */
    private void gridyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridyActionPerformed
        grid_common();
    }//GEN-LAST:event_gridyActionPerformed
    /**
     * Grid event handled by a common function. 
     * @see grid_common()
     */
    private void gridzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gridzActionPerformed
        grid_common();
    }//GEN-LAST:event_gridzActionPerformed

    private void xlabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xlabelActionPerformed
    }//GEN-LAST:event_xlabelActionPerformed

    private void xlabelInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_xlabelInputMethodTextChanged
    }//GEN-LAST:event_xlabelInputMethodTextChanged
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void xlabelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_xlabelFocusLost
        label_update(xlabel,parent.xlabel, true);
    }//GEN-LAST:event_xlabelFocusLost
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void xlabelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xlabelKeyTyped
        label_update(xlabel,parent.xlabel, true);
    }//GEN-LAST:event_xlabelKeyTyped
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void ylabelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ylabelKeyTyped
        label_update(ylabel,parent.ylabel, true);
    }//GEN-LAST:event_ylabelKeyTyped
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void ylabelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ylabelFocusLost
        label_update(ylabel,parent.ylabel, true);
    }//GEN-LAST:event_ylabelFocusLost
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void zlabelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_zlabelKeyTyped
        label_update(zlabel,parent.zlabel, true);
    }//GEN-LAST:event_zlabelKeyTyped
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void zlabelFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_zlabelFocusLost
        label_update(zlabel,parent.zlabel, true);
    }//GEN-LAST:event_zlabelFocusLost
 
    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void zlabel_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zlabel_sActionPerformed
        parent.zlabel.setVisible(zlabel_s.isSelected());
        parent.zlabel_b.setVisible(label_border.isSelected() && zlabel_s.isSelected());
    }//GEN-LAST:event_zlabel_sActionPerformed

    /**
     * Checkbox logic following some simple grouping/ freezing logic.
     */
    private void axesm_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_axesm_sActionPerformed
        parent.axismGroup.setVisible(axesm_s.isSelected());
    }//GEN-LAST:event_axesm_sActionPerformed

    /**
     * Hiding/showing ticks immediately, based on checkbox selection.
     */
    private void ytic_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ytic_sActionPerformed
        boolean selected = ytic_s.isSelected();
        parent.ticsGroups[2].setVisible(selected);
        for (Object tic : parent.tics[2].keySet()){
            Node n = ((Group) ((Pair)parent.tics[2].get(tic)).getValue()).getChildren().getLast();
            n.setVisible(selected&&label_border.isSelected());
        } 
    }//GEN-LAST:event_ytic_sActionPerformed
    /**
     * Hiding/showing ticks immediately, based on checkbox selection.
     */
    private void ztic_sActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ztic_sActionPerformed
        boolean selected = ztic_s.isSelected();
        parent.ticsGroups[1].setVisible(selected);
        for (Object tic : parent.tics[1].keySet()){
            Node n = ((Group) ((Pair)parent.tics[1].get(tic)).getValue()).getChildren().getLast();
            n.setVisible(selected&&label_border.isSelected());
        } 
    }//GEN-LAST:event_ztic_sActionPerformed
   
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void ylabel_dStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ylabel_dStateChanged
        parent.ylabel_dis = ((int) ylabel_d.getValue())/100.0;
        label_update(ylabel, parent.ylabel, false);
    }//GEN-LAST:event_ylabel_dStateChanged
    /**
     * Quick label modification, handled by a common function. 
     * @see #label_update()
     */
    private void zlabel_dStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zlabel_dStateChanged
        parent.zlabel_dis = ((int) zlabel_d.getValue())/100.0;
        label_update(zlabel, parent.zlabel, false);
    }//GEN-LAST:event_zlabel_dStateChanged
    
    /**
     * Quick tick modification, handled by a common function. 
     * @see tic_update()
     */
    private void ytic_dStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ytic_dStateChanged
        parent.ytic_dis = ((int) ytic_d.getValue())/100.0;
        tic_update(2);
    }//GEN-LAST:event_ytic_dStateChanged
    /**
     * Quick tick modification, handled by a common function. 
     * @see tic_update()
     */
    private void ztic_dStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ztic_dStateChanged
        parent.ztic_dis = ((int) ztic_d.getValue())/100.0;
        tic_update(1);
    }//GEN-LAST:event_ztic_dStateChanged

    /**
     * Immediate label rotation action based on the checkbox.
     */
    private void zlabel_hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zlabel_hActionPerformed
        if (zlabel_h.isSelected()){  
            parent.zlabel.setRotate(270);
            parent.zlabel_b.setRotate(90);
        } else {
            parent.zlabel.setRotate(0);
            parent.zlabel_b.setRotate(0);
        }
    }//GEN-LAST:event_zlabel_hActionPerformed

    /**
     * Tick rotation modification, handled by a common function. 
     * @see rotate_tics()
     */
    private void ztic_hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ztic_hActionPerformed
        rotate_tics(ztic_h.isSelected(),1);
    }//GEN-LAST:event_ztic_hActionPerformed

    /**
     * Hide/show (or redraw according to colour selection) of the label borders.
     */
    private void label_borderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_label_borderActionPerformed
        boolean select = label_border.isSelected();
        boolean[] show_border_l = {select && xlabel_s.isSelected(),
                                   select && ylabel_s.isSelected(),
                                   select && zlabel_s.isSelected()};
        parent.xlabel_b.setVisible(show_border_l[0]);
        parent.ylabel_b.setVisible(show_border_l[1]);
        parent.zlabel_b.setVisible(show_border_l[2]);            

        boolean[] show_border_t = {select && xtic_s.isSelected(),
                                   select && ztic_s.isSelected(),
                                   select && ytic_s.isSelected()};
        for (int ax=0; ax<3; ax++){
            for (Object tic : parent.tics[ax].keySet()){
                Node n = ((Group) ((Pair)parent.tics[ax].get(tic)).getValue()).getChildren().getLast();
                n.setVisible(show_border_t[ax]);
                n = ((Group) ((Pair)parent.tics[ax].get(tic)).getValue()).getChildren().getFirst();
                ((Text3DMesh) n).setTextureModeNone(select?javafx.scene.paint.Color.DARKGRAY:javafx.scene.paint.Color.LIGHTGRAY);
            }  
        }
        for (Object tic : parent.point_labels.getChildren()){
                Node n = ((Group) tic).getChildren().getLast();
                n.setVisible(select);
                n = ((Group) (tic)).getChildren().getFirst();
                ((Text3DMesh) n).setTextureModeNone(select?javafx.scene.paint.Color.BLACK:javafx.scene.paint.Color.DARKGRAY);
        }
    }//GEN-LAST:event_label_borderActionPerformed

    /**
     * Label colour modification, also using the common label refresh function. 
     * @see #label_update()
     */
    private void labels_rgbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labels_rgbActionPerformed
       if (labels_rgb.isSelected()){
           int ax=0;
           for (Node n : parent.axisGroup.getChildren()){
               switch(ax++){
                   case 0: ((Cylinder) n).setMaterial(parent.redMaterial); break;
                   case 1: ((Cylinder) n).setMaterial(parent.greenMaterial); break;
                   case 2: ((Cylinder) n).setMaterial(parent.blueMaterial); break;
               }
           }
       } else {
           for (Node n : parent.axisGroup.getChildren()){
               ((Cylinder) n).setMaterial(parent.grayMaterial);
           }
       }
       label_update(xlabel,parent.xlabel,true);
       label_update(ylabel,parent.ylabel,true);
       label_update(zlabel,parent.zlabel,true);
    }//GEN-LAST:event_labels_rgbActionPerformed

    /**
     * Hide/show ticks, and fix their direction and other live attributes using
     * a function from the Juniper thread.
     */
    private void tics_marksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tics_marksActionPerformed
        boolean selected = tics_marks.isSelected();
        for (int ax=0; ax<parent.ticMarksGroups.length; ax++)
            parent.ticMarksGroups[ax].setVisible(selected);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{                    
                    parent.seeLabels();
                }  catch (Exception e){
                }
            }
        });
    }//GEN-LAST:event_tics_marksActionPerformed

    /**
     * Grid opacity change directly inside the Juniper class.
     */
    private void grid_opacityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_grid_opacityStateChanged
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int ax = 0; ax < parent.gridSurfGroups.length; ax++) {
                        parent.grid_opacity = grid_opacity.getValue() / 100.0;
                        for (Node n : parent.gridSurfGroups[ax].getChildren()) {
                            n.setOpacity(grid_opacity.getValue() / 100.0);
                        }
                    }

                    parent.gridLinesMaterial.setDiffuseColor(javafx.scene.paint.Color.color(0.5, 0.5, 0.5, (10 + grid_opacity.getValue()) / 100.0));
                    parent.gridLinesMaterial.setSpecularColor(javafx.scene.paint.Color.color(0.5, 0.5, 0.5, (10 + grid_opacity.getValue()) / 100.0));
                } catch (Exception e) {
                }
            }
        });        
    }//GEN-LAST:event_grid_opacityStateChanged

    /**
     * Grid type modification, handled by a common function. 
     * @see #grid_common()
     */
    private void grid_typeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_grid_typeItemStateChanged
        try{                    
            grid_common();
        }  catch (Exception e){
        }
    }//GEN-LAST:event_grid_typeItemStateChanged

    /**
     * Shadow modification, handled by a common function. 
     * @see #shadow_common()
     */
    private void shadowx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shadowx1ActionPerformed
        shadow_common();
    }//GEN-LAST:event_shadowx1ActionPerformed
    /**
     * Shadow modification, handled by a common function. 
     * @see #shadow_common()
     */
    private void shadowy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shadowy1ActionPerformed
        shadow_common();
    }//GEN-LAST:event_shadowy1ActionPerformed
    /**
     * Shadow modification, handled by a common function. 
     * @see #shadow_common()
     */
    private void shadowz1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shadowz1ActionPerformed
        shadow_common();
    }//GEN-LAST:event_shadowz1ActionPerformed
    
    /**
     * Shadow opacity modification per object inside Juniper class. 
     */
    private void shadow_opacityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_shadow_opacityStateChanged
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    parent.shadow_opacity = shadow_opacity.getValue() / 100.0;
                    parent.shadowMaterial.setDiffuseColor(javafx.scene.paint.Color.color(0.5, 0.5, 0.5, shadow_opacity.getValue() / 100.0));

                    for (int ax = 0; ax < parent.point_shadow_projections.length; ax++) {

                        double a = shadow_opacity.getValue() / 100.0;
                        
                        // This is per object because each point may have a different colour
                        for (Node n : parent.point_shadow_projections[ax].getChildren()) {
                            {
                                PhongMaterial m = (PhongMaterial) ((Shape3D) n).getMaterial();
                                javafx.scene.paint.Color c = m.getDiffuseColor();
                                double r = c.getRed();
                                double g = c.getGreen();
                                double b = c.getBlue();
                                m = new PhongMaterial();

                                m.setDiffuseColor(new javafx.scene.paint.Color(r, g, b, a));
                                ((Shape3D) n).setMaterial(m);
                            }
                        }
                        // JavaFX bug? (it works for boxes just by changing the material)
                        for (Node n : parent.point_shadows[ax].getChildren()) {
                            if (pTypeComboBox.getSelectedIndex() == 0) // Spheres                             
                            {
                                ((Shape3D) n).setMaterial(parent.shadowMaterial);
                            }

                        }
                    }
                } catch (Exception e) {
                }
            }
        });
    }//GEN-LAST:event_shadow_opacityStateChanged
    
    /**
     * Shadow modification, handled by a common function, with early initialisation 
     * workaround. 
     * @see #shadow_common()
     */
    private void shadow_typeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_shadow_typeItemStateChanged
        try {
        shadow_common();} catch (Exception e){}
    }//GEN-LAST:event_shadow_typeItemStateChanged
    
    /**
     * Enable/disable colour-mapped points through a boolean, and then replot.
     */
    private void depthcolorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depthcolorsActionPerformed
        parent.depth_colormap=depthcolors.isSelected();
        if (!depthcolors.isSelected())
            plotButtonActionPerformed(null);
        else
            Platform.runLater(new Runnable() {
                @Override
                public void run() {                   
                    parent.seeLabels(); // (removing the colours is simpler)
                }
            });  
        
    }//GEN-LAST:event_depthcolorsActionPerformed

    /**
     * Modify fog opacity.
     */
    private void fog_sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fog_sliderStateChanged
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                parent.fog.setVisible(fog_slider.getValue()!=0); 
                
                // Per fog rectangle (group opacity change is not supported in JavaFX)
                for (Node n:parent.fog.getChildren())
                    n.setOpacity(fog_slider.getValue()/500.0);
                parent.seeLabels();
            }
        }); 
    }//GEN-LAST:event_fog_sliderStateChanged

    /**
     * Export ratio change handled by common function,
     * @see #ratio_helper()
     */
    private void ratio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratio1ActionPerformed
        ratio_helper();
    }//GEN-LAST:event_ratio1ActionPerformed
    /**
     * Export ratio change handled by common function,
     * @see #ratio_helper()
     */     
    private void ratio2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratio2ActionPerformed
        ratio_helper();
    }//GEN-LAST:event_ratio2ActionPerformed
    /**
     * Export ratio change handled by common function,
     * @see #ratio_helper()
     */ 
    private void ratio3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratio3ActionPerformed
        ratio_helper();
    }//GEN-LAST:event_ratio3ActionPerformed
    /**
     * Export ratio change handled by common function,
     * @see #ratio_helper()
     */ 
    private void ratio4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratio4ActionPerformed
        ratio_helper();
    }//GEN-LAST:event_ratio4ActionPerformed
    /**
     * Export ratio change handled by common function,
     * @see #ratio_helper()
     */ 
    private void ratio5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ratio5ActionPerformed
        ratio_helper();
    }//GEN-LAST:event_ratio5ActionPerformed

    private boolean chain=false; // assists keeping a steady ration when changing resolution 

    /**
     * Export resolution change handled by common function,
     * @see #ratio_helper()
     */ 
    private void picXdimSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_picXdimSpinnerStateChanged
        if (chain==false){ 
            ratio_helper();              
        } else
            chain=false;
    }//GEN-LAST:event_picXdimSpinnerStateChanged
    /**
     * Export resolution change handled by common function,
     * @see #ratio_helper_inv()
     */ 
    private void picYdimSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_picYdimSpinnerStateChanged
        if (chain==false){ 
            ratio_helper_inv(); 
        } else
            chain=false;
    }//GEN-LAST:event_picYdimSpinnerStateChanged

    /**
     * Combo box logic following some simple grouping/ freezing logic.
     */
    private void z4cb1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_z4cb1ItemStateChanged
        boolean card_en = z4l1.isEnabled() && (z4cb1.getSelectedIndex()!=2);
        z4js.setEnabled(card_en);  z4l2.setEnabled(card_en);
        z4cb2.setEnabled(card_en); z4l3.setEnabled(card_en);        
    }//GEN-LAST:event_z4cb1ItemStateChanged
    /**
     * Slider logic following some simple grouping/ freezing logic.
     */
    private void z4jsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_z4jsStateChanged
        if ((int)z4js.getValue()<1)
            z4js.setValue(1);
        if ((int)z4js.getValue()>dataset.size())
            z4js.setValue(dataset.size());
    }//GEN-LAST:event_z4jsStateChanged

    /**
     * Change all label sizes except per point (z4), within Juniper class. 
     */
    private void lsizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lsizeStateChanged
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    double new_size = (int)lsize.getValue()/12.0;
                    parent.xlabel.setScaleX(0.8*new_size); parent.xlabel.setScaleY(0.8*new_size);
                    parent.ylabel.setScaleX(0.8*new_size); parent.ylabel.setScaleY(0.8*new_size);
                    parent.zlabel.setScaleX(0.8*new_size); parent.zlabel.setScaleY(0.8*new_size);
                    
                    parent.xlabel_b.setScaleX(new_size); parent.xlabel_b.setScaleY(new_size);
                    parent.ylabel_b.setScaleX(new_size); parent.ylabel_b.setScaleY(new_size);
                    parent.zlabel_b.setScaleX(new_size); parent.zlabel_b.setScaleY(new_size);
                    
                    for (int ax=0; ax<3; ax++){
                        // Including all tick-related labels, tick-by-tick
                        for (Node n: parent.ticsGroups[ax].getChildren()){
                            Text3DMesh tm = (Text3DMesh) ((Group) n).getChildren().get(0);
                            Rectangle re = (Rectangle) ((Group) n).getChildren().get(1);
                            tm.setScaleX(0.8*new_size); tm.setScaleY(0.8*new_size); 
                            re.setScaleX(new_size); re.setScaleY(new_size); 
                        }
                    }
                }  catch (Exception e){
                }
            }
        }); 
    }//GEN-LAST:event_lsizeStateChanged
    /**
     * Change all z4 label sizes, within Juniper class. 
     */
    private void z4cb3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_z4cb3StateChanged
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    double new_size = (int)z4cb3.getValue()/12.0;                    
                    for (Node n: parent.point_labels.getChildren()){
                        Text3DMesh tm = (Text3DMesh) ((Group) n).getChildren().get(0);
                        Rectangle re = (Rectangle) ((Group) n).getChildren().get(1);
                        tm.setScaleX(0.8*new_size); tm.setScaleY(0.8*new_size); 
                        re.setScaleX(new_size); re.setScaleY(new_size); 
                    }   
                }  catch (Exception e){
                }
            }
        }); 
    }//GEN-LAST:event_z4cb3StateChanged
    
    /**
     * Adjust Y dimension of exported image, when modifying X and ratio is fixed.
     */
    private void ratio_helper(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    chain=true;
                    if (ratio1.isSelected()){
                        picYdimSpinner.setValue(Integer.parseInt(picXdimSpinner.getValue().toString())*1);
                    } else if (ratio2.isSelected()){
                        picYdimSpinner.setValue(Integer.parseInt(picXdimSpinner.getValue().toString())*3/4);
                    } else if (ratio3.isSelected()){
                        picYdimSpinner.setValue(Integer.parseInt(picXdimSpinner.getValue().toString())*10/16);
                    } else if (ratio4.isSelected()){
                        picYdimSpinner.setValue(Integer.parseInt(picXdimSpinner.getValue().toString())*100000/161803);
                    } else if (ratio5.isSelected()){
                        picYdimSpinner.setValue(Integer.parseInt(picXdimSpinner.getValue().toString())*9/16);
                    }                   
                }  catch (Exception e){
                }
                
            }
        });       
    }
    /**
     * Adjust X dimension of exported image, when modifying Y and ratio is fixed.
     */
    private void ratio_helper_inv(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{ 
                    chain=true;
                    if (ratio1.isSelected()){
                        picXdimSpinner.setValue(Integer.parseInt(picYdimSpinner.getValue().toString())*1);
                    } else if (ratio2.isSelected()){
                        picXdimSpinner.setValue(Integer.parseInt(picYdimSpinner.getValue().toString())*4/3);
                    } else if (ratio3.isSelected()){
                        picXdimSpinner.setValue(Integer.parseInt(picYdimSpinner.getValue().toString())*16/10);
                    } else if (ratio4.isSelected()){
                        picXdimSpinner.setValue(Integer.parseInt(picYdimSpinner.getValue().toString())*16180/10000);
                    } else if (ratio5.isSelected()){
                        picXdimSpinner.setValue(Integer.parseInt(picYdimSpinner.getValue().toString())*16/9);
                    }                       
                }  catch (Exception e){
                }
                //chain=true;
            }
        }); 
    }
    
    /**
     * Common function for grid attribute manipulation.
     */
    public void grid_common(){
        
        // Logic to decide what is visible or not according to grid type and check boxes
        boolean lines = grid_type.getSelectedIndex()!=1;
        boolean surfaces = grid_type.getSelectedIndex()>=1;
        boolean xor = grid_type.getSelectedIndex()==2;
        boolean preselect = grid_type.getSelectedIndex()==3;
        
        boolean selected_x = gridx.isSelected();
        parent.gridLinesGroups[0].setVisible((preselect||(xor^selected_x)) && lines);
        parent.gridLinesGroups[3].setVisible((preselect||(xor^selected_x)) && lines);
        parent.gridLinesGroups[4].setVisible((preselect||(xor^selected_x)) && lines);
        parent.gridSurfGroups[0].setVisible((selected_x) && surfaces);
        
        boolean selected_y = gridy.isSelected();        
        parent.gridLinesGroups[2].setVisible((preselect||(xor^selected_y)) && lines);
        parent.gridLinesGroups[5].setVisible((preselect||(xor^selected_y)) && lines);
        parent.gridLinesGroups[6].setVisible((preselect||(xor^selected_y)) && lines);
        parent.gridSurfGroups[2].setVisible((selected_y) &&surfaces);
        
        boolean selected_z = gridz.isSelected();        
        parent.gridLinesGroups[1].setVisible((preselect||(xor^selected_z)) && lines);
        parent.gridLinesGroups[7].setVisible((preselect||(xor^selected_z)) && lines);
        parent.gridLinesGroups[8].setVisible((preselect||(xor^selected_z)) && lines);
        parent.gridLinesGroups[9].setVisible((preselect||(xor^selected_z)) && lines);
        parent.gridSurfGroups[1].setVisible((selected_z) && surfaces);
        
        // Freeze/unfreeze GUI elements
        if (selected_x || selected_y || selected_z || xor || preselect){
            grid_type.setEnabled(true);  grid_opacity.setEnabled(true);
        } else {
            grid_opacity.setEnabled(false);
        }
        
        // Invoke seeLabels() to refresh the plot
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{                    
                    parent.seeLabels();
                }  catch (Exception e){
                }
            }
        });
    }
    
    /**
     * Common function for shadow attribute manipulation.
     */
    public void shadow_common(){
        
        // Logic to decide what is visible or not according to shadow type and check boxes
        boolean projections = (shadow_type.getSelectedIndex()==0)||(shadow_type.getSelectedIndex()>=2);
        boolean paths =       (shadow_type.getSelectedIndex()==1)||(shadow_type.getSelectedIndex()>=2);
        boolean xor =         (shadow_type.getSelectedIndex()==3);
        boolean preselect =         (shadow_type.getSelectedIndex()==4);
        
        boolean selected_x = shadowx1.isSelected();
        parent.point_shadows[2].setVisible(selected_x && paths);
        parent.point_shadows[4].setVisible(selected_x && paths);
        parent.point_shadow_projections[2].setVisible((preselect||(xor^selected_x)) && projections);
        parent.point_shadow_projections[4].setVisible((preselect||(xor^selected_x)) && projections);
        
        boolean selected_y = shadowy1.isSelected();        
        parent.point_shadows[1].setVisible(selected_y && paths);
        parent.point_shadows[3].setVisible(selected_y && paths);     
        parent.point_shadow_projections[1].setVisible((preselect||(xor^selected_y)) && projections);
        parent.point_shadow_projections[3].setVisible((preselect||(xor^selected_y)) && projections);
        
        boolean selected_z = shadowz1.isSelected();  
        parent.point_shadows[0].setVisible(selected_z && paths);
        parent.point_shadow_projections[0].setVisible((preselect||(xor^selected_z)) && projections);
        
        // Freeze/unfreeze GUI elements
        if (selected_x || selected_y || selected_z || xor || preselect){
            shadow_type.setEnabled(true);  shadow_opacity.setEnabled(true);
        } else {
                                           shadow_opacity.setEnabled(false);
        }
        // Invoke seeLabels() to refresh the plot
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{                    
                    parent.seeLabels();
                }  catch (Exception e){
                }
            }
        });
    }
    
    /**
     * Common function for tick rotation. Applied per tick Node within Juniper.
     * 
     * @param horizontal rotate or not
     * @param ax axis (0 to 2 internally)
     */
    public void rotate_tics (boolean horizontal, int ax){
        if (horizontal){
            boolean dir = false;
            
            // Take into consideration if whole axis is toward the left or right of the plot
            switch (ax){
                case 0: dir = (parent.rot>90 && parent.rot<270)^(!(parent.rot>0 && parent.rot<180)); break;
                case 1: dir = ((parent.rot>45 && parent.rot<225)^(parent.rot>315 || parent.rot<135)); break;
                case 2: dir = !((parent.rot>90 && parent.rot<270)^(!(parent.rot>0 && parent.rot<180))); break;
            }
            for (Object tic : parent.tics[ax].keySet()){ 
                for (Node n: ((Group) ((Pair)parent.tics[ax].get(tic)).getValue()).getChildren())
                    if(dir)
                       n.setRotate(90);            
                    else
                       n.setRotate(270);
            }
        } else {
            for (Object tic : parent.tics[ax].keySet()){ 
                for (Node n: ((Group) ((Pair)parent.tics[ax].get(tic)).getValue()).getChildren())
                    n.setRotate(0);            
            }
        }
    } 
    
    /**
     * Common function for label updates, directly through Juniper class.
     * 
     * @param label the JTextField inside the control window to read its current text
     * @param plabel the corresponding (FXyz) axis label within the Juniper class
     * @param change_text a boolean denoting if this call is to update the text
     */
    private void label_update(javax.swing.JTextField label, Text3DMesh plabel, boolean change_text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{                  
                    // Adjust text,
                    if (change_text) plabel.setText3D(label.getText()); 
                    if (label==xlabel) {
                        // label colour,
                        if (change_text) plabel.setTextureModeNone(labels_rgb.isSelected()?javafx.scene.paint.Color.DARKRED:javafx.scene.paint.Color.BLACK);
                        // distance from the axis,
                        plabel.getParent().setTranslateY(- parent.AXIS_LENGTH*parent.xlabel_dis); 
                        plabel.getParent().setTranslateZ(- parent.AXIS_LENGTH*parent.xlabel_dis);    
                    
                    }
                    if (label==ylabel) {
                        if (change_text) plabel.setTextureModeNone(labels_rgb.isSelected()?javafx.scene.paint.Color.DARKBLUE:javafx.scene.paint.Color.BLACK);
                        plabel.getParent().setTranslateY(- parent.AXIS_LENGTH*parent.ylabel_dis);
                        plabel.getParent().setTranslateX(- parent.AXIS_LENGTH*parent.ylabel_dis);
                    }
                    if (label==zlabel){
                        if (change_text) plabel.setTextureModeNone(labels_rgb.isSelected()?javafx.scene.paint.Color.DARKGREEN:javafx.scene.paint.Color.BLACK);
                        plabel.getParent().setTranslateZ(- parent.AXIS_LENGTH*parent.zlabel_dis);
                        plabel.getParent().setTranslateX(- parent.AXIS_LENGTH*parent.zlabel_dis);
                    }
                    if (change_text) {
                        plabel.setTranslateX(-plabel.getLayoutBounds().getCenterX());
                        plabel.setTranslateY(-plabel.getLayoutBounds().getCenterY());
                        
                        // and the corresponding label border.
                        double height=plabel.getLayoutBounds().getHeight();
                        double width =plabel.getLayoutBounds().getWidth();
                        Rectangle r = (Rectangle)((Group)(plabel.getParent())).getChildren().getLast();
                        
                        r.setHeight(height); 
                        r.setWidth(width);
                        r.setTranslateX(-width/2);
                        r.setTranslateY(-height/2);
                    }
                    parent.seeLabels();
                }  catch (Exception e){
                }
            }
        });
    }
    
    /**
     * Common function for tick updates (distance from the axis in question). 
     * 
     * @param ax axis (0 to 2 internally)
     */
    private void tic_update(int ax){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{                    
                    double ddis = 0;
                    // Apply the change to the corresponding tick Group.
                    switch(ax){
                        case 0: ddis = parent.AXIS_LENGTH*parent.xtic_dis;
                            parent.ticsGroups[ax].setTranslateY(ddis);                            
                            if (parent.ticsGroups[ax].getTranslateZ()<parent.AXIS_LENGTH)
                                parent.ticsGroups[ax].setTranslateZ(ddis);  
                            else
                                parent.ticsGroups[ax].setTranslateZ(-ddis+parent.AXIS_LENGTH); 
                            break;
                        case 2: ddis =-parent.AXIS_LENGTH*parent.ytic_dis;
                            if (parent.ticsGroups[ax].getTranslateX()<parent.AXIS_LENGTH)
                                parent.ticsGroups[ax].setTranslateX(ddis);  
                            else
                                parent.ticsGroups[ax].setTranslateX(-ddis+parent.AXIS_LENGTH); 
                            parent.ticsGroups[ax].setTranslateY(ddis);  
                            break;
                        case 1: ddis =-parent.AXIS_LENGTH*parent.ztic_dis;
                            if (parent.ticsGroups[ax].getTranslateX()<parent.AXIS_LENGTH)
                                parent.ticsGroups[ax].setTranslateX(ddis);  
                            else
                                parent.ticsGroups[ax].setTranslateX(-ddis+parent.AXIS_LENGTH);  
                            if (parent.ticsGroups[ax].getTranslateZ()<parent.AXIS_LENGTH)
                                parent.ticsGroups[ax].setTranslateZ(ddis);  
                            else
                                parent.ticsGroups[ax].setTranslateZ(-ddis+parent.AXIS_LENGTH);  
                            break;
                    }
                    parent.seeLabels();
                    
                }  catch (Exception e){
                }
            }
        });
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Controls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Controls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Controls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Controls.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Controls().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aboutLabel;
    private javax.swing.JPanel aboutPanel;
    private javax.swing.JCheckBox axes_s;
    private javax.swing.JCheckBox axesm_s;
    private javax.swing.ButtonGroup button_group_ratios;
    private javax.swing.ButtonGroup button_group_viewer_sources;
    private javax.swing.JPanel dataPanel;
    private javax.swing.JButton dataViewerLoadButton;
    private javax.swing.JScrollPane dataViewerPane;
    private javax.swing.JTable dataViewerTable;
    private javax.swing.JLayeredPane depthLayeredPane;
    private javax.swing.JLabel depthPercLabel;
    private javax.swing.JCheckBox depthcolors;
    private javax.swing.JScrollPane dimAssignmentScrollPane;
    private javax.swing.JTable dimAssignmentTable;
    private javax.swing.JPanel dimPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel exportPanel;
    private javax.swing.JLabel fileSelectLabel;
    private javax.swing.JLabel fileSepLabel;
    private javax.swing.JLabel fogLabel;
    private javax.swing.JSlider fog_slider;
    private javax.swing.JLabel fovLabel;
    private javax.swing.JSlider fovSlider;
    private javax.swing.JComboBox<String> fsepComboBox;
    private javax.swing.JLabel gridLabel;
    private javax.swing.JLabel gridTypeLabel;
    private javax.swing.JSlider grid_opacity;
    private javax.swing.JComboBox<String> grid_type;
    private javax.swing.JCheckBox gridx;
    private javax.swing.JCheckBox gridy;
    private javax.swing.JCheckBox gridz;
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLayeredPane imageLayeredPane;
    private javax.swing.JButton imgExportButton;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JCheckBox label_border;
    private javax.swing.JLayeredPane labelsLayeredPane;
    private javax.swing.JCheckBox labels_rgb;
    private javax.swing.JCheckBox labels_sync;
    private javax.swing.JScrollPane licenseScrollPane;
    private javax.swing.JTextArea licenseTextArea;
    private javax.swing.JButton loadButton;
    public javax.swing.JSpinner lsize;
    private javax.swing.JCheckBox namesFromLineCheckBox;
    private javax.swing.JLabel opacityLabel;
    private javax.swing.JLabel pSizeLabel;
    private javax.swing.JSlider pSizeSlider;
    private javax.swing.JComboBox<String> pTypeComboBox;
    private javax.swing.JLabel pTypeLabel;
    private javax.swing.JButton pasteButton;
    public javax.swing.JSpinner picXdimSpinner;
    public javax.swing.JSpinner picYdimSpinner;
    private javax.swing.JButton plotButton;
    private javax.swing.JRadioButton ratio1;
    private javax.swing.JRadioButton ratio2;
    private javax.swing.JRadioButton ratio3;
    private javax.swing.JRadioButton ratio4;
    private javax.swing.JRadioButton ratio5;
    private javax.swing.JRadioButton ratio6;
    private javax.swing.JLabel ratioLabel;
    private javax.swing.JLayeredPane ratiosLayeredPane;
    private javax.swing.JLabel resLabel;
    private javax.swing.JLabel resTimesLabel;
    private javax.swing.JLabel sOpacityLabel;
    private javax.swing.JLabel shadowLabel;
    private javax.swing.JLabel shadowTypeLabel;
    private javax.swing.JSlider shadow_opacity;
    private javax.swing.JComboBox<String> shadow_type;
    private javax.swing.JCheckBox shadowx1;
    private javax.swing.JCheckBox shadowy1;
    private javax.swing.JCheckBox shadowz1;
    private javax.swing.JLabel showLabel;
    private javax.swing.JCheckBox tics_marks;
    private javax.swing.JCheckBox tics_sync;
    private javax.swing.JPanel viewPanel;
    private javax.swing.JRadioButton viewPointsRadioButton;
    private javax.swing.JRadioButton viewRecRadioButton;
    private javax.swing.JPanel viewerOptPanel;
    private javax.swing.JPanel viewerPanel;
    private javax.swing.JTextField xlabel;
    private javax.swing.JSpinner xlabel_d;
    private javax.swing.JCheckBox xlabel_s;
    private javax.swing.JSpinner xtic_d;
    private javax.swing.JCheckBox xtic_h;
    private javax.swing.JCheckBox xtic_s;
    private javax.swing.JCheckBox yaw_stuck;
    private javax.swing.JTextField ylabel;
    private javax.swing.JSpinner ylabel_d;
    private javax.swing.JCheckBox ylabel_s;
    private javax.swing.JSpinner ytic_d;
    private javax.swing.JCheckBox ytic_h;
    private javax.swing.JCheckBox ytic_s;
    private javax.swing.JLayeredPane z4LayeredPane;
    public javax.swing.JComboBox<String> z4cb1;
    public javax.swing.JComboBox<String> z4cb2;
    public javax.swing.JSpinner z4cb3;
    public javax.swing.JSpinner z4js;
    private javax.swing.JLabel z4l1;
    private javax.swing.JLabel z4l2;
    private javax.swing.JLabel z4l3;
    private javax.swing.JLabel z4l4;
    private javax.swing.JLabel zdimLegendLabel;
    private javax.swing.JTextField zlabel;
    private javax.swing.JSpinner zlabel_d;
    private javax.swing.JCheckBox zlabel_h;
    private javax.swing.JCheckBox zlabel_s;
    private javax.swing.JSpinner ztic_d;
    private javax.swing.JCheckBox ztic_h;
    private javax.swing.JCheckBox ztic_s;
    // End of variables declaration//GEN-END:variables
}
