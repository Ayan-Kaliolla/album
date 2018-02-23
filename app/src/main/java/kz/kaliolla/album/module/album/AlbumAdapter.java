package kz.kaliolla.album.module.album;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import kz.kaliolla.album.module.photo.PhotoActivity;

/**
 * Created by akaliolla on 22.02.2018.
 */

class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Album> items;

    public AlbumAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setAlbums(@NonNull List<Album> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<Album> getAlbums(){
        return items;
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

        holder.itemView.setTag(album);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
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

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Album album = (Album) view.getTag();
            Context context = view.getContext();
            Intent intent = new Intent(context, PhotoActivity.class);
            intent.putExtra(PhotoActivity.ALBUM_ID, album.getId());
            context.startActivity(intent);
        }
    };
}
