package br.com.youtube.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import br.com.youtube.R;
import br.com.youtube.model.Items;
import br.com.youtube.model.Video;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Items> videos = new ArrayList<>();
    private Context context;

    public AdapterVideo(List<Items> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Items items = videos.get(position);
        holder.titulo.setText(items.snippet.title);

        String url = items.snippet.thumbnails.high.url;
        Picasso.get().load(url).into(holder.capa);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, descricao, data;
        ImageView capa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo= itemView.findViewById(R.id.text_title);
            capa = itemView.findViewById(R.id.image_capa);
        }
    }
}
