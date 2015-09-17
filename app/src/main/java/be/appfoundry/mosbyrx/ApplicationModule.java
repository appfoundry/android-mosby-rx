package be.appfoundry.mosbyrx;

import android.app.Application;
import android.content.Context;

import be.appfoundry.mosbyrx.data.service.GitHubAPI;
import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

@Module
public class ApplicationModule {
    final Application application;
    final Retrofit retrofit;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
        this.retrofit = new Retrofit.Builder()
                .baseUrl(GitHubAPI.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    public Context provideContext() {
        return application;
    }

    @Provides
    public GitHubAPI provideGitHubAPI() {
        return retrofit.create(GitHubAPI.class);
    }
}

