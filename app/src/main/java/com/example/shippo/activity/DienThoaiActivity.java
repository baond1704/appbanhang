package com.example.shippo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.shippo.R;
import com.example.shippo.adapter.DienThoaiAdapter;
import com.example.shippo.model.SanPhamMoi;
import com.example.shippo.model.SanPhamMoiModel;
import com.example.shippo.retrofit.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    DienThoaiAdapter dienThoaiAdapter;
    List<SanPhamMoi> sanPhamMoiList;
    int loai;
    int page = 1;
    LinearLayoutManager layoutManager;
    Handler handler = new Handler();
    boolean isLoangding = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        loai = getIntent().getIntExtra("loai",1);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();



    }

    private void getData(int page) {
        ApiService.apiService.GetSanPham(page,loai).enqueue(new Callback<SanPhamMoiModel>() {
            @Override
            public void onResponse(Call<SanPhamMoiModel> call, Response<SanPhamMoiModel> response) {
                SanPhamMoiModel sanPhamMoiModel = response.body();
                if(sanPhamMoiModel != null && sanPhamMoiModel.getSuccess()){
                    if(dienThoaiAdapter == null){
                        sanPhamMoiList = sanPhamMoiModel.getResult();
                        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                        recyclerView.setAdapter(dienThoaiAdapter);
                    }else {
                        int vitri= sanPhamMoiList.size()-1;
                        int soluongadd = sanPhamMoiModel.getResult().size();
                        for (int i=0 ;i<soluongadd;i++){
                            sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                        }
                        dienThoaiAdapter.notifyItemRangeChanged(vitri,soluongadd);
                    }

                }else {
                    Toast.makeText(DienThoaiActivity.this, "Het du lieu", Toast.LENGTH_SHORT).show();
                    isLoangding = true;
                }
            }

            @Override
            public void onFailure(Call<SanPhamMoiModel> call, Throwable t) {
                Toast.makeText(DienThoaiActivity.this, "Khong ket noi sever", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoangding == false){
                    if(layoutManager.findFirstCompletelyVisibleItemPosition() == sanPhamMoiList.size() -1){
                        isLoangding = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //add null
                sanPhamMoiList.add(null);
                dienThoaiAdapter.notifyItemChanged(sanPhamMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remover null
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                dienThoaiAdapter.notifyItemRemoved(sanPhamMoiList.size());
                page = page+1;
                getData(page);
                dienThoaiAdapter.notifyDataSetChanged();
                isLoangding = false;

            }
        },2000);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_dt);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }
}