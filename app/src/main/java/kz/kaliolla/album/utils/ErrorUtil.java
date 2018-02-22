package kz.kaliolla.album.utils;

import com.google.gson.Gson;

import java.io.IOException;

import kz.kaliolla.album.models.ErrorModel;
import retrofit2.Response;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class ErrorUtil {

    public static ErrorModel parseError(Response<?> response) {
        ErrorModel error;
        try {
            error = new Gson().fromJson(response.errorBody().string(), ErrorModel.class);
        } catch (IOException e) {
            return new ErrorModel();
        }
        return error;
    }
}
