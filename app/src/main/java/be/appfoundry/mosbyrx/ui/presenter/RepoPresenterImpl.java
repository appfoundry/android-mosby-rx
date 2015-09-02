package be.appfoundry.mosbyrx.ui.presenter;

import java.util.List;

import javax.inject.Inject;

import be.appfoundry.mvp.mosby.BaseRxPresenter;
import be.appfoundry.mosbyrx.data.entity.GitHubRepo;
import be.appfoundry.mosbyrx.data.service.GitHubAPI;
import be.appfoundry.mosbyrx.ui.view.repo.RepoView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Subscriber;
import timber.log.Timber;

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

    @Override
    public void loadRepoList(boolean isRefresh) {
        Timber.i("Subscribing");
        new RxIOSubscription<List<GitHubRepo>>().add(
                gitHubAPI.getRepos(),
                new Subscriber<List<GitHubRepo>>() {
                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepos) {
                        Timber.i("Result = %d items", gitHubRepos.size());
                        getView().showRepos(gitHubRepos);
                    }

                    @Override
                    public void onCompleted() {
                        Timber.i("Done");
                        getView().hideLoadingIndicator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i("Error - %s", e.getMessage());
                        getView().showMessage(e.getMessage());
                        getView().hideLoadingIndicator();
                    }
                }
        );
    }

    @Override
    public void loadRepoListDangerously(boolean isRefresh) {
        Timber.i("Start Call");
        gitHubAPI.getRepos(new Callback<List<GitHubRepo>>() {
            @Override
            public void success(List<GitHubRepo> gitHubRepos, Response response) {
                Timber.i("Success Callback, Result = %d items", gitHubRepos.size());
                getView().showRepos(gitHubRepos);
                getView().hideLoadingIndicator();
                Timber.i("Either we crashed with nullpointers because Activity is destroyed / " +
                        "View is detached or we see this log message...");
            }

            @Override
            public void failure(RetrofitError e) {
                Timber.i("Error Callback - %s", e.getMessage());
                getView().showMessage(e.getMessage());
                getView().hideLoadingIndicator();
            }
        });
    }
}
