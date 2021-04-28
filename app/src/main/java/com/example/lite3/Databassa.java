package com.example.lite3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databassa extends SQLiteOpenHelper {
    public static final String database_name = "Data";
    public static final int database_version = 4;

    public static final String Tabel_Data = "Tabel_Data";
    public static final String id_Data = "Id_Data";
    public static final String nama = "Nama";
    public static final String tanggal_lahir = "Tanggal lahir";
    public static final String nomor_telepon = "Nomor Telepon";
    public static final String alamat = "Alamat";
    public static final String created = "created";

    private SQLiteDatabase db;

    public Databassa(@Nullable Context context) {
        super(context, database_name, null, database_version);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ Tabel_Data + "("
                + id_Data + "interger primary key autoincrement, "
                + nama +" text not null, "
                + tanggal_lahir + "text not null,"
                + nomor_telepon + "text not null,"
                + alamat + " text not null,"
                + created + " timestamp default (datetime ('now','localtime')))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tabel_data");
        onCreate(db);
    }

    public void insertData(ContentValues values){
        db.insert(Tabel_Data, null, values);
    }

    public void updateData(ContentValues values, long id){
        db.update(Tabel_Data, values, id_Data + "=" + id, null);
    }

    public void deleteData(long id){
        db.delete(Tabel_Data, id_Data + "=" + id, null);
    }

    public Cursor getAllData(){
        return db.query(Tabel_Data, null, null, null, null, null, nama+ "ASC");
    }

    public Cursor getData(long id){
        return db.rawQuery("select * from "+ Tabel_Data +" where"+ id_Data+"-" + id, null);
    }

}
