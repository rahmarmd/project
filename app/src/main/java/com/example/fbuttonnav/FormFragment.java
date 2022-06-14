package com.example.fbuttonnav;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fbuttonnav.API.APIRequestData;
import com.example.fbuttonnav.API.RetroServer;
import com.example.fbuttonnav.Response.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {
    String imagePath, kategori, nama, email, no_ponsel, alamat, jenis_pesan, deskripsi_pesan;
    final int REQUEST_GALLERY = 9544;
    int id_kategori;
    private int IMG_REQUEST = 23;
    private Bitmap bitmap;
    EditText txtEmail, txtNama, txtAlamat, txtnoPonsel, txtJenis, txtDeskripsi;
    Spinner spinnerKategori;
    ImageView iconupload;
    SessionManager sessionManager;
    String[] category = { "Standart", "Medium", "Premium"};
    private Button btnSubmit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_form, container, false);
        txtNama = view.findViewById(R.id.nm);
        txtEmail = view.findViewById(R.id.mail);
        txtnoPonsel = view.findViewById(R.id.no);
        txtAlamat = view.findViewById(R.id.alamat);
        txtJenis = view.findViewById(R.id.jns);
        txtDeskripsi = view.findViewById(R.id.desk);
        spinnerKategori = view.findViewById(R.id.pilihktg);
        iconupload = view.findViewById(R.id.gbr);
        btnSubmit = view.findViewById(R.id.buttonsubmit);

        SessionManager sessionManager = new SessionManager(this.getContext());



        ArrayAdapter aa = new ArrayAdapter(getContext() ,android.R.layout.simple_spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(aa);
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kategori = spinnerKategori.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        iconupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "iConUpload: setOncliclikListeners" ,Toast.LENGTH_LONG).show();
                try {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, IMG_REQUEST);
                    Toast.makeText(getActivity(), "iConUpload: Endlistener" ,Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(getActivity(), "iConUpload: Failed" ,Toast.LENGTH_LONG).show();

                }


            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_user = sessionManager.getSessionID();
                nama = txtNama.getText().toString();
                email = txtEmail.getText().toString();
                no_ponsel = txtnoPonsel.getText().toString();
                jenis_pesan = txtJenis.getText().toString();
                alamat = txtAlamat.getText().toString();
                deskripsi_pesan = txtDeskripsi.getText().toString();

                switch (kategori) {
                    case ("Standart") :
                        id_kategori = 1;
                        break;
                    case ("Medium") :
                        id_kategori = 2;
                        break;
                    case ("Premium") :
                        id_kategori =  3;
                        break;
                    default:
                        id_kategori = 1;
                        break;
                }

                Toast.makeText(getContext(), id_user + " " + " " + nama + email + no_ponsel + alamat + deskripsi_pesan + jenis_pesan + id_kategori , Toast.LENGTH_SHORT).show();

                if (nama.trim().equals("")) {
                    txtNama.setError("Nama Lengkap tidak boleh kosong!");
                } else if (email.trim().equals("")) {
                    txtEmail.setError("Email tidak boleh kosong!");
                } else if (no_ponsel.trim().equals("")) {
                    txtnoPonsel.setError("Nomor Telepon tidak boleh kosong!");
                } else if (alamat.trim().equals("")) {
                    txtAlamat.setError("Alamat tidak boleh kosong!");
                } else if (jenis_pesan.isEmpty()) {
                    txtJenis.setError("Jenis pesanan harus diisi!");
                } else if (deskripsi_pesan.isEmpty()) {
                    txtDeskripsi.setError("Deskripsi pesanan harus diisi!");
                }else {
                    pemesanan();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this.getActivity(), "onActivityResult:Start" ,Toast.LENGTH_SHORT).show();

        if ( resultCode == RESULT_OK && requestCode == IMG_REQUEST && data != null) {
            Toast.makeText(this.getActivity(), "onActivityResult" ,Toast.LENGTH_SHORT).show();

            Uri path = data.getData();
            Toast.makeText(this.getActivity(), "Path: " + data.getData().toString(),Toast.LENGTH_LONG).show();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), path);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void pemesanan() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);

        APIRequestData apiRequestData = RetroServer.koneksiRetrofit().create(APIRequestData.class);
        Call<ResponseUser> call = apiRequestData.formPesan(sessionManager.getSessionID(), nama, email, no_ponsel, alamat, jenis_pesan, deskripsi_pesan, id_kategori, encodedImage);
        call.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                String pesan = response.body().getPesan();
                if (pesan.equals("BERHASIL")) {
                    txtNama.setText("");
                    txtEmail.setText("");
                    txtAlamat.setText("");
                    txtnoPonsel.setText("");
                    txtJenis.setText("");
                    txtDeskripsi.setText("");
                    Toast.makeText(getContext(), "Pesanan berhasil", Toast.LENGTH_SHORT).show();

                } else if (pesan.equals("GAGAL")) {
                    Toast.makeText(getContext(), "Pemesanan gagal!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(getContext(), "Terjadi kesalahan\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, projection, null, null,null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}