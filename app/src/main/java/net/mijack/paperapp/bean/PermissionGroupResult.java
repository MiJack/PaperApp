package net.mijack.paperapp.bean;

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
    protected int count = -1;

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

    public int getCount() {
        return count;
    }

    public void addCount(int count) {
        if (count<=0){return;}
        if (count == -1) {
            this.count = count;
        } else {
            this.count = this.count + count;
        }
    }
}
