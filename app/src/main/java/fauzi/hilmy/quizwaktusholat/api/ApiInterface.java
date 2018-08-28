package fauzi.hilmy.quizwaktusholat.api;

import fauzi.hilmy.quizwaktusholat.model.ResponseData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("weekly.json?key=d1aa7cdf5e8c0d2d7f1f47811497a732")
    Call<ResponseData> readJadwalShalat();
}
