package ch.heig_vd.lab3_sym;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barecode);

        final IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();


        txt_scan = (TextView)findViewById(R.id.txt_scan);
        btn_rescan = (Button) findViewById(R.id.btn_rescan);

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
        }
        // else continue with any other code you need in the method
    }
}
