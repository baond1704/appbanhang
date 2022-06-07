package com.example.shippo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shippo.R;
import com.example.shippo.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class DienThoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SanPhamMoi> array;
    private static final int VIEW_TYPE_DATA =0;
    private static final int VIEW_TYPE_LOADING =1;


    public DienThoaiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }




    public class loadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public loadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai,parent,false);
            return new MyViewHodel(item);
        }else {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new loadingViewHolder(item);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHodel){
            MyViewHodel myViewHodel = (MyViewHodel)holder;
        SanPhamMoi sanPham = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHodel.tensp.setText(sanPham.getTensanpham());
            myViewHodel.giasp.setText("Giá " + decimalFormat.format(Double.parseDouble(sanPham.getGiasanpham())) + "Đ");
            myViewHodel.mota.setText(sanPham.getMota());
            myViewHodel.id.setText(sanPham.getId()+"");
        Glide.with(context).load(sanPham.getHinhanh()).into(myViewHodel.hinhanh);
    }else {
            loadingViewHolder loadingViewHolder = (loadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHodel extends RecyclerView.ViewHolder {
        TextView tensp,giasp,mota,id;
        ImageView hinhanh;
        public MyViewHodel(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_mota);
            id = itemView.findViewById(R.id.itemdt_iddt);
            hinhanh = itemView.findViewById(R.id.itemdt_image);
        }
    }
}
