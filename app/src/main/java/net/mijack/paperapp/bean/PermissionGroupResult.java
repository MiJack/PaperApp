package net.mijack.paperapp.bean;

import java.util.Map;

/**
 * @author MiJack
 * @since 2016/5/17
 */
public class PermissionGroupResult {
    protected int id;
    protected String description;
    protected String label;
    protected String name;
    protected String priority;
    protected String permissionGroupFlags;
    protected int functionInvokeCount = -1;
    protected int permissionCount = 0;
    protected Map<String, PermissionResult> permissionMap;

    public PermissionGroupResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPermissionGroupFlags() {
        return permissionGroupFlags;
    }

    public void setPermissionGroupFlags(String permissionGroupFlags) {
        this.permissionGroupFlags = permissionGroupFlags;
    }

    public int getFunctionInvokeCount() {
        return functionInvokeCount;
    }

    public void setFunctionInvokeCount(int functionInvokeCount) {
        this.functionInvokeCount = functionInvokeCount;
    }

    public int getPermissionCount() {
        return permissionCount;
    }

    public void setPermissionCount(int permissionCount) {
        this.permissionCount = permissionCount;
    }

    public Map<String, PermissionResult> getPermissionMap() {
        return permissionMap;
    }

    public void setPermissionMap(Map<String, PermissionResult> permissionMap) {
        this.permissionMap = permissionMap;
    }
}
