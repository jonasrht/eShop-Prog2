package eshop.Client.ui.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowCloser extends WindowAdapter {

    public void windowClosing(WindowEvent e) {
        Window window = e.getWindow();
        System.out.println("close");
    }
    
}
