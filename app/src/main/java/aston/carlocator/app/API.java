package aston.carlocator.app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @POST("add")
    Call<Locator> addLocator(@Body Locator locator);

    @GET("getAll")
    Call<List<Locator>> getLocators();
}
