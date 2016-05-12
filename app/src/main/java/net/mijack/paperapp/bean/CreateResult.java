package net.mijack.paperapp.bean;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class CreateResult {
private String localPath;
    private String status;
    private String url;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
