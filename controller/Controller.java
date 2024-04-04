package controller;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import model.Model;
import view.View;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.addSaveButtonListener((ActionEvent e) -> saveFile());
        view.addOpenButtonListener((ActionEvent e) -> openFile());
        view.addBrowseButtonListener((ActionEvent e) -> browseDirectory());
    }

    private void saveFile() {
        String content = view.getContent();
            model.saveToFile(content);
            JOptionPane.showMessageDialog(view, "File saved successfully.");
            view.clear();
    }

    private void openFile() {
            String content = model.loadFromFile();
            view.setContent(content);
    }

    private void browseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File directory = fileChooser.getSelectedFile();
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(directory.getName());
            addFilesToNode(directory, rootNode);
            view.setTreeData(rootNode);
        }
    }

    private void addFilesToNode(File directory, DefaultMutableTreeNode parentNode) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
                parentNode.add(fileNode);
                if (file.isDirectory()) {
                    addFilesToNode(file, fileNode);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Model model = new Model();
            View view = new View();
            Controller c = new Controller(model, view);
            view.setVisible(true);
        });
    }
}