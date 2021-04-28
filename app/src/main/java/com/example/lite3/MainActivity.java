package com.example.lite3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvData;
    private DataAdapter DataAdapter;
    private FloatingActionButton adData;
    private Databassa database;
    private LinearLayout BEditPop, BDeletePop;
    private Button BBatalPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Databassa (this);

        rvData = findViewById(R.id.rvData);
        adData = findViewById(R.id.adData);

        rvData.setLayoutManager(new LinearLayoutManager(this));
        DataAdapter = new DataAdapter(this, database.getAllData());
        rvData.setAdapter(DataAdapter);

        adData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, addEditData1.class));
            }
        });

        DataAdapter.setOnItemClickListenerData(new DataAdapter.OnClickListenerData() {
            @Override
            public void onItemClickData(long id) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.popup_data, null);
                BEditPop = view.findViewById(R.id.BEditPop);
                BDeletePop = view.findViewById(R.id.BDeletePop);
                BBatalPop = view.findViewById(R.id.BBatalPop);

                Dialog popupdata = new Dialog(MainActivity.this);
                popupdata.setContentView(view);
                popupdata.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupdata.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        BEditPop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent editData = new Intent(MainActivity.this, addEditData1.class);
                                editData.putExtra(Databassa.id_Data, id);
                                startActivity(editData);
                                popupdata.dismiss();
                            }
                        });

                        BDeletePop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Konfirmasi");
                                builder.setMessage("Anda ingin menghapus data? ");
                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        database.deleteData(id);
                                        popupdata.dismiss();
                                        DataAdapter.swapCursor(database.getAllData());
                                    }
                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        popupdata.dismiss();
                                    }
                                });
                                AlertDialog popupKonfirmasi = builder.create();
                                popupKonfirmasi.show();
                            }
                        });

                        BBatalPop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupdata.dismiss();
                            }
                        });
                    }
                });
                popupdata.show();

            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        DataAdapter.swapCursor(database.getAllData());
    }
}