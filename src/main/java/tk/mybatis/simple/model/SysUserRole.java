package tk.mybatis.simple.model;

/**
 * 用户角色关联表
 */
public class SysUserRole {
    /**
     * 用户ID
     */
    private Long userID;
    /**
     * 角色ID
     */
    private Long roleID;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }
}
