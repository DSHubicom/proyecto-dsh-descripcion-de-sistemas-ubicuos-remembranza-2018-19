package com.example.user.remembranzaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.remembranzaproject.database.AppDatabase;
import com.example.user.remembranzaproject.database.Destination;
import com.example.user.remembranzaproject.database.Memento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

public class AddDestination extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination);

        //Actives back button in action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Add a destination");
        ab.setDisplayHomeAsUpEnabled(true);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button button = (Button) findViewById(R.id.add_destination);
        Log.w("memento_checking", "");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = findViewById(R.id.title);
                TextView x_coordinate = findViewById(R.id.x_coordinate);
                TextView y_coordinate = findViewById(R.id.y_coordinate);

                Destination destination = new Destination(title.getText().toString(), Float.parseFloat(x_coordinate.getText().toString()), Float.parseFloat(y_coordinate.getText().toString()));
                try {
                    new add_new_destination().execute(destination).get();
                    Intent intent = new Intent(AddDestination.this, MainActivity.class);
                    intent.putExtra("fragment", "MEMENTOS");
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(12f);


        LatLng sydney = new LatLng(-34, 151);
        final Marker someMarker = mMap.addMarker(new MarkerOptions()
                .position(sydney).title("Destination").draggable(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                TextView x_coordinate, y_coordinate;
                x_coordinate = findViewById(R.id.x_coordinate);
                y_coordinate = findViewById(R.id.y_coordinate);

                Double y = marker.getPosition().longitude;
                Double x = marker.getPosition().latitude;
                x_coordinate.setText(String.valueOf(x));
                y_coordinate.setText(String.valueOf(y));
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("fragment", "DESTINATIONS");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class add_new_destination extends AsyncTask<Destination, Void, Long> {

        @Override
        protected Long doInBackground(Destination... destinations) {
            AppDatabase appDatabase = AppDatabase.getDatabase(AddDestination.this);
            return appDatabase.destinationDao().insertDestination(destinations[0]);
        }
    }
}
