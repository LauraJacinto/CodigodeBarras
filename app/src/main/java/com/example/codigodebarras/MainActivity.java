package com.example.codigodebarras;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Scanner(View v){
        scanCode();
    }

    public void scanCode(){
        //Mensaje que se muestra al pulsar nuestro boton que nos indica que el proceso de
        //escane de nuestra aplicacion esta por inciarse
        Toast.makeText(getApplicationContext(), "Iniciando escaneo" ,
                Toast.LENGTH_SHORT).show();

        IntentIntegrator integrador= new IntentIntegrator(this);
        //Activity que es la encargada de mostranos el resultado del escaneo
        integrador.setCaptureActivity(CapturaClass.class);
        integrador.setOrientationLocked(false);
        integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        //mensaje  que nos indica que se esga llevando acabo un proceso de escaneo
        integrador.setPrompt("Escanenado.....");
        integrador.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult resultado= IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(resultado != null){
            if(resultado.getContents()!=null){
                AlertDialog.Builder constructor= new AlertDialog.Builder(this);
                constructor.setMessage(resultado.getContents());
                //Titulo de nuestro cuadro que noes muestra el resultado obtenido del escaneo
                constructor.setTitle("Escaneando el resultado");
                //es el contructor encargado de preguntar si deceamos realizar otro escaneo de
                //codigo de barras o QR en dado caso
                constructor.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scanCode();
                        //En caso de ya  o querer realiza algun otro escane nuetra actividad en la
                        //aplicacion termina y ser cierra
                    }
                }).setNegativeButton("Finalizando", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                AlertDialog dialogo=constructor.create();
                dialogo.show();
            }else{
                //mensaje que se nos muestra al no obtener resultado de escaneo
                Toast.makeText(this, "No existen resultado", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode,data);
        }
    }
}