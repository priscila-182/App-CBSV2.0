package com.example.app_cbsv20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.app_cbsv20.HomeAdapter.AllBooksAdapter;
import com.example.app_cbsv20.HomeAdapter.AllBooksHelpedClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class LibrosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    static final float END_SCALE = 0.7f;

    private ProgressDialog progressDialog;

    RecyclerView allBooksRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1;
    ImageView menuIcon;
    LinearLayout contentView;

    //Sección de menú

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        init();

        showPDialog1();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },1250);


        Button btnUbicaciones = (Button) findViewById(R.id.btnVerUbicaciones);
        btnUbicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Opción no disponible",Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPdf = (Button)findViewById(R.id.btnVerPdf);
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Opción no disponible",Toast.LENGTH_SHORT).show();
            }
        });

        allBooksRecycler = findViewById(R.id.all_books_recycler);
        menuIcon = findViewById(R.id.menu_icono);
        contentView = findViewById(R.id.content);

        //menu hooks

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navegation_view);


        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_inicio);

        navigationDrawer();

        //recycler view function calls

        allBooksRecycler();


    }

    private void init() {
        this.progressDialog = new ProgressDialog(this);
    }

    private void showPDialog1(){
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Cargando");
        progressDialog.setMessage("Por favor, espere");
        progressDialog.show();
    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                final float diffScaleOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaleOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaleOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    private void navigationDrawer() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Libros);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    //Información de libros

    private void allBooksRecycler() {

        allBooksRecycler.setHasFixedSize(true);
        allBooksRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<AllBooksHelpedClass> allBooksLocations = new ArrayList<>();

        allBooksLocations.add(new AllBooksHelpedClass(R.drawable.soyleyenda, "Soy Leyenda", "Autor: Richard Matheson", "Género: Ciencia Ficción", "Robert Neville es el único superviviente de una guerra bacteriológica que ha asolado el planeta y convertido al resto de la humanidad en vampiros. Su vida se ha reducido a asesinar el máximo número posible de estos seres sanguinarios durante el día, y a soportar su asedio cada noche.", "Puntuación: "));
        allBooksLocations.add(new AllBooksHelpedClass(R.drawable.sangreycenizas, "De Sangre y Cenizas", "Jennifer L. Armentrout", "Género: Fantasía","Una Doncella elegida desde su nacimiento para comenzar una nueva era...", "Puntuación: "));
        allBooksLocations.add(new AllBooksHelpedClass(R.drawable.elresplandor, "El resplandor", "Stephen King","Género: Horror","Jack Torrance acepta una oferta de trabajo en un hotel de montaña que se encuentra a 65 kilómetros del...", "Puntuación: "));
        allBooksLocations.add(new AllBooksHelpedClass(R.drawable.cumbresborrascosas, "Cumbres Borrascosas", "Emily Bronte",  "Género: Romance","Cumbres borrascosas constituye una asombrosa visión metafísica del destino, la obsesión, la pasión y la venganza...", "Puntuación: "));
        allBooksLocations.add(new AllBooksHelpedClass(R.drawable.lachicadeltren, "La Chica del Tren",  "Autor: Paula Hawkins", "Género: Thriller","Rachel Watson se divorcia del marido que la engaña y se siente sola y deprimida. No tiene trabajo y...", "Puntuación: "));

        adapter = new AllBooksAdapter(allBooksLocations);
        allBooksRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.nav_inicio){
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();
        } else if (id == R.id.nav_buscar){

        } else if (id == R.id.nav_lugares) {

        } else if (id == R.id.nav_Libros){
            startActivity(new Intent(this, LibrosActivity.class));
            finish();
        } else if (id == R.id.nav_categorias){
            startActivity(new Intent(this, CategoriasActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}