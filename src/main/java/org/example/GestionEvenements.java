package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;

public class GestionEvenements implements EvenementObservable{

    private static GestionEvenements instance;

    private Map<String, Evenement> evenements;
    private Map<String, List<ParticipantObserver>> observateurs;
    private GestionEvenements(){
        evenements = new HashMap<>();
    }

    public static synchronized GestionEvenements getInstance(){
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }
    public void ajouterEvenement(Evenement evenement){

        evenements.put(evenement.getId(), evenement);
        observateurs.put(evenement.getId(), new ArrayList<>());// on cree un array list pour les observateurs

    }
    public boolean supprimerEvenement(String id){
        if (evenements.containsKey(id)) {
            evenements.remove(id);
            notifierObservateurs(id, "L'événement '" + id + "' a été annulé.");
            observateurs.remove(id); // on peut vider la liste après
        }
        return false;
    }
    @Override
    public void notifierObservateurs(String idEvenement, String message) { // on definit maintenant la fonction pour notifier les observateurs
        List<ParticipantObserver> liste = observateurs.get(idEvenement);
        if (liste != null) {
            for (ParticipantObserver p : liste) {
                p.recevoirNotification(message);
            }
        }
    }
    @Override // Ca c'est pour ajouter un observateur
    public void ajouterObservateur(String idEvenement, ParticipantObserver p) {
        observateurs.computeIfAbsent(idEvenement, k -> new ArrayList<>()).add(p);
    }

    @Override  // Ca pour retirer un observateur
    public void supprimerObservateur(String idEvenement, ParticipantObserver p) {
        if (observateurs.containsKey(idEvenement)) {
            observateurs.get(idEvenement).remove(p);
        }
    }
     public Evenement rechercherEvenement(String Id){
        // on va mettre ce que ça fait plutard
        return evenements.get(Id);
    }
    /*public void modifierEvenement(String id, Evenement evenementModifie) {
        if (evenements.containsKey(id)) {
            evenements.put(id, evenementModifie);
        }
    }*/
    public boolean modifierEvenement(String id, String nom, String lieu, int capacite) {
        Evenement e = evenements.get(id);
        if (e != null) {
            e.setNom(nom);
            e.setLieu(lieu);
            e.setCapaciteMax(capacite);
            return true;
        }
        return false;
    }



    // ici c'est pour sauvegarder l'évènement dans le fichier on converti le MAP<String,Evenement> en fichier Json
    public void SauvegardeEvenement(String cheminFichier){
        try {
            ObjectMapper mapper = new ObjectMapper();// on cree un mapper pour effectuer la serialiastion
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(cheminFichier), evenements.values());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void chargerDepuisJson(String cheminFichier) {
        try {
            ObjectMapper mapper = new ObjectMapper();// on cree un mapper pour effectuer la deserialiastion
            List<Evenement> liste = mapper.readValue( // on lit le fichier Json et on crée une liste d'evenement pour stocker le contenu du fichier
                    new File(cheminFichier),
                    mapper.getTypeFactory().constructCollectionType(
                            List.class, Evenement.class)
            );
            for (Evenement e : liste) { // on parcourt ici chaque evenements lu depuis le fichier Json et on ajoute  l’événement dans une Map<String, Evenement> evenements, en utilisant son id comme clé.
                evenements.put(e.getId(), e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
