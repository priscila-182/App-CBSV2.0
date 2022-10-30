package com.example.app_cbsv20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class IngresarLibroActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listaLibros1;

    static final float END_SCALE = 0.7f;

    private ProgressDialog progressDialog;

    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1;
    ImageView menuIcon;
    LinearLayout contentView;

    //Sección de menú

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    /////// variables para ingresar datos de libros ///////

    private EditText edTitulo, edAutor, edGenero, edPaginas, edDescripcion;
    private Button btnGuardarLibro, btnVerLibros;

    /////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_libro);

        ///////////////////////////

        edTitulo = findViewById(R.id.txtTitulo);
        edAutor = findViewById(R.id.txtAutor);
        edGenero = findViewById(R.id.txtGenero);
        edPaginas = findViewById(R.id.txtNumeroPaginas);
        edDescripcion = findViewById(R.id.txtDescripcion);

        btnGuardarLibro = findViewById(R.id.btnGuardarLibro);
        btnVerLibros = findViewById(R.id.btnVerLibros);

        btnVerLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LibrosActivity.class);
                startActivity(i);
            }
        });

        btnGuardarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });

        ///////////////////////////

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

        //recycler view function calls



    }

    private void insertar() {
        try {
            String titulo = edTitulo.getText().toString();
            String autor = edAutor.getText().toString();
            String genero = edGenero.getText().toString();
            String paginas = edPaginas.getText().toString();
            String descripcion = edDescripcion.getText().toString();

            SQLiteDatabase db = openOrCreateDatabase("BD_CBS", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS libro(id INTEGER PRIMARY KEY AUTOINCREMENT,titulo VARCHAR,autor VARCHAR,genero VARCHAR,paginas VARCHAR,descripcion VARCHAR)");

            String sql = "insert into libro(titulo,autor,genero,paginas,descripcion)values(?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1,titulo);
            statement.bindString(2,autor);
            statement.bindString(3,genero);
            statement.bindString(4,paginas);
            statement.bindString(5,descripcion);
            statement.execute();
            Toast.makeText(this,"Guardado",Toast.LENGTH_LONG).show();

            edTitulo.setText("");
            edAutor.setText("");
            edGenero.setText("");
            edPaginas.setText("");
            edDescripcion.setText("");
            edTitulo.requestFocus();

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
        navigationView.setCheckedItem(R.id.nav_IngresarLibro);

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