package net.mijack.paperapp.bean;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class CreateResult {
private String localPath;
    private STATUS status;
    private String url;

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
