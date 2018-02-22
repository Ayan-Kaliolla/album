package kz.kaliolla.album.net;

import java.util.List;

import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.models.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static kz.kaliolla.album.net.Constants.ALBUMS_PATH;
import static kz.kaliolla.album.net.Constants.PHOTOS_PATH;

/**
 * Created by akaliolla on 22.02.2018.
 */


public interface RestApi {

    @GET(ALBUMS_PATH)
    Call<List<Album>> getAlbums();

    @GET(PHOTOS_PATH)
    Call<List<Photo>> getPhotos(@Query("albumId") long albumId);
}
