package com.bagusseptianto.gd8_c_9623;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserFragment extends DialogFragment {
    private TextView twNama, twNim, twFakultas, twProdi, twJenisKelamin;
    private String sIdUser, sNama, sNim, sFakultas, sProdi, sJenisKelamin;
    private ImageButton ibClose;
    private Button btnDelete, btnEdit;
    private ProgressDialog progressDialog;

    public static DetailUserFragment newInstance() {
        return new DetailUserFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_user_fragment, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        twNama = v.findViewById(R.id.twNama);
        twNim = v.findViewById(R.id.twNim);
        twFakultas = v.findViewById(R.id.twFakultas);
        twProdi = v.findViewById(R.id.twProdi);
        twJenisKelamin = v.findViewById(R.id.twJenisKelamin);

        btnDelete = v.findViewById(R.id.btnDelete);
        btnEdit = v.findViewById(R.id.btnEdit);

        sIdUser = getArguments().getString("id", "");
        loadUserById(sIdUser);
        return v;
    }

    private void loadUserById(String id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.getUserById(id, "data");

        add.enqueue(new Callback<UserResponse>(){

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                sNama = response.body().getUsers().get(0).getNama();
                sNim = response.body().getUsers().get(0).getNim();
                sFakultas = response.body().getUsers().get(0).getFakultas();
                sProdi = response.body().getUsers().get(0).getProdi();
                sJenisKelamin = response.body().getUsers().get(0).getJenis_kelamin();

                twNama.setText(sNama);
                twNim.setText(sNim);
                twFakultas.setText(sFakultas);
                twProdi.setText(sProdi);
                twJenisKelamin.setText(sJenisKelamin);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setTitle("Konfirmasi");
                        alertDialog.setMessage("Yakin ingin menghapus data ini?");
                        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                                Call<UserResponse> delete = apiService.deleteUser(id);

                                delete.enqueue(new Callback<UserResponse>(){

                                    @Override
                                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                        Toast.makeText(getContext(), "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                        dismiss();
                                        getActivity().recreate();
                                    }

                                    @Override
                                    public void onFailure(Call<UserResponse> call, Throwable t) {
                                        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                        dismiss();
                                    }
                                });
                            }
                        });
                        alertDialog.show();
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EditUserActivity.class);
                        intent.putExtra("id", sIdUser);
                        intent.putExtra("nama", sNama);
                        intent.putExtra("nim", sNim);
                        intent.putExtra("fakultas", sFakultas);
                        intent.putExtra("prodi", sProdi);
                        intent.putExtra("jeniskelamin", sJenisKelamin);
                        startActivity(intent);
                        dismiss();
                    }
                });

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
