package css.cis3334.fishlocatorfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

public class AddFishActivity extends AppCompatActivity {

    Button buttonSave;
    EditText editTextSpecies, editTextWeight, editTextDate;
    Double lattitude, longitude;
    FishFirebaseData fishDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fish);

        // link each editText variable to the xml layout
        editTextSpecies = (EditText) findViewById(R.id.editTextSpecies);
        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        editTextDate = (EditText) findViewById(R.id.editTextDate);

        fishDataSource = new FishFirebaseData();
        fishDataSource.open();

        // get the current location of the phone
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null) {
                lattitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {
                lattitude = 0.0;
                longitude = 0.0;
            }
        }

            // set up the button listener
            buttonSave = (Button) findViewById(R.id.buttonSave);
            buttonSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Add the fish to the database
                    String species = editTextSpecies.getText().toString();
                    String weight = editTextWeight.getText().toString();
                    String dateCaught = editTextDate.getText().toString();
                    //fishDataSource.createFish(species, weight, dateCaught);
                    fishDataSource.createFish(species, weight, dateCaught, lattitude.toString(), longitude.toString());
                    Intent mainActIntent = new Intent(view.getContext(), MainActivity.class);
                    finish();
                    startActivity(mainActIntent);
                }
            });

        }
    }

