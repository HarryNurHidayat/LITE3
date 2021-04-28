package com.example.lite3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;

public class addEditData1 extends AppCompatActivity {

    private EditText etNama, etTTL, etTNTelepon, etTAlamat;
    private Button BSimpan, BBatal;
    private Databassa databassa;
    private long id;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_data1);

        intent= getIntent();

        id = intent.getLongExtra(Databassa.id_Data,0);

        if (intent.hasExtra(Databassa.id_Data)){
            setTitle("Edit Data");

        }else {
            setTitle("Tambah Data");
        }
        etNama =findViewById(R.id.etNama);
        etTTL =findViewById(R.id.etTTL);
        etTNTelepon =findViewById(R.id.etTNTelepon);
        etTAlamat =findViewById(R.id.etTAlamat);

        BSimpan =findViewById(R.id.BSimpan);
        BBatal =findViewById(R.id.BBatal);

        databassa = new Databassa(this);

        etTTL.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar getCalandar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(addEditData1.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar setCalendar = Calendar.getInstance();
                        setCalendar.set(Calendar.YEAR, year);
                        setCalendar.set(Calendar.MONTH, month);
                        setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String setCurrentDate = DateFormat.getDateInstance().format(setCalendar.getTime());
                        etTTL.setText(setCurrentDate);
                    }
                }, getCalandar.get(Calendar.YEAR), getCalandar.get(Calendar.MONTH),getCalandar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        BSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesSimpan();
            }
        });


        BBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getData();
    }

    private void getData(){
        Cursor cursor = databassa.getData(id);
        if(cursor.moveToFirst()){
            String Nama = cursor.getString(cursor.getColumnIndex(Databassa.nama));
            String TanggalLahir = cursor.getString(cursor.getColumnIndex(Databassa.tanggal_lahir));
            String NomorTelepon = cursor.getString(cursor.getColumnIndex(Databassa.nomor_telepon));
            String Alamat = cursor.getString(cursor.getColumnIndex(Databassa.alamat));

            etNama.setText(Nama);
            etTTL.setText(TanggalLahir);
            etTNTelepon.setText(NomorTelepon);
            etTAlamat.setText(Alamat);
        }
    }

    private void prosesSimpan() {
        String Nama = etNama.getText().toString().trim();
        String TanggalLahir = etTTL.getText().toString().trim();
        String NomorTelepon = etTNTelepon.getText().toString().trim();
        String Alamat = etTAlamat.getText().toString().trim();

        if(Nama.isEmpty()){
            etNama.setError("Nama Belum Diisi");
        }else if(TanggalLahir.isEmpty()){
            etTTL.setError("Tanggal lahir Belum Diisi");
        }else if(NomorTelepon.isEmpty()) {
            etTNTelepon.setError("Nomor Telepon Belum Diisi");
        }else if(Alamat.isEmpty()) {
            etTAlamat.setError("Alamat lahir Belum Diisi");
        }else{
            ContentValues values = new ContentValues();
            values.put(Databassa.nama, Nama);
            values.put(Databassa.tanggal_lahir, TanggalLahir);
            values.put(Databassa.nomor_telepon, NomorTelepon);
            values.put(Databassa.alamat, Alamat);
            if (intent.hasExtra(Databassa.id_Data)){
                databassa.updateData(values, id);

            }else {

                databassa.insertData(values);
            }
            finish();
        }
    }
}