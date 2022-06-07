package com.example.shippo.adapter;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shippo.R;
import com.example.shippo.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {

    Context context;
    List<SanPhamMoi>  array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvTen.setText(sanPhamMoi.getTensanpham());
        holder.tvGia.setText("Giá "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasanpham()))+"Đ");
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imgHinhanh);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvGia,tvTen;
        ImageView imgHinhanh;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGia = itemView.findViewById(R.id.itemsp_gia);
            tvTen = itemView.findViewById(R.id.itemsp_ten);
            imgHinhanh = itemView.findViewById(R.id.itemsp_image);
        }
    }
}
