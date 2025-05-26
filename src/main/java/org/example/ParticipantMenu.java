package org.example;


import javax.swing.*;
import java.awt.*;

public class ParticipantMenu extends JFrame {
    public ParticipantMenu() {
        setTitle("Menu Participants");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1));

        JButton inscrireBtn = new JButton("Inscrire un participant");
        inscrireBtn.addActionListener(e -> new org.example.participants.InscriptionForm());

        JButton desinscrireBtn = new JButton("Désinscrire un participant");
        desinscrireBtn.addActionListener(e -> new org.example.participants.DesinscriptionForm());

        add(inscrireBtn);
        add(desinscrireBtn);
        setVisible(true);
    }
}
