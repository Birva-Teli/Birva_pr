package example.com.birva_pr;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static WebService getRestClient(){

       // OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        //builder.connectTimeout(1000, TimeUnit.SECONDS);
        //builder.readTimeout(1000, TimeUnit.SECONDS);
        //builder.writeTimeout(1000, TimeUnit.SECONDS);
        //builder.addInterceptor(new HeaderInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app.auronia.io/Auronia/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WebService.class);
    }
}
