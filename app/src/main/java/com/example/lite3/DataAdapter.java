package com.example.lite3;

import android.content.Context;
import android.database.Cursor;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private OnClickListenerData listenerData;

    public DataAdapter(Context Context, Cursor Cursor) {
        this.mContext = Context;
        this.mCursor = Cursor;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.b1, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String nama = mCursor.getString(mCursor.getColumnIndex(Databassa.nama));
        String tanggallahir = mCursor.getString(mCursor.getColumnIndex(Databassa.tanggal_lahir));
        String nomortelepon = mCursor.getString(mCursor.getColumnIndex(Databassa.nomor_telepon));
        String alamat = mCursor.getString(mCursor.getColumnIndex(Databassa.alamat));
        long id = mCursor.getLong(mCursor.getColumnIndex(Databassa.id_Data));

        holder.itemView.setTag(id);
        holder.TNama.setText(nama);
        holder.TTL.setText(tanggallahir);
        holder.TNTelepon.setText(nomortelepon);
        holder.TAlamat.setText(alamat);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        private TextView TNama, TTL, TNTelepon, TAlamat;

        public DataViewHolder(@NonNull View itemView){
            super(itemView);

            TNama = itemView.findViewById(R.id.TNama);
            TTL = itemView.findViewById(R.id.TTL);
            TNTelepon = itemView.findViewById(R.id.TNTelepon);
            TAlamat = itemView.findViewById(R.id.TAlamat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long position = (long) itemView.getTag();
                    listenerData.onItemClickData(position);
                }
            });
        }
    }

    public interface OnClickListenerData {
        void onItemClickData(long id);
    }
    public void setOnItemClickListenerData(OnClickListenerData listenerData){
        this.listenerData =listenerData;
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor !=null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            this.notifyDataSetChanged();
        }
    }
}
