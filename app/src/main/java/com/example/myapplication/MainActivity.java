package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Model.Lens;
import com.example.myapplication.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 42;
    private Lens Len;
    private LensManager lenses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = AddLen.makelunchintent(MainActivity.this,-1);
            startActivityForResult(i,REQUEST_CODE);
        });
        lenses = LensManager.getInstance();
        if (lenses.size() == 0 && lenses.getCheck() == 0) {
            lenses.add(new Lens("Canon", 1.8, 50, R.drawable.ic_baseline_brightness_2_24));
            lenses.add(new Lens("Tamron", 2.8, 90, R.drawable.ic_baseline_brightness_4_24));
            lenses.add(new Lens("Sigma", 2.8, 200, R.drawable.ic_baseline_brightness_5_24));
            lenses.add(new Lens("Nikon", 4, 200, R.drawable.ic_baseline_brightness_7_24));
            lenses.setCheck(1);
        }
        if (lenses.size() == 0 && lenses.getCheck() == 1) {
            String message = "Create a new Len by clicking the adding FloatingActionButton.";
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }
        populateListView();
        registerClickCallback();
    }
    private List<Lens> myItems;
    private void populateListView(){
        // Create list of items
//        for (int i = 0 ; i < lenses.size(); i++){
//            myItems.add(lenses.output(i));
//        }
        myItems = new ArrayList<Lens>();
        for (int i = 0 ; i  <lenses.size();i++){
            myItems.add(lenses.getLens(i));
        }

        ArrayAdapter <Lens> adapter = new MylistAdapter();   // Items to be displayed

        // Configure the list view.
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setAdapter(adapter);
    }
    private class MylistAdapter extends ArrayAdapter<Lens> {
        public MylistAdapter() {
            super(MainActivity.this,R.layout.len_item,myItems);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.len_item,parent,false);
            }

            Lens currentLens = myItems.get(position);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentLens.geticonID());

            TextView textmake = (TextView) itemView.findViewById(R.id.textMake);
            textmake.setText(currentLens.getMake());

            TextView textfocal = (TextView) itemView.findViewById(R.id.textFocal);
            textfocal.setText("Focal length:" + currentLens.getFocal_length());

            TextView textAperture = (TextView) itemView.findViewById(R.id.textAperture);
            textAperture.setText("Aperture:" + currentLens.getMax_aperture());
            return itemView;
//            return super.getView(position, convertView, parent);
        }
    }
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewMain);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent i = Dofcalculate.makelunchintent(MainActivity.this,position);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_CANCELED){
            return;
        }
        switch (requestCode){
            case REQUEST_CODE:
//                for (int i = 0 ; i < lenses.size(); i++){
//                    myItems.add(lenses.output(i));
//                }
                myItems = new ArrayList<Lens>();
                for (int i = 0 ; i  <lenses.size();i++){
                    myItems.add(lenses.getLens(i));
                }

//                adapter = new ArrayAdapter<String>(
//                        this,     // Context for the activity.
//                        R.layout.len_item,  // Layout to use (create)
//                        myItems);   // Items to be displayed

                ArrayAdapter <Lens> adapter = new MylistAdapter();
                // Configure the list view.
                ListView list = (ListView) findViewById(R.id.listViewMain);
                list.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}