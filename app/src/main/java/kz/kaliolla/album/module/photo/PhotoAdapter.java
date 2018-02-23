package kz.kaliolla.album.module.photo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;
import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.models.Photo;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Photo> photos;

    public PhotoAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPhotos(@NonNull List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public List<Photo> getPhotos() {
        return this.photos;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.photo_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.id.setText(String.valueOf(photo.getId()));
        holder.title.setText(photo.getTitle());
        holder.url.setText(photo.getUrl());
        Glide.with(context).load(photo.getThumbnailUrl()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.imgProgress.setVisibility(View.GONE);
                holder.img.setImageResource(R.drawable.ic_error);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.imgProgress.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.img);

        holder.itemView.setTag(photo);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (photos != null) {
            return photos.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.url)
        TextView url;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.img_progress)
        ProgressBar imgProgress;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Photo photo = (Photo) view.getTag();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(photo.getThumbnailUrl()));
            context.startActivity(intent);
        }
    };

}
