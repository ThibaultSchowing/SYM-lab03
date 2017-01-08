/*
* Auteur : Thibault Schowing, Amine Tayaa, Simon Baehler
* date : 8 janvier 2017
 */

package ch.heig_vd.lab3_sym;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AbstractNFCActivity {

    public static final String TAG = "NfcDemo";
    public static final String MIME_TEXT_PLAIN = "text/plain";

    private TextView txt_username = null;
    private TextView txt_passwd = null;
    private TextView txt_nfc = null;
    private TextView nfcState = null;
    private TextView NFCinfo = null;
    private Button loginBtn = null;
    private Button barecode = null;
    private Button ibeacon = null;
    private CheckBox fastlogin = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fastlogin = (CheckBox) findViewById(R.id.FastLogin);
        loginBtn = (Button) findViewById(R.id.btn_login);
        barecode = (Button) findViewById(R.id.bareCode);
        ibeacon = (Button) findViewById(R.id.ibeacon);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_passwd = (TextView) findViewById(R.id.txt_passwd);

        // Champ du formulaire ou la donnée du nfc sera entrée
        txt_nfc = (TextView) findViewById(R.id.txt_nfc);

        // Text récuupéré du nfc
        NFCinfo = (TextView) findViewById(R.id.NFCinfo);

        // INfo sur l'état du NFC (activé ou pas)
        nfcState = (TextView) findViewById(R.id.textView_explanation);


        if(savedInstanceState == null){
            txt_username.setText("");
            txt_nfc.setText("");
            txt_passwd.setText("");
        }


        if (!isNFCenabled()) {
            nfcState.setText("NFC is disabled.");
        } else {
            nfcState.setText(R.string.explanation);
        }


        // Pour lancer les deux autres activités du labo
        barecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ch.heig_vd.lab3_sym.Barecode.class);
                startActivity(intent);
            }
        });

        ibeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ch.heig_vd.lab3_sym.Ibeacon.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txt_username.getText().toString();
                String password = txt_passwd.getText().toString();
                String nfc = txt_nfc.getText().toString();
                Boolean fastloginChecked = fastlogin.isChecked();
                Boolean authorized = false;

                if(fastloginChecked && username.equals("a") && nfc.contains("test")){
                    authorized = true;
                }
                else
                    authorized = !fastloginChecked && username.equals("a") && password.equals("a") && nfc.contains("test");

                if(authorized){
                    txt_nfc.setText("");
                    txt_username.setText("");
                    txt_passwd.setText("");
                    Intent intent = new Intent(MainActivity.this, Wellcome.class);
                    startActivity(intent);
                }
                else{
                    CharSequence text = "GO AWAY !!";
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

    }

    @Override
    protected void readTag(String tag) {
        txt_nfc.setText(tag);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

