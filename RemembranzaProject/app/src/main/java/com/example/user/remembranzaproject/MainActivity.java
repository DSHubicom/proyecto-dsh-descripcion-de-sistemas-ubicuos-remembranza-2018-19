package com.example.user.remembranzaproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toobar configuration
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.fab = (FloatingActionButton) findViewById(R.id.new_route);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(getVisibleFragment().getTag().equals("DESTINATIONS")){
                    Intent intent = new Intent(MainActivity.this, AddDestination.class);
                    intent.putExtra("fragment", "DESTINATIONS");
                    startActivity(intent);
                    //fragmentManager.beginTransaction().replace(R.id.container, new NewDestination(), "NEW DESTINATION").commit();
                }else if(getVisibleFragment().getTag().equals("MEMENTOS")){
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, CreateRoute.class);
                    intent.putExtra("fragment", getVisibleFragment().getTag());
                    startActivity(intent);
                    //fragmentManager.beginTransaction().replace(R.id.container, new NewRoute(), "NEW ROUTE").commit();
                }
            }
        });

        //Opciones del Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Checks which fragment start
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new Main(),"TODAY").commit();
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            Log.w("extras form intent", "received: "+intent.getExtras().get("fragment").toString());
            switch(intent.getExtras().get("fragment").toString()){
                case "DESTINATIONS":{
                    Log.w("inout", "detected");
                    fragmentManager.beginTransaction().replace(R.id.container, new Destinations(),"DESTINATIONS").commit();
                    Log.w("inout", "post detected");
                }
                case "TODAY":{
                    Log.w("inout", "today detected");
                    fragmentManager.beginTransaction().replace(R.id.container, new Main(),"TODAY").commit();
                }
                case "ROUTES":{
                    fragmentManager.beginTransaction().replace(R.id.container, new Main(),"TODAY").commit();
                }

            }
        }else{
            fragmentManager.beginTransaction().replace(R.id.container, new Main(),"TODAY").commit();
        }
        */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        this.fab.setImageResource(R.drawable.route);

        if (id == R.id.today) {
            fragmentManager.beginTransaction().replace(R.id.container, new Main(), "TODAY").commit();
        } else if (id == R.id.routes) {
            fragmentManager.beginTransaction().replace(R.id.container, new Routes(), "ROUTES").commit();
        } else if (id == R.id.destinations) {
            //Change the Action Button Image to add destination
            this.fab.setImageResource(R.drawable.new_destination);
            fragmentManager.beginTransaction().replace(R.id.container, new Destinations(), "DESTINATIONS").commit();
        } else if (id == R.id.stadistics) {
            fragmentManager.beginTransaction().replace(R.id.container, new Stats(), "STATS").commit();
        } else if (id == R.id.options) {
            Intent intent = new Intent(MainActivity.this, Options.class);
            startActivity(intent);
        } else if (id == R.id.warnings) {
            fragmentManager.beginTransaction().replace(R.id.container, new Warnings(), "WARNINGS").commit();
        } else if (id == R.id.mementos){
            this.fab.setImageResource(R.drawable.memento);
            fragmentManager.beginTransaction().replace(R.id.container, new Mementos(), "MEMENTOS").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}