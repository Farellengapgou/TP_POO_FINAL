package org.example;

public interface EvenementObservable {
    void ajouterObservateur(String idEvenement, ParticipantObserver p);
    void supprimerObservateur(String idEvenement, ParticipantObserver p);
    void notifierObservateurs(String idEvenement, String message);
}
