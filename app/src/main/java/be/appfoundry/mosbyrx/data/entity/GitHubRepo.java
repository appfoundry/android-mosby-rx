package be.appfoundry.mosbyrx.data.entity;


/**
 * Created by janvancoppenolle on 31/08/15.
 */
public class GitHubRepo {
    // @SerializedName("name")
    String name;

    // @SerializedName("html_url")
    String url;

    // @SerializedName("description")
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
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
