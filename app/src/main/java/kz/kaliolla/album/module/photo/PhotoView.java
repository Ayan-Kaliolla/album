package kz.kaliolla.album.module.photo;

import java.util.List;

import kz.kaliolla.album.models.ErrorModel;
import kz.kaliolla.album.models.Photo;

/**
 * Created by akaliolla on 22.02.2018.
 */

interface PhotoView {
    void setPhotos(List<Photo> photos);
    void onError(ErrorModel errorModel);
}
