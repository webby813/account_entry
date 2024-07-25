package javaassignment;

import javax.swing.JFrame;

public class JavaAssignment {
    public static void main(String[] args) {
        //Run UI
        java.awt.EventQueue.invokeLater(() -> {
                JFrame index = new Login();
                index.setVisible(true);
                
        });
    }
}