package be.appfoundry.mosbyrx.ui.presenter;

import android.os.NetworkOnMainThreadException;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import be.appfoundry.mosbyrx.data.entity.GitHubRepo;
import be.appfoundry.mosbyrx.data.service.GitHubAPI;
import be.appfoundry.mosbyrx.ui.view.repo.RepoView;
import be.appfoundry.mvp.mosby.BaseRxPresenter;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import rx.Subscriber;

/**
 * Created by janvancoppenolle on 19/06/15.
 */
public class RepoPresenterImpl
        extends BaseRxPresenter<RepoView>
        implements RepoPresenter {

    GitHubAPI gitHubAPI;

    @Inject
    public RepoPresenterImpl(GitHubAPI gitHubAPI) {
        this.gitHubAPI = gitHubAPI;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /* This won't fly... */
    @Override
    public List<GitHubRepo> loadRepoListSynchonously() {
        Call<List<GitHubRepo>> call = gitHubAPI.getRepos();
        List<GitHubRepo> result = null;
        try {
            result = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            // We will end up here...
            getView().showMessage(e.toString());
        }
        return result;
    }

    @Override
    public void loadRepoListAsynchonously() {
        Call<List<GitHubRepo>> call = gitHubAPI.getRepos();
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Response<List<GitHubRepo>> response) {
                if (isViewAttached()) {
                    getView().showRepos(response.body());
                    getView().hideLoadingIndicator();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (isViewAttached()) {
                    getView().showMessage(t.getMessage());
                    getView().hideLoadingIndicator();
                }
            }
        });
    }

    @Override
    public void loadRepoListRx() {
        new RxIOSubscription<List<GitHubRepo>>().add(
                gitHubAPI.getReposRx(),
                new Subscriber<List<GitHubRepo>>() {
                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepos) {
                        getView().showRepos(gitHubRepos);
                    }

                    @Override
                    public void onCompleted() {
                        getView().hideLoadingIndicator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showMessage(e.getMessage());
                        getView().hideLoadingIndicator();
                    }
                }
        );
    }
}
