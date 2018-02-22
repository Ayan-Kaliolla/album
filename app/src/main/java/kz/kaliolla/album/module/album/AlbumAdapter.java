package kz.kaliolla.album.module.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;
import kz.kaliolla.album.models.Album;

/**
 * Created by akaliolla on 22.02.2018.
 */

class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Album> items;

    public AlbumAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAlbums(List<Album> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.album_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Album album = items.get(position);
        holder.id.setText(String.valueOf(album.getId()));
        holder.title.setText(album.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
