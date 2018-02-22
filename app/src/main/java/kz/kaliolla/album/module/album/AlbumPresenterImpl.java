package kz.kaliolla.album.module.album;

import android.support.annotation.NonNull;

import java.util.List;

import kz.kaliolla.album.app.App;
import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.utils.ErrorUtil;
import kz.kaliolla.album.utils.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akaliolla on 22.02.2018.
 */

class AlbumPresenterImpl implements AlbumPresenter, Callback<List<Album>>{
    private AlbumView view;

    public AlbumPresenterImpl(@NonNull AlbumView view) {
        this.view = view;
    }

    @Override
    public void loadAlbums() {
        App.getReatApi().getAlbums().enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
        if (response.isSuccessful()){
            view.setAlbums(response.body());
        }else {
            view.onError(ErrorUtil.parseError(response));
        }
    }

    @Override
    public void onFailure(Call<List<Album>> call, Throwable t) {
        LogUtil.e(this, t.getMessage(), t);
    }
}
