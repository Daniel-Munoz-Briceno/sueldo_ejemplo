package com.example.sueldo_ejemplo;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {
    private EditText txtHoras, txtDescuento, txtSueldo;
    private CheckBox chbxDcto;
    private RadioGroup rgRedondeo;
    private RadioButton rbRedondeo, rbNoRedondeo;
    private TextView lbl_pago, lbl_dcto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSueldo = (EditText) findViewById(R.id.txtSueldo);
        txtHoras = (EditText) findViewById(R.id.txtHoras);
        txtDescuento = (EditText) findViewById(R.id.txtDescuento);
        chbxDcto = (CheckBox) findViewById(R.id.chbxDcto);
        lbl_dcto = (TextView) findViewById(R.id.lbl_dcto);
        lbl_pago = (TextView) findViewById(R.id.lbl_pago);
        rgRedondeo = (RadioGroup) findViewById(R.id.rgRedondeo);

        //Funcion escucha RadioGroup
        rgRedondeo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup Group, int checkedID) {
                double tD=0, tP=0;                    //Variables auxiliar contadores y valores totales acumulados

                if (checkedID == R.id.rbRedondeo) {
                    tD = Double.parseDouble(txtDescuento.getText().toString());
                    tP = Double.parseDouble(txtSueldo.getText().toString());
                    lbl_pago.setText(String.valueOf(Math.round(tP)));
                    lbl_dcto.setText(String.valueOf(Math.round(tD)));
                    Toast.makeText(getApplication(), "Redondeo", Toast.LENGTH_SHORT).show();
                } else if (checkedID == R.id.rbNoRedondeo) {
                    tD = Double.parseDouble(txtDescuento.getText().toString());
                    tP = Double.parseDouble(txtSueldo.getText().toString());
                    lbl_pago.setText(String.valueOf(Math.ceil(tP)));
                    lbl_dcto.setText(String.valueOf(Math.ceil(tD)));
                    Toast.makeText(getApplication(), "Sin Redondeo" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Mensaje de propiedad
        Toast.makeText(getApplication(), "By Daniel Muñoz", Toast.LENGTH_SHORT).show();
    }

    //Limpiar pantalla
    @SuppressLint("SetTextI18n")
    public void limpiar(View view) {
        txtDescuento.setText("0");
        txtSueldo.setText("");
        txtHoras.setText("160");
        lbl_pago.setText("0");
        lbl_dcto.setText("0");

        if (!chbxDcto.isChecked()) {
            chbxDcto.setChecked(true);
        }
    }

    public void calcular(View view) {
        try {
            // stuff that could cause error
            double S = Double.parseDouble(txtSueldo.getText().toString());
            double D = Double.parseDouble(txtDescuento.getText().toString());
            double horas = Double.parseDouble(txtHoras.getText().toString());
            double iM = (S * 20) / 100;                     //20% de impocisiones sobre el sueldo
            double vH = (S / 160);                          //Valor por hora
            double vA1, vA2, tD=0, tP=0;                    //Variables auxiliar contadores y valores totales acumulados

            if (chbxDcto.isChecked()) {                     //Con DESCUENTO
                //horas
                if (horas < 160) {
                    vA1 = (vH * horas);
                    vA2 = (S - vA1);
                    tD = (tD + vA2 + iM - D) * -1;           // Para que muestre valors con signo negativo
                    tP = tP + vA1 - D;
                    lbl_dcto.setText(String.valueOf(tD));
                    lbl_pago.setText(String.valueOf(tP));
                } else if (horas > 160) {
                    vA1 = (vH * horas);
                    tD = (tD + vA1 + iM - D - S);
                    tP = tP + vA1 - D;
                    lbl_dcto.setText(String.valueOf(tD));
                    lbl_pago.setText(String.valueOf(tP));
                } else {
                    tD = (tD + iM - D);
                    tP = (tP + S - tD);
                    lbl_dcto.setText(String.valueOf(tD));
                    lbl_pago.setText(String.valueOf(tP));
                }
            } else {
                lbl_dcto.setText("0");
                lbl_pago.setText(String.valueOf(S));
            }
        } catch (Exception e) {
            // handle error
            Toast.makeText(getApplication(), "Completa los datos correctamente", Toast.LENGTH_SHORT).show();
        }
        // do stuff
    }
}