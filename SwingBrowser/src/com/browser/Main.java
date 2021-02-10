package com.browser;

//https://www.google.com

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class Main extends JFrame implements ActionListener, HyperlinkListener {
    private JTextField addressBar;
    private JEditorPane pane;

    Main() {
        super("Swing HTML Browser");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addressBar = new JTextField();
        addressBar.addActionListener(this);
        pane = new JEditorPane();
        pane.setEditable(false);
        pane.addHyperlinkListener(this);
        add(addressBar, BorderLayout.NORTH);
        add(new JScrollPane(pane));
        setSize(new Dimension(400, 400));
    }

    public void actionPerformed(ActionEvent evt) {
//        String url = "https://www.google.com";//
        String url = addressBar.getText();
        try {
            pane.setPage(url);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void hyperlinkUpdate(HyperlinkEvent evt) {
        if (evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }
        JEditorPane srcPane = (JEditorPane)evt.getSource();
        if (evt instanceof HTMLFrameHyperlinkEvent) {
            HTMLDocument doc = (HTMLDocument)pane.getDocument();
            doc.processHTMLFrameHyperlinkEvent((HTMLFrameHyperlinkEvent)evt);
        } else {
            String url = evt.getURL().toString();
            addressBar.setText(url);
            try {
                pane.setPage(url);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        Main browser = new Main();
        browser.setVisible(true);
    }
}