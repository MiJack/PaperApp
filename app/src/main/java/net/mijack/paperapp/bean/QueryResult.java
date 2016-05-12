package net.mijack.paperapp.bean;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class QueryResult {
    private String status;
    private String taskId;
    private String apkLabel;
    private String packageName;
    private String versionCode;
    private String versionName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getApkLabel() {
        return apkLabel;
    }

    public void setApkLabel(String apkLabel) {
        this.apkLabel = apkLabel;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "status='" + status + '\'' +
                ", taskId='" + taskId + '\'' +
                ", apkLabel='" + apkLabel + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                '}';
    }
}
