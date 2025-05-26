package org.example;



import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        JButton participantsBtn = new JButton("Participants");
        participantsBtn.addActionListener(e -> new ParticipantMenu());

        JButton evenementBtn = new JButton("Événements");
        evenementBtn.addActionListener(e -> new EvenementMenu());

        add(participantsBtn);
        add(evenementBtn);
        setVisible(true);
    }
}

