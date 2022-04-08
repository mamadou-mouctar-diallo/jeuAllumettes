package fr.unice.l3.allumettes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.unice.l3.allumettes.engine.JeuDesAllumettes;
import fr.unice.l3.allumettes.player.Joueur;
import fr.unice.l3.allumettes.view.Allumettes;
import fr.unice.l3.matchesgame.control.Controleur;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private Allumettes gameView;
    private Controleur c;
    private ScrollView scrollView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.gameView = findViewById(R.id.allumettes);
        JeuDesAllumettes jeu = new JeuDesAllumettes(21);
        jeu.démarrerNouvellePartie();
        jeu.ajouterJoueur(new Joueur());
        jeu.ajouterJoueur(new Joueur());
        this.gameView.setTotal(jeu.getNombreAllumettesTotal());
        this.c = new Controleur(jeu, this);
        findViewById(R.id.commencer).setOnClickListener(this);
        this.scrollView = findViewById(R.id.scroll);
        this.textView = findViewById(R.id.message);
    }

    @Override
    public void onClick(View view) {
        c.start();
//        c.stop();
    }
    public void updateView(String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(message);
                textView.append("\n\n");
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

    }
    public Allumettes getGameView(){
        return   this.gameView;
    }
}