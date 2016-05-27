package net.mijack.paperapp.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class CreateResult {
    private String localPath;
    private STATUS status;
    private String url;
    @JsonProperty("md5")
    private String fileMD5;

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

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }
}
