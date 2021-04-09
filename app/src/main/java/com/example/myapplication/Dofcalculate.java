package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Model.DepthofFieldCalculator;
import com.example.myapplication.Model.Lens;
import com.example.myapplication.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Dofcalculate extends AppCompatActivity {

    public static Intent makelunchintent(Context c, int pos){
        Intent intent = new Intent(c,Dofcalculate.class);
        intent.putExtra("position", pos);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dofcalculate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firstenter();
        Edit();
        Delete();
        Calculate();
    }

    private void Delete() {
        Button btn = (Button) findViewById(R.id.delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int pos = intent.getIntExtra("position",0);
                LensManager lenses = LensManager.getInstance();
                lenses.remove(pos);
                Intent i = new Intent(Dofcalculate.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void Edit() {
        Button btn = (Button) findViewById(R.id.edit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int pos = intent.getIntExtra("position",0);
                Intent i = AddLen.makelunchintent(Dofcalculate.this,pos);
                startActivity(i);
            }
        });
    }

    private void Firstenter() {
        TextView nfd = (TextView) findViewById(R.id.ansNearfocal);
        TextView ffd = (TextView) findViewById(R.id.ansFarfocal);
        TextView dof = (TextView) findViewById(R.id.ansDepth);
        TextView hyd = (TextView) findViewById(R.id.ansHyperfocal);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position",0);
        LensManager lenses = LensManager.getInstance();

        TextView type = (TextView) findViewById(R.id.type);
        nfd.setText("Enter all values");
        ffd.setText("Enter all values");
        dof.setText("Enter all values");
        hyd.setText("Enter all values");

        type.setText(lenses.output(pos));

    }
    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
    private void Calculate() {
        Button btn = findViewById(R.id.claculate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int pos = intent.getIntExtra("position",0);
                LensManager lenses = LensManager.getInstance();
                Lens l = lenses.getLens(pos);

                EditText coc = (EditText) findViewById(R.id.circle);
                EditText dos = (EditText) findViewById(R.id.distancesub);
                EditText sea = (EditText) findViewById(R.id.selaperture);
                double Coc = Double.parseDouble(coc.getText().toString());
                double Dos = Double.parseDouble(dos.getText().toString());
                double Sea = Double.parseDouble(sea.getText().toString());

                if (Coc <= 0 || Dos <= 0 || Sea < 1.4){
                    String message = "(Confusion must > 0, Distance > 0, aperture should >= 1.4)";
                    Toast.makeText(Dofcalculate.this,message,Toast.LENGTH_LONG).show();
                }
                else {
                    TextView nfd = (TextView) findViewById(R.id.ansNearfocal);
                    TextView ffd = (TextView) findViewById(R.id.ansFarfocal);
                    TextView dof = (TextView) findViewById(R.id.ansDepth);
                    TextView hyd = (TextView) findViewById(R.id.ansHyperfocal);

                    if (l.getMax_aperture() > Sea) {
                        nfd.setText("Invalid aperture");
                        ffd.setText("Invalid aperture");
                        dof.setText("Invalid aperture");
                        hyd.setText("Invalid aperture");
                    } else if (Coc == 0) {
                        nfd.setText("Nan");
                        ffd.setText("Nan");
                        dof.setText("Nan");
                        hyd.setText("Nan");
                    } else {
                        DepthofFieldCalculator DoF = new DepthofFieldCalculator(l);
                        Dos = Dos * 1000;
                        double hyper = DoF.Hyperfocal_distance(l.getFocal_length(), Sea);
                        double Near = DoF.Near_focal_point(l.getFocal_length(), hyper, Dos);
                        double Far = DoF.Far_focal_point(l.getFocal_length(), hyper, Dos);
                        String hyperfpcal = formatM((double) hyper / 1000);
                        String near = formatM((double) Near / 1000);
                        String far = formatM((double) Far / 1000);
                        String DOF = formatM((double) DoF.Depth_of_field(Near, Far) / 1000);

                        nfd.setText(near + "m");
                        ffd.setText(far + "m");
                        dof.setText(DOF + "m");
                        hyd.setText(hyperfpcal + "m");
                    }
                }
            }
        });
    }
}