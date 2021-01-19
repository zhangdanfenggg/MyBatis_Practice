package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

import static org.junit.Assert.*;

public class PrivilegeMapperTest extends BaseMapperTest {

    @Test
    public void selectPrivilegeByRoleId() {
        try (SqlSession sqlSession = getSqlSession()) {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            List<SysPrivilege> privilegeList = privilegeMapper.selectPrivilegeByRoleId(2L);
            Assert.assertTrue(privilegeList.size()>0);
        }
    }
}