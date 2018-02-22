package kz.kaliolla.album.module.album;

import java.util.List;

import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.models.ErrorModel;

/**
 * Created by akaliolla on 22.02.2018.
 */

public interface AlbumView {
    void setAlbums(List<Album> albums);
    void onError(ErrorModel errorMessage);
}
