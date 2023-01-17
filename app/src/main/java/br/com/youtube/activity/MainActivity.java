package br.com.youtube.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.youtube.R;
import br.com.youtube.adapter.AdapterVideo;
import br.com.youtube.api.YoutubeService;
import br.com.youtube.databinding.ActivityMainBinding;
import br.com.youtube.helper.RecyclerItemClickListener;
import br.com.youtube.helper.RetrofitConfig;
import br.com.youtube.helper.YoutubeConfig;
import br.com.youtube.model.Items;
import br.com.youtube.model.Resultado;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Items> videos = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;
    private MaterialSearchView searchView;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        searchView = findViewById(R.id.search_view);

        //configuracoes iniciais
        retrofit = RetrofitConfig.getRetrofit();

        //config toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);

        recuperarVideos("");

        //config métodos para SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recuperarVideos("");
            }
        });
    }

    private void recuperarVideos(String pesquisa){

        String q = pesquisa.replaceAll(" ", "+");

        YoutubeService  youtubeService = retrofit.create(YoutubeService.class);

        youtubeService.recuperarVideos(
                "snippet",
                "date",
                "20",
                YoutubeConfig.CHAVE_YOUTUBE_API,
                YoutubeConfig.CANAL_ID,
                q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {

                Log.d("resultado", "resultado: " + response.toString());
                if (response.isSuccessful()){

                    resultado = response.body();
                    videos = resultado.items;

                    configuraRecyclerView();

//                    Log.d("resultado", "resultado: " + resultado.items.get(0).id.videoId.toString());
//                    Log.d("resultado1", "resultado1: " + resultado.items.get(0).snippet.title.toString());
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });
    }

    public void configuraRecyclerView(){
        //config recycler
        adapterVideo = new AdapterVideo(videos, getApplicationContext());
        binding.recyclerVideos.setHasFixedSize(true);
        binding.recyclerVideos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerVideos.setAdapter(adapterVideo);

        //Configura evento de clique
        binding.recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        binding.recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Items video = videos.get(position);
                                String idVideo = video.id.videoId;

                                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                                i.putExtra("idVideo", idVideo);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }


}