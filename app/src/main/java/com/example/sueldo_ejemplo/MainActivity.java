package com.example.sueldo_ejemplo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private RadioGroup RadioGroup;
    private EditText txtHoras, txtDescuento, txtSueldo;
    private CheckBox chbxDcto;
    private RadioGroup rgRedondeo;
    private RadioButton rbRedondeo, rbNoRedondeo;
    private Button btnLimpiar, btnCalcular;
    private TextView lbl_pago, lbl_dcto;
    private double D1, D2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSueldo = (EditText) findViewById(R.id.txtSueldo);
        txtHoras = (EditText) findViewById(R.id.txtHoras);
        txtDescuento = (EditText) findViewById(R.id.txtDescuento);
        chbxDcto = (CheckBox) findViewById(R.id.chbxDcto);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        lbl_dcto = (TextView) findViewById(R.id.lbl_dcto);
        lbl_pago = (TextView) findViewById(R.id.lbl_pago);

        //Limpiar y formatear variables
        txtDescuento.setText(String.valueOf("0"));
        txtSueldo.setText(String.valueOf(""));
        txtHoras.setText(String.valueOf("160"));
        lbl_pago.setText(String.valueOf("0"));
        lbl_dcto.setText(String.valueOf("0"));

        //Funcion escucha RadioGroup
        RadioGroup = (RadioGroup) findViewById(R.id.rgRedondeo);
        RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup Group, int checkedID) {
                if (checkedID == R.id.rbRedondeo) {
                    Toast.makeText(getApplication(), "Redondeo", Toast.LENGTH_SHORT).show();
                } else if (checkedID == R.id.rbNoRedondeo) {
                    Toast.makeText(getApplication(), "Sin Redondeo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Mensaje de propiedad
        Toast.makeText(getApplication(), "By Daniel Muñoz", Toast.LENGTH_SHORT).show();
    }

    //Limpiar pantalla
    public void limpiar(View view) {
        txtDescuento.setText(String.valueOf("0"));
        txtSueldo.setText(String.valueOf(""));
        txtHoras.setText(String.valueOf("160"));
        lbl_pago.setText(String.valueOf("0"));
        lbl_dcto.setText(String.valueOf("0"));


        if (chbxDcto.isChecked() == false) {
            chbxDcto.setChecked(true);
        }
/*
        if (rgRedondeo.getCheckedRadioButtonId() == R.id.rbRedondeo){
            rbRedondeo.setChecked(false);
        }

        if (rgRedondeo.getCheckedRadioButtonId() == R.id.rbNoRedondeo){
            rbNoRedondeo.setChecked(false);
        }

 */
    }


    public void calcular(View view) {
        try {
            // stuff that could cause error
            int sueldo = Integer.parseInt(txtSueldo.getText().toString());
            int descuento = Integer.parseInt(txtDescuento.getText().toString());
            int horas = Integer.parseInt(txtHoras.getText().toString());
            int afp_salud = (sueldo * 20) / 100;    //20% de descuento sobre el sueldo
            int vH = (sueldo / 160);              //Valor por hora
            int vD = (vH * 8);                    //Valor por dia
            int vA1 = 0, vA2 = 0, tD = 0, tP = 0;       //Variables auxiliar

            if (chbxDcto.isChecked() == true) {             //Con descuento
                //horas
                if (horas < 160) {
                    vA1 = (vH * horas);
                    vA2 = (sueldo - vA1);
                    tD = (tD + vA2 - descuento) * -1;
                    tP = tP + vA1 - descuento;
                    lbl_dcto.setText(String.valueOf(tD));
                    lbl_pago.setText(String.valueOf(tP));
                } else if (horas > 160) {
                    vA1 = (vH * horas);
                    tD = (tD + vA1 - descuento - sueldo);
                    tP = tP + vA1 - descuento;
                    lbl_dcto.setText(String.valueOf(tD));
                    lbl_pago.setText(String.valueOf(tP));
                } else {
                    tD = (tD - descuento);
                    tP = (tP + sueldo - descuento);
                    lbl_dcto.setText(String.valueOf(tD));
                    lbl_pago.setText(String.valueOf(tP));
                }
            } else {
                lbl_dcto.setText(String.valueOf("0"));
                lbl_pago.setText(String.valueOf(sueldo));
            }
        } catch (Exception e) {
            // handle error
            Toast.makeText(getApplication(), "Completa los datos", Toast.LENGTH_SHORT).show();
        }
        // do stuff
    }
}
