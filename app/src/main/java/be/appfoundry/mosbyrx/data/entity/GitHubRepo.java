package be.appfoundry.mosbyrx.data.entity;


/**
 * Created by janvancoppenolle on 31/08/15.
 */
public class GitHubRepo {
    String name;
    String html_url;
    String description;

    public GitHubRepo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return html_url;
    }

    public void setUrl(String url) {
        this.html_url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
