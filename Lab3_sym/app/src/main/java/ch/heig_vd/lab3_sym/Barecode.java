package ch.heig_vd.lab3_sym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Barecode extends AppCompatActivity {

    private TextView txt_scan = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barecode);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            txt_scan = (TextView)findViewById(R.id.txt_scan);
            txt_scan.setText(scanResult.getContents());
        }
        // else continue with any other code you need in the method
    }
}
