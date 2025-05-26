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

    public void ajouterEvenement(Evenement evenement) {
        evenements.put(evenement.getId(), evenement);
        observateurs.put(evenement.getId(), new ArrayList<>());
    }

    public boolean supprimerEvenement(String id) {
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
