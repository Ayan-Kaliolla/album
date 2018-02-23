package kz.kaliolla.album.module.photo;

import java.util.List;

import kz.kaliolla.album.app.App;
import kz.kaliolla.album.models.ErrorModel;
import kz.kaliolla.album.models.Photo;
import kz.kaliolla.album.utils.ErrorUtil;
import kz.kaliolla.album.utils.LogUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akaliolla on 22.02.2018.
 */

class PhotoPresenterImpl implements PhotoPresenter, Callback<List<Photo>> {
    private PhotoView photoView;

    public PhotoPresenterImpl(PhotoView photoView) {
        this.photoView = photoView;
    }

    @Override
    public void loadPhotos(long id) {
        App.getReatApi().getPhotos(id).enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
        if (response.isSuccessful()){
            photoView.setPhotos(response.body());
        }else {
            ErrorModel errorModel = ErrorUtil.parseError(response);
            photoView.onError(errorModel);
        }
    }

    @Override
    public void onFailure(Call<List<Photo>> call, Throwable t) {
        LogUtil.e(this, t.getMessage(), t);
    }
}
