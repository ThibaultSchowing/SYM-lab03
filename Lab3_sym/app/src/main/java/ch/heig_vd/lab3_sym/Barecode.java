package ch.heig_vd.lab3_sym;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Barecode extends AppCompatActivity {

    private TextView txt_scan = null;
    private Button btn_rescan = null;

    private Button btn_secret = null;
    private Button btn_conf = null;
    private Button btn_info = null;

    private TextView txt_welcome = null;


    // Niveaux de sécurité
    private final String AUTH_MAX = "AUTHENTICATE_MAX";
    private final String AUTH_MED = "AUTHENTICATE_MED";
    private final String AUTH_MIN = "AUTHENTICATE_MIN";
    private final String AUTH_NULL = "AUTHENTICATE_NULL";

    private final int AUTH_MAX_TIME = 10000;
    private final int AUTH_MED_TIME = 20000;
    private final int AUTH_MIN_TIME = 30000;
    private boolean firsttime = true ;

    private final CharSequence text1 = "Secret information ! Valdor au lait cru AOC ROHMILCH !!";
    private final CharSequence text2 = "Confidential information ! Fontaine à Absinth BORDEL !!";
    private final CharSequence text3 = "Information ! Inscrivez-vous à la R.A.C.L.E.T.T.E. !!";


    private String currentLevel = "";


    private int clickTime = 0, connexionTime = 0, diffTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barecode);

        final IntentIntegrator integrator = new IntentIntegrator(this);
        if(firsttime)
        {
            integrator.initiateScan();
            firsttime = false;
        }


        txt_scan = (TextView)findViewById(R.id.txt_scan);
        btn_secret = (Button) findViewById(R.id.btn_secret);
        btn_conf = (Button) findViewById(R.id.btn_conf);
        btn_info = (Button) findViewById(R.id.btn_info);
        btn_rescan = (Button) findViewById(R.id.btn_rescan);

        txt_welcome = (TextView) findViewById(R.id.txt_welcome);

        connexionTime = (int) (System.currentTimeMillis());

        currentLevel = calculateAuthLevel();
        txt_welcome.setText("Auth level: " + currentLevel);


        btn_secret.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                currentLevel = calculateAuthLevel();
                Context context = getApplicationContext();
                CharSequence text = "Please reauthenticate !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                if(currentLevel == AUTH_MAX){
                    try {
                        getAppropriateInfo(currentLevel);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    txt_welcome.setText("Auth level: " + currentLevel);
                    toast.show();
                }

            }
        });

        btn_conf.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                currentLevel = calculateAuthLevel();
                Context context = getApplicationContext();
                CharSequence text = "Please reauthenticate !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                if(currentLevel == AUTH_MED || currentLevel == AUTH_MAX){
                    try {
                        getAppropriateInfo(AUTH_MED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    txt_welcome.setText("Auth level: " + currentLevel);
                    toast.show();
                }
            }
        });

        btn_info.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                currentLevel = calculateAuthLevel();
                Context context = getApplicationContext();
                CharSequence text = "Please reauthenticate !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                if(currentLevel == AUTH_MIN || currentLevel == AUTH_MED || currentLevel == AUTH_MAX){
                    try {
                        getAppropriateInfo(AUTH_MIN);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    txt_welcome.setText("Auth level: " + currentLevel);
                    toast.show();
                }
            }
        });

        btn_rescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integrator.initiateScan();
            }
        });



    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            txt_scan.setText(scanResult.getContents());
            readTag(scanResult.getContents());

        }
        // else continue with any other code you need in the method
    }

    private void readTag(String tag) {
        if(tag != null && tag.contains("test")){
            connexionTime = (int) (System.currentTimeMillis());
            currentLevel = calculateAuthLevel();
            txt_welcome.setText("Auth level: " + currentLevel);
        }
    }
    private String calculateAuthLevel(){

        clickTime = (int) (System.currentTimeMillis());
        diffTime = clickTime - connexionTime;

        if(diffTime >= 0 && diffTime <= AUTH_MAX_TIME){
            return AUTH_MAX;
        }
        else if(diffTime > AUTH_MAX_TIME && diffTime <= AUTH_MED_TIME){
            return AUTH_MED;
        }
        else if(diffTime > AUTH_MED_TIME && diffTime <= AUTH_MIN_TIME){
            return AUTH_MIN;
        }
        else {
            //else if(diffTime > AUTH_MIN_TIME){
            return AUTH_NULL;
        }
    }

    private void getAppropriateInfo(String level) {
        switch (level){
            case AUTH_MAX:
                txt_welcome.setText("Auth level: " + level);

                //toast1.show();

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Top Secret");
                alertDialog.setMessage(text1);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                break;
            case AUTH_MED:
                txt_welcome.setText("Auth level: " + level);

                //toast2.show();
                AlertDialog alertDialog2 = new AlertDialog.Builder(this).create();
                alertDialog2.setTitle("Confidential");
                alertDialog2.setMessage(text2);
                alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog2.show();

                break;
            case AUTH_MIN:
                txt_welcome.setText("Auth level: " + level);

                //toast3.show();
                AlertDialog alertDialog3 = new AlertDialog.Builder(this).create();
                alertDialog3.setTitle("Information");
                alertDialog3.setMessage(text3);
                alertDialog3.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog3.show();

                break;
            case AUTH_NULL:
                this.finish();
        }
    }
}
