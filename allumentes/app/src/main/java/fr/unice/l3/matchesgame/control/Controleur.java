package fr.unice.l3.matchesgame.control;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import fr.unice.l3.allumettes.GameActivity;
import fr.unice.l3.allumettes.engine.JeuDesAllumettes;
import fr.unice.l3.allumettes.player.Joueur;

public class Controleur {
    private JeuDesAllumettes jeu;
    private GameActivity gameActivity;
    private AsyncTask<Void, String, String> partie;
    public Controleur(JeuDesAllumettes jeu, GameActivity gameActivity){
        this.jeu = jeu;
        this.gameActivity = gameActivity;
    }

    public void start(){
        this.partie = new AsyncTask<Void, String, String>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    publishProgress("Debut de partie.");
                    // Tant que la partie du jeu d'allumettes est en cours (pas encore de gagnant)
                    while(jeu.partieEnCours()) {
                        // Quel est le joueur dont c'est le tour ?
                        Joueur j = jeu.aQuiDeJouer();
                        // Combien d'allumettes sont choisies lors de ce tour de jeu ?
                        int nbAllumettesChoisies = jeu.jouerUnTour();
                        // 1er message : on affiche le coup (allumettes choisies)
                        publishProgress("Joueur : " + jeu.joueurCourant()+ " choisit " + nbAllumettesChoisies + " allumettes.");

                        gameActivity.getGameView().setSelectedCount(nbAllumettesChoisies);

                        // Marquer une pause pour laisser le temps de voir le coup
                        j.attendre();

                        int nbAllumettesVisibles = jeu.getNombreAllumettesVisible();
                        // 2e message : on affiche le résultat de ce tour (allumettes restantes)
                        publishProgress("Il reste " + nbAllumettesVisibles +" Allumettes");

                        gameActivity.getGameView().setVisibleCount(nbAllumettesVisibles);

                        publishProgress("***********");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Partie", "Fin de partie.");
                // Afficher le gagnant
                Log.d("Partie", "Gagnant : " + jeu.getGagnant());

                return "Le gagnant est ...";
            }
            @Override
            protected void onProgressUpdate(String[] values) {
                super.onProgressUpdate(values);
                String message = values[0];
                Log.d("message", message);
                gameActivity.updateView(message);

            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                gameActivity.updateView(s);
            }
        };
        this.partie.execute();

    }

    public void stop(){
        this.partie.cancel(true);
    }

    private abstract class UpdateMessage {
        protected int nbAllumettesChoisies;
        protected Joueur j;

        public UpdateMessage(Joueur j, int nbAllumettesChoisies) {
            this.j = j;
            this.nbAllumettesChoisies = nbAllumettesChoisies;
        }

        public abstract void updateView();
    }
    public class MainUpdateMessage extends UpdateMessage{
        public MainUpdateMessage(Joueur j, int nbAllumettesChoisies){
            super(j, nbAllumettesChoisies);
        }

        @Override
        public void updateView() {

        }
    }
    public class PostUpdateMessage extends UpdateMessage{
        public PostUpdateMessage(Joueur j, int nbAllumettesChoisies){
            super(j, nbAllumettesChoisies);
        }

        @Override
        public void updateView() {

        }
    }

}
