package org.example;

import java.io.IOException;
import java.util.*;
import java.io.File;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class GestionEvenements implements EvenementObservable {

    private static GestionEvenements instance;

    private Map<String, Evenement> evenements;
    private Map<String, List<ParticipantObserver>> observateurs;

    private GestionEvenements() {
        this.observateurs = new HashMap<>();
        evenements = new HashMap<>();
    }

    public static synchronized GestionEvenements getInstance() {
        if (instance == null) {
            instance = new GestionEvenements();
        }
        return instance;
    }
    public Map<String, Evenement> getEvenements() {
        return evenements;
    }
    public void setEvenements(Map<String, Evenement> evenements) {
        this.evenements = evenements;
    }

    public Map<String, List<ParticipantObserver>> getObservateurs() {
        return observateurs;
    }
    public void setObservateurs(Map<String, List<ParticipantObserver>> nouveaux) {
        this.observateurs.clear();
        this.observateurs.putAll(nouveaux);
    }



    public void ajouterEvenement(Evenement evenement) {
        evenements.put(evenement.getId(), evenement);
        observateurs.put(evenement.getId(), new ArrayList<>());
    }

    public boolean supprimerEvenement(String id) {
        if (evenements != null) {
            System.out.println("Contenu actuel de evenements :");
            for (Map.Entry<String, Evenement> entry : evenements.entrySet()) {
                System.out.println("Clé = " + entry.getKey() + ", Valeur = " + entry.getValue());
            }

        } else {
            System.out.println("La collection evenements est nulle !");
        }

        if (observateurs != null) {
            System.out.println("\nContenu actuel de observateurs :");
            for (Map.Entry<String, List<ParticipantObserver>> entry : observateurs.entrySet()) {
                System.out.println("Clé (ID événement) = " + entry.getKey());
                System.out.println("Liste d'observateurs :");
                List<ParticipantObserver> liste = entry.getValue();
                if (liste.isEmpty()) {
                    System.out.println("  Aucun observateur.");
                } else {
                    for (ParticipantObserver obs : liste) {
                        System.out.println("  - " + obs);
                    }
                }
            }
        } else {
            System.out.println("La map observateurs est null.");
        }

        System.out.println("Tentative de suppression de l'événement avec ID = " + id);

        if (evenements.containsKey(id)) {
            evenements.remove(id);
            notifierObservateurs(id, "L'événement '" + id + "' a été annulé.");
            observateurs.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void notifierObservateurs(String idEvenement, String message) {
        List<ParticipantObserver> liste = observateurs.get(idEvenement);
        if (liste != null) {
            for (ParticipantObserver p : liste) {
                p.recevoirNotification(message);
            }
        }
    }

    @Override
    public void ajouterObservateur(String idEvenement, ParticipantObserver p) {
        observateurs.computeIfAbsent(idEvenement, k -> new ArrayList<>()).add(p);
    }

    @Override
    public void supprimerObservateur(String idEvenement, ParticipantObserver p) {
        if (observateurs.containsKey(idEvenement)) {
            observateurs.get(idEvenement).remove(p);
        }
    }

    public Evenement rechercherEvenement(String Id) {
        return evenements.get(Id);
    }

    public boolean modifierEvenement(String id, String nom, String lieu, int capacite) {
        Evenement e = evenements.get(id);
        if (e != null) {
            e.setNom(nom);
            e.setLieu(lieu);
            e.setCapaciteMax(capacite);
            notifierObservateurs(id, "L'événement '" + id + "' a été modifié.");
            return true;
        }
        return false;
    }

    // ObjectMapper global configuré pour toute la classe
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerSubtypes(Conference.class, Concert.class);
    }

    public boolean SauvegardeEvenement(Map<String, Evenement> evenements, String cheminFichier) throws IOException {
        mapper.writerFor(new TypeReference<Map<String, Evenement>>() {})
                .withDefaultPrettyPrinter()
                .writeValue(new File(cheminFichier), evenements);
        return true;
    }

    public void initialiserObservateursDepuisEvenements() {
        Map<String, List<ParticipantObserver>> map = new HashMap<>();
        for (String id : evenements.keySet()) {
            map.put(id, new ArrayList<>()); // Initialiser une liste vide pour chaque événement
        }
        setObservateurs(map); // Appel au setter
    }

    public Map<String, Evenement> chargerDepuisJson(String cheminFichier) {
        try {
            File fichier = new File(cheminFichier);

            if (!fichier.exists() || fichier.length() == 0) {
                System.out.println("Fichier JSON inexistant ou vide, aucun événement chargé.");
                return new HashMap<>();
            }

            return mapper.readValue(fichier, new TypeReference<Map<String, Evenement>>() {});
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier JSON : " + e.getMessage());
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
