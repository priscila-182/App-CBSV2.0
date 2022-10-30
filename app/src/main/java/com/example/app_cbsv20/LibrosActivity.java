package com.example.app_cbsv20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    ///////////////////////////

    private ListView listaLibros;
    private ArrayList<String> arreglo = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;

    //////////////////////////

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
        },1400);

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

        //////////////////////////////////////

        try {
            SQLiteDatabase db = openOrCreateDatabase("BD_CBS", Context.MODE_PRIVATE, null);
            listaLibros = findViewById(R.id.listaLibros1);
            final Cursor c = db.rawQuery("select * from libro", null);
            int id = c.getColumnIndex("Libro: ");
            int titulo = c.getColumnIndex("Titulo: ");
            int autor = c.getColumnIndex("Autor: ");
            int genero = c.getColumnIndex("Género: ");
            int paginas = c.getColumnIndex("Páginas: ");
            int descripcion = c.getColumnIndex("Descripción: ");
            arreglo.clear();

            listaLibros.setAdapter(arrayAdapter);

            final ArrayList<Libro> lista = new ArrayList<Libro>();

            if (c.moveToFirst()) {
                do {
                    Libro libro = new Libro();
                    libro.id = c.getString(id);
                    libro.titulo = c.getString(titulo);
                    libro.autor = c.getString(autor);
                    libro.genero = c.getString(genero);
                    libro.paginas = c.getString(paginas);
                    libro.descripcion = c.getString(descripcion);

                    arreglo.add(c.getString(id) + "\t" + c.getString(titulo) + "\t" + c.getString(autor) + "\t" + c.getString(genero) + "\t" + c.getString(paginas) + "\t" + c.getString(descripcion));

                } while (c.moveToNext());
                arrayAdapter.notifyDataSetChanged();
                listaLibros.invalidateViews();
            }
            listaLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, android.view.View view, int position, long l) {
                    Libro libro = lista.get(position);
                    Intent i = new Intent(getApplicationContext(), EditarDatosActivity.class);
                    i.putExtra("Libro: ",libro.id);
                    i.putExtra("Titulo: ",libro.titulo);
                    i.putExtra("Autor: ",libro.autor);
                    i.putExtra("Genero: ",libro.genero);
                    i.putExtra("Paginas: ",libro.paginas);
                    i.putExtra("Descripción: ",libro.descripcion);
                    startActivity(i);
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Error: Datos no guardados", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.nav_inicio){
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();

        } else if (id == R.id.nav_lugares) {
            startActivity(new Intent(this, MapsActivity.class));
            finish();

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