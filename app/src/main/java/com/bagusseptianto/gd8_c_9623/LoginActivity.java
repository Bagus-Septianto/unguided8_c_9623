package com.bagusseptianto.gd8_c_9623;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etNim, etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNim = findViewById(R.id.etNim);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNim.getText().toString().isEmpty()) {
                    etNim.setError("Isikan dengan benar");
                    etNim.requestFocus();
                }
                else if(etPassword.getText().toString().isEmpty()) {
                    etPassword.setError("Isikan dengan benar");
                    etPassword.requestFocus();
                }
                else {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    Call<UserResponse> login = apiService.loginUser(etNim.getText().toString(),
                            etPassword.getText().toString());
                    login.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            //cek berhasil/tidak dari message api
                            if(response.body().getMessage().contains("Berhasil login")) {
                                //login untuk admin (cek nimnya admin/bukan)
                                if(etNim.getText().toString().toLowerCase().contains("admin")) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                } else {
                                    //login untuk user (nimnya bukan admin)
                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    intent.putExtra("nama", response.body().getUsers().get(0).getNama());
                                    intent.putExtra("nim", response.body().getUsers().get(0).getNim());
                                    intent.putExtra("fakultas", response.body().getUsers().get(0).getFakultas());
                                    intent.putExtra("prodi", response.body().getUsers().get(0).getProdi());
                                    intent.putExtra("jeniskelamin", response.body().getUsers().get(0).getJenis_kelamin());
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}