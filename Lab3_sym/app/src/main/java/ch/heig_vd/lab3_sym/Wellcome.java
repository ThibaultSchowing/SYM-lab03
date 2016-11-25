package ch.heig_vd.lab3_sym;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Wellcome extends AppCompatActivity {

    private Button btn_secret = null;
    private Button btn_conf = null;
    private Button btn_info = null;

    private TextView txt_infos = null;
    private TextView txt_welcome = null;

    //Milisecondes max avant déconnexion (début du countdown)
    private final int maxMs = 60000;
    private long seconds = 0;

    // Niveaux de sécurité
    private final String AUTH_MAX = "AUTHENTICATE_MAX";
    private final String AUTH_MED = "AUTHENTICATE_MED";
    private final String AUTH_MIN = "AUTHENTICATE_MIN";
    private String currentLevel = "";

    private final String secret = "Secret information: I love cake !";
    private final String conf = "Confidential information: You're beautiful !";
    private final String info = "Informational information: Beer !";

    private CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        btn_secret = (Button) findViewById(R.id.btn_secret);
        btn_conf = (Button) findViewById(R.id.btn_conf);
        btn_info = (Button) findViewById(R.id.btn_info);

        txt_infos = (TextView) findViewById(R.id.txt_infos);
        txt_welcome = (TextView) findViewById(R.id.txt_welcome);

        // Secondes totales pour accéder "l'information"



        //TODO démarer un timer, à la fin du compteur retourner au login

        timer = new CountDownTimer(maxMs, 1000) {

            public void onTick(long millisUntilFinished) {
                seconds = millisUntilFinished/1000;


                // Le tiers le plus grand du pool de seconde = AuthHigh, le tiers du milieu = medium et le dernier tiers = low
                if(seconds >= ((maxMs/1000)/3)*2){
                    currentLevel = AUTH_MAX;
                }
                else if( seconds < ((maxMs/1000)/3)*2 && seconds >= ((maxMs/1000)/3)){
                    currentLevel = AUTH_MED;
                }
                else if (seconds < ((maxMs/1000)/3)){
                    currentLevel = AUTH_MIN;
                }

                txt_welcome.setText("Seconds remaining: " + seconds + " - " + currentLevel);

            }

            public void onFinish() {
                txt_welcome.setText("done!");
            }
        }.start();




        //On click listeners - choix de l'information à accéder

        btn_secret.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });

        btn_conf.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });

        btn_info.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

            }
        });

    }
}
