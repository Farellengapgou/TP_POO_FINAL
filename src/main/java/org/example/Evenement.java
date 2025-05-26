package org.example;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDateTime;

// vu que la classe Evenement est abstraite,on doit dire à jackson que evenement est parent et qu'il existe les types concrets concert et conférence en utilisatant les annotations @JsonTypeInfo et @JsonSubTypes
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Concert.class, name = "concert"),
        @JsonSubTypes.Type(value = Conference.class, name = "conference")
})
public abstract class Evenement {

    protected String id;
    protected String nom;
    protected LocalDateTime date;
    protected String lieu;
    protected int capaciteMax;
    // surchage : création d'un constructeur vide pour la sérialisation et la désérialisation
    public Evenement(){};
    public Evenement(String id,String nom,LocalDateTime date,String lieu,int capaciteMax){
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
    }
    public String getId(){
        return id;
    }
    public String getNom(){
        return nom;
    }
    public LocalDateTime getDate(){
        return date;
    }
    public String getLieu(){
        return lieu;
    }
    public int getCapaciteMax(){
        return capaciteMax;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }


    public abstract boolean ajouterParticipant(Participant participant) throws CapaciteDepasseException;
    public abstract boolean annuler (Participant participant);
    public abstract void afficherDetails();
}
