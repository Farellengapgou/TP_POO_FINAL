package org.example;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Concert extends Evenement{

    private String artiste;
    private String genreMusical;
    private List<Participant> participants;
    public Concert(){};
    public Concert(String id, String nom, LocalDateTime date, String lieu, int capaciteMax,String artiste,String genreMusical){
        super(id, nom, date, lieu, capaciteMax);
        this.artiste = artiste;
        this.genreMusical = genreMusical;
        this.participants = new ArrayList<>();
    }
    @Override
    public boolean ajouterParticipant(Participant participant) throws CapaciteDepasseException{
        if(participants.size()>= capaciteMax){
            throw new CapaciteDepasseException(" nous somme désolés toutes les places sont prises");
        }
        participants.add(participant);
         // vu que le participant est par défaut un observer pour nous on le met observer automatiquement lors de la création du participant
        if (participant instanceof ParticipantObserver) {
            GestionEvenements.getInstance().ajouterObservateur(this.getId(), (ParticipantObserver) participant);
        }

        return false;
    };
    @Override
    public boolean annuler (Participant participant){
        participants.remove(participant);
        // on fait la même chose pour la suppression que pour la création
        if (participant instanceof ParticipantObserver) {
            GestionEvenements.getInstance().supprimerObservateur(this.getId(), (ParticipantObserver) participant);
        }
        return false;
    };
    @Override
    public  void afficherDetails (){
        System.out.println("Détails du concert");
        System.out.println(" id:"+getId()+"\nnom"+getNom()+"\ndate"+getDate()+"\nlieu"+getLieu()+"\ncapacité max"+getCapaciteMax()+"\nArtiste"+getArtiste()+"\ngenre musical"+getGenreMusical());
        // utilisons les streams pour afficher la liste des participants
        System.out.println("La liste des participants:");
        AtomicInteger index = new AtomicInteger(1);// pour mettre 1,2,3 devant les participants
        getParticipants().forEach(parti ->
                System.out.println(index.getAndIncrement() + "."+parti));
    };
    public List<Participant> getParticipants(){
        return participants;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getGenreMusical() {
        return genreMusical;
    }

    public void setGenreMusical(String genreMusical) {
        this.genreMusical = genreMusical;
    }



}
