package eu.apglos.apglospositionreader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static TextView LBL_location;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LBL_location = findViewById(R.id.LBL_location);
        Intent serviceIntent = new Intent(this, SampleReceiver.class);
        startService(serviceIntent);
    }
}
