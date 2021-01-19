package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;

import javax.management.relation.Role;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void selectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = roleMapper.selectById(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员", sysRole.getRoleName());
        }
    }

    @Test
    public void selectById2() {
        try (SqlSession sqlSession = getSqlSession()) {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = roleMapper.selectById2(1L);
            Assert.assertNotNull(sysRole);
            Assert.assertEquals("管理员", sysRole.getRoleName());
        }
    }

    @Test
    public void selectAll() {
        try (SqlSession sqlSession = getSqlSession()) {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectAll();
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size()>0);
            Assert.assertNotNull(roleList.get(0).getRoleName());
        }
    }

    @Test
    public void insert() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setId(3L);
            sysRole.setRoleName("测试-用户");
            sysRole.setCreateBy(1L);
            sysRole.setCreateTime(new Date());
            Assert.assertEquals(1,roleMapper.insert(sysRole));
            Assert.assertNotNull(roleMapper.selectById(3L));
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void insert2() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("测试-用户");
            sysRole.setCreateBy(1L);
            sysRole.setCreateTime(new Date());
            Assert.assertEquals(1,roleMapper.insert2(sysRole));
            Assert.assertNotNull(roleMapper.selectById(4L));
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void insert3() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("测试-用户");
            sysRole.setCreateBy(1L);
            sysRole.setCreateTime(new Date());
            Assert.assertEquals(1,roleMapper.insert3(sysRole));
//            Assert.assertNotNull(roleMapper.selectById(3L));
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void updateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            SysRole sysRole = roleMapper.selectById(2L);
            sysRole.setRoleName("特战论坛");
            Assert.assertEquals(1,roleMapper.updateById(sysRole));
            Assert.assertEquals("特战论坛",roleMapper.selectById(2L).getRoleName());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void deleteById() {
        SqlSession sqlSession = getSqlSession();
        try {
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            Assert.assertEquals(1,roleMapper.deleteById(2L));
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void selectRoleByUserId() {
        try (SqlSession sqlSession = getSqlSession()){
            RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
            List<SysRole> roleList = roleMapper.selectRoleByUserId(1L);
            Assert.assertEquals(2,roleList.size());
        }
    }
}