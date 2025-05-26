package org.example;

import java.util.List;

public class Participant implements ParticipantObserver {
    public String id;
    public String nom;
    public String email;
    public Participant(){};
    public Participant(String id,String nom,String email){
        this.id = id;
        this.nom = nom;
        this.email = email;
    }
    @Override // c'est pour notifier le participant
    public void recevoirNotification(String message) {
        System.out.println("Notification pour " + nom + " : " + message);
    }

    public String getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }
    public String getEmail(){
        return email;
    }
    public void setNom(){
        this.nom = nom;
    }
    public void setId(){
        this.id = id;
    }
    public void setEmail(){
        this.email = email;
    }
    @Override
    public String toString() {
        return nom + " - " + email;
    }// comme ca on va pouvoir afficher tout sur participant quand on va utiliser les streams


    public Participant chercherParticipantParId(List<Participant> participants, String id) { // ici c'est pour connaitre un participant connaissant son id
        return participants.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null); // ou tu peux lever une exception si tu préfères
    }


}
