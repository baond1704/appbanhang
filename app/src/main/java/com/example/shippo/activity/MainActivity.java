package com.example.shippo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.shippo.R;
import com.example.shippo.adapter.LoaiSpAdapter;
import com.example.shippo.adapter.SanPhamMoiAdapter;
import com.example.shippo.model.LoaiSp;
import com.example.shippo.model.LoaiSpModel;
import com.example.shippo.model.SanPhamMoi;
import com.example.shippo.model.SanPhamMoiModel;
import com.example.shippo.retrofit.ApiService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spMoiAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ActionBar();
        ActionViewFlipper();

        if(isConnected(this)){
            ApiService.apiService.Getloaisanpham().enqueue(new Callback<LoaiSpModel>() {
                @Override
                public void onResponse(Call<LoaiSpModel> call, Response<LoaiSpModel> response) {
                    Toast.makeText(MainActivity.this, "Call Api OKE", Toast.LENGTH_SHORT).show();
                    LoaiSpModel loaiSpModel = response.body();
                    if(loaiSpModel != null && loaiSpModel.getSuccess()){
                        mangloaisp = loaiSpModel.getResult();
                        loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(),mangloaisp);
                        listViewManHinhChinh.setAdapter(loaiSpAdapter);
                    }

                }

                @Override
                public void onFailure(Call<LoaiSpModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
                }
            });

            ApiService.apiService.Getsanphammoi().enqueue(new Callback<SanPhamMoiModel>() {
                @Override
                public void onResponse(Call<SanPhamMoiModel> call, Response<SanPhamMoiModel> response) {
                    SanPhamMoiModel sanPhamMoiModel = response.body();
                    if(sanPhamMoiModel != null && sanPhamMoiModel.getSuccess()){
                        mangSpMoi= sanPhamMoiModel.getResult();
                        spMoiAdapter= new SanPhamMoiAdapter(getApplicationContext(),mangSpMoi);
                        recyclerViewManHinhChinh.setAdapter(spMoiAdapter);
                    }
                }

                @Override
                public void onFailure(Call<SanPhamMoiModel> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Call Api sp Error", Toast.LENGTH_SHORT).show();
                }
            });

            getEventClick();
        }else {
            Toast.makeText(getApplicationContext(),"Kết nối internet",Toast.LENGTH_LONG).show();
        }
    }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;

                    case 1:
                        Intent dienthoai = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        dienthoai.putExtra("loai",1);
                        startActivity(dienthoai);
                        break;

                    case 2:
                        Intent laptop = new Intent(getApplicationContext(),DienThoaiActivity.class);
                        startActivity(laptop);
                        break;
                }
            }
        });
    }


    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");
        for (int i=0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.siide_out_right);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void AnhXa() {
        toolbar =findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        //khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();


    }
    private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }


    }


}