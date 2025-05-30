package org.example;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//la ligne ci c'est pour eviter les boucles infinies car dans evenment il y a organisateur comme participant, et l'organisateur a aussi sa liste d'évènement qu'il organise . ca ecrit donc en boucle dans le fichier Json sans ça
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Conference extends Evenement {

    private String theme;
    private List<Participant> intervenants;
    public Conference(){
        super();
        this.intervenants = new ArrayList<>();
    };
    public Conference(String id, String nom, LocalDateTime date, String lieu, int capaciteMax, String theme) {
        super(id, nom, date, lieu, capaciteMax);
        this.theme = theme;
        this.intervenants = new ArrayList<>();
    }


    @Override
    public boolean ajouterParticipant(Participant participant) throws CapaciteDepasseException{
        if(intervenants.size()>= capaciteMax){
            throw new CapaciteDepasseException(" nous somme désolés toutes la liste des intervenants est pleine");
        }
        intervenants.add(participant);

        return false;
    };
    @Override
    public boolean annuler (Participant participant){
       intervenants.remove(participant);
        return false;
    };
    @Override
    public  void afficherDetails (){
        System.out.println("Détails de la conférence");
        System.out.println(" id:"+getId()+"\nnom"+getNom()+"\ndate"+getDate()+"\nlieu"+getLieu()+"\ncapacité max"+getCapaciteMax()+"\ntheme"+getTheme());
        // utilisons les streams pour afficher la liste des intervenants
        System.out.println("La liste des intervenants:");
        AtomicInteger index = new AtomicInteger(1);// pour mettre 1,2,3 devant les participants
        getIntervenants().forEach(inter ->
                System.out.println(index.getAndIncrement() + "."+inter));
    };

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Participant> getIntervenants() {
        return intervenants;
    }

    public void setIntervenants(List<Participant> intervenants) {
        this.intervenants = intervenants;
    }


}





