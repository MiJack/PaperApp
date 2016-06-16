package net.mijack.paperapp.bean;


import java.util.Map;

/**
 * @author MiJack
 * @since 2016/5/12
 */
public class QueryResult {
    private STATUS status;
    private String taskId;
    private String apkLabel;
    private String packageName;
    private String versionCode;
    private String versionName;
    private String fileMd5;
    private int install;
    private int uninstall;
    private Map<String, PermissionGroupResult> permissionGroupResultMap;
    private Map<String, PermissionResult> permissionResultMap;

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
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

    public Map<String, PermissionGroupResult> getPermissionGroupResultMap() {
        return permissionGroupResultMap;
    }

    public void setPermissionGroupResultMap(Map<String, PermissionGroupResult> permissionGroupResultMap) {
        this.permissionGroupResultMap = permissionGroupResultMap;
    }

    public Map<String, PermissionResult> getPermissionResultMap() {
        return permissionResultMap;
    }

    public void setPermissionResultMap(Map<String, PermissionResult> permissionResultMap) {
        this.permissionResultMap = permissionResultMap;
    }

    public int getInstall() {
        return install;
    }

    public void setInstall(int install) {
        this.install = install;
    }

    public int getUninstall() {
        return uninstall;
    }

    public void setUninstall(int uninstall) {
        this.uninstall = uninstall;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "\nstatus=" + status +
                ", \ntaskId='" + taskId + '\'' +
                ", \napkLabel='" + apkLabel + '\'' +
                ", \npackageName='" + packageName + '\'' +
                ", \nversionCode='" + versionCode + '\'' +
                ", \nversionName='" + versionName + '\'' +
                ", \npermissionGroupResultMap=" + permissionGroupResultMap +
                ", \npermissionResultMap=" + permissionResultMap +
                '}';
    }
}
