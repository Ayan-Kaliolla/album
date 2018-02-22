package kz.kaliolla.album.module.photo;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class PhotoAdapter /*extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>*/ {




    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView title;
        TextView url;
        TextView thumbnailUrl;

        public ViewHolder(TextView v) {
            super(v);

        }
    }

}
