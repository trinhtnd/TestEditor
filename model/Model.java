package model;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
public class Model {
    public void saveToFile(String content) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                writer.println(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            StringBuilder content = new StringBuilder();
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
        return null;
    }
}