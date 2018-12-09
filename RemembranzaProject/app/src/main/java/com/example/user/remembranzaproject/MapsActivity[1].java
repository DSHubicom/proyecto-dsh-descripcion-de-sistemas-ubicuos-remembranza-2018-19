package com.example.user.remembranzaproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.remembranzaproject.database.AppDatabase;
import com.example.user.remembranzaproject.database.Memento;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Actives back button in action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Add a memento");
        ab.setDisplayHomeAsUpEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button button = (Button) findViewById(R.id.add_memento);
        Log.w("memento_checking", "");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = findViewById(R.id.title);
                TextView x_coordinate = findViewById(R.id.x_coordinate);
                TextView y_coordinate = findViewById(R.id.y_coordinate);

                Log.w("memento_adding", "title: "+title.getText().toString());

                Memento memento = new Memento(title.getText().toString(), Float.parseFloat(x_coordinate.getText().toString()), Float.parseFloat(y_coordinate.getText().toString()));
                try {
                    memento.setId_memento(new add_new_memento().execute(memento).get());
                    Log.w("memento added:", ""+memento.getId_memento());

                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
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
                intent.putExtra("fragment", "MEMENTOS");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class add_new_memento extends AsyncTask<Memento, Void, Long> {
        @Override
        protected Long doInBackground(Memento... mementos) {
            AppDatabase appDatabase = AppDatabase.getDatabase(MapsActivity.this);
            return appDatabase.mementoDao().insertDestination(mementos[0]);
        }
    }
}