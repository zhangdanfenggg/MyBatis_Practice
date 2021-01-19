package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public interface PrivilegeMapper {

    /**
     * 通过roleId查询该角色的所有权限信息
     * @param roleId
     * @return
     */
    List<SysPrivilege> selectPrivilegeByRoleId(@Param("roleId") Long roleId);
}
