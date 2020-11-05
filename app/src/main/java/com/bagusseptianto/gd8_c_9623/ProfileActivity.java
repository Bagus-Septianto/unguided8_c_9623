package com.bagusseptianto.gd8_c_9623;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView twNama, twNim, twFakultas, twProdi, twJenisKelamin;
    private String sNama, sNim, sFakultas, sProdi, sJenisKelamin;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        twNama = findViewById(R.id.twNama);
        twNim = findViewById(R.id.twNim);
        twFakultas = findViewById(R.id.twFakultas);
        twProdi = findViewById(R.id.twProdi);
        twJenisKelamin = findViewById(R.id.twJenisKelamin);

        Intent intent = getIntent();
        sNim = intent.getStringExtra("nim");
        sNama = intent.getStringExtra("nama");
        sFakultas = intent.getStringExtra("fakultas");
        sProdi = intent.getStringExtra("prodi");
        sJenisKelamin = intent.getStringExtra("jeniskelamin");

        twNama.setText(sNama);
        twNim.setText(sNim);
        twFakultas.setText(sFakultas);
        twProdi.setText(sProdi);
        twJenisKelamin.setText(sJenisKelamin);
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}