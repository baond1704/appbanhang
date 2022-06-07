package com.example.shippo.adapter;

import android.content.Context;
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

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.MyViewHodel> {

    Context context;
    List<SanPhamMoi> array;

    public DienThoaiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai,parent,false);
        return new MyViewHodel(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodel holder, int position) {
        SanPhamMoi sanPham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tensp.setText(sanPham.getTensanpham());
        holder.giasp.setText("Giá "+decimalFormat.format(Double.parseDouble(sanPham.getGiasanpham()))+"Đ");
        holder.mota.setText(sanPham.getMota());
        Glide.with(context).load(sanPham.getHinhanh()).into(holder.hinhanh);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHodel extends RecyclerView.ViewHolder {
        TextView tensp,giasp,mota;
        ImageView hinhanh;
        public MyViewHodel(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_mota);
            hinhanh = itemView.findViewById(R.id.itemdt_image);
        }
    }
}
