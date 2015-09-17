package be.appfoundry.mosbyrx.ui.presenter;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

import java.util.List;

import be.appfoundry.mosbyrx.data.entity.GitHubRepo;
import be.appfoundry.mosbyrx.ui.view.repo.RepoView;

public interface RepoPresenter
        extends MvpPresenter<RepoView> {
        List<GitHubRepo> loadRepoListSynchonously();
        void loadRepoListAsynchonously();
        void loadRepoListRx();
}
