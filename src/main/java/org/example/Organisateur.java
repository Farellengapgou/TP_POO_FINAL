package org.example;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Organisateur extends Participant{

    private List<Evenement> evenementsOrganises;
    public Organisateur(String id,String nom,String email){
        super(id, nom, email);
        this.evenementsOrganises = new ArrayList<>();
    }
    public List<Evenement> getEvenementsOrganises(){
        return evenementsOrganises;
    }
    public void setEvenementsOrganises(){
        this.evenementsOrganises = evenementsOrganises;
    }
    // Ca c'est pour ajouter un nouvel evenement crée à la liste des évènements
    public void ajouterEvenementList(Evenement NewEvenement){
        evenementsOrganises.add(NewEvenement);
    }
}
