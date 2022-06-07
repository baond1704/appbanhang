package com.example.shippo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        loai = getIntent().getIntExtra("loai",1);
        AnhXa();
        ActionToolBar();


        ApiService.apiService.GetSanPham(page,loai).enqueue(new Callback<SanPhamMoiModel>() {
            @Override
            public void onResponse(Call<SanPhamMoiModel> call, Response<SanPhamMoiModel> response) {
                SanPhamMoiModel sanPhamMoiModel = response.body();
                if(sanPhamMoiModel != null && sanPhamMoiModel.getSuccess()){
                    sanPhamMoiList = sanPhamMoiModel.getResult();
                    dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                    recyclerView.setAdapter(dienThoaiAdapter);
                }
            }

            @Override
            public void onFailure(Call<SanPhamMoiModel> call, Throwable t) {
                Toast.makeText(DienThoaiActivity.this, "Khong ket noi sever", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_dt);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }
}