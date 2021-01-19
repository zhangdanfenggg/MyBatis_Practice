package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.*;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById(){
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectById方法,查询id = 1的用户
            SysUser user = userMapper.selectById(1L);
            //user不为空
            Assert.assertNotNull(user);
            //userName = admin
            Assert.assertEquals("admin", user.getUserName());
        }
    }

    @Test
    public void testSelectAll(){
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectById方法,查询id = 1的用户
            List<SysUser> userList = userMapper.selectAll();
            //结果不为空
            Assert.assertNotNull(userList);
            //用户数量大于0个
            Assert.assertTrue(userList.size()>0);
        }
    }

    @Test
    public void selectRolesByUserId() {
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //调用selectById方法,查询id = 1的用户
            List<SysRole> userList = userMapper.selectRolesByUserId(1L);
            //结果不为空
            Assert.assertNotNull(userList);
            //用户数量大于0个
            Assert.assertTrue(userList.size()>0);
        }
    }

    @Test
    public void insert() {
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.tk");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1,2,3});
            user.setCreateTime(new Date());
            int result = userMapper.insert(user);
            //结果不为空
            Assert.assertNull(user.getId());
            //用户数量大于0个
            Assert.assertEquals(1,result);
        }
    }

    @Test
    public void insert2() {
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test-selective");
            user.setUserPassword("123456");
            user.setUserInfo("test info");
            user.setCreateTime(new Date());
            int result = userMapper.insert2(user);
            //结果不为空
            user = userMapper.selectById(user.getId());
            Assert.assertEquals("test@mybatis.tk",user.getUserEmail());
        }
    }

    @Test
    public void insert3() {
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.tk");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1,2,3});
            user.setCreateTime(new Date());
            int result = userMapper.insert3(user);
            //结果不为空
            Assert.assertNotNull(user.getId());
            //用户数量大于0个
            Assert.assertEquals(1,result);
        }
    }

    @Test
    public void updateById() {
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin", user.getUserName());
            user.setUserName("admin_test");
            user.setUserEmail("test@mybatis.tk");
            int result = userMapper.updateById(user);
            user = userMapper.selectById(1L);
            Assert.assertEquals("admin_test", user.getUserName());
            //用户数量大于0个
            Assert.assertEquals(1, result);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void deleteById() {
        //获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            SysUser user1 = userMapper.selectById(1L);
            Assert.assertNotNull(user1);
            Assert.assertEquals(1,userMapper.deleteById(1L));
            Assert.assertNull(userMapper.selectById(1L));

            SysUser user2 = userMapper.selectById(1001L);
            Assert.assertNotNull(user2);
            Assert.assertEquals(1,userMapper.deleteById(user2));
            Assert.assertNull(userMapper.selectById(1001L));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 出现未解决的错误，https://www.cnblogs.com/tv151579/p/11565509.html,已解决，方法名写错了。
     */
    @Test
    public void selectRolesByIdAndRoleEnabled() {
        //获取sqlSession
        try (SqlSession sqlSession = getSqlSession()) {
            //获取UserMapper接口
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个user对象
            List<SysRole> userList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void selectByUser() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //只查询用户名时
            SysUser query = new SysUser();
            query.setUserName("ad");
            List<SysUser> userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);
            //只查询用户邮箱时
            query = new SysUser();
            query.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(query);
            Assert.assertTrue(userList.size() > 0);
            //当同时查询用户名和邮箱时
            query = new SysUser();
            query.setUserName("ad");
            query.setUserEmail("test@mybatis.tk");
            userList = userMapper.selectByUser(query);
            Assert.assertEquals(0, userList.size());
        }
    }

    @Test
    public void updateByIdSelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            //创建一个新的user对象
            SysUser user = new SysUser();
            //更新 id = 1 的用户
            user.setId(1L);
            //修改邮箱
            user.setUserEmail("test@mybatis.tk");
            //更新邮箱，注意：返回值为执行sql语句影响的行数
            int result = userMapper.updateByIdSelective(user);
            //只更新一条数据
            Assert.assertEquals(1,result);
            //根据id查询修改后的数据
            user = userMapper.selectById(1L);
            //修改后的名称保持不变，但是邮箱变了
            Assert.assertEquals("admin",user.getUserName());
            Assert.assertEquals("test@mybatis.tk",user.getUserEmail());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void selectByIdOrUserName() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser query = new SysUser();
            query.setId(1L);
            query.setUserName("admin");
            SysUser user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
            //当没有id时
            query.setId(null);
            user = userMapper.selectByIdOrUserName(query);
            Assert.assertNotNull(user);
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void selectByIdList() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<Long> idList = new ArrayList<>();
            idList.add(1L);
            idList.add(1001L);
            //业务逻辑中必须校验idList.size()>0
            Assert.assertTrue(idList.size() > 0);
            List<SysUser> userList = userMapper.selectByIdList(idList);
            Assert.assertEquals(2, userList.size());
        }
    }

    @Test
    public void selectByIdArray() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Long[] idArray = new Long[]{1L, 1001L};
            //业务逻辑中必须校验idList.size()>0
            Assert.assertTrue(idArray.length > 0);
            List<SysUser> userList = userMapper.selectByIdArray(idArray);
            Assert.assertEquals(2, userList.size());
        }
    }

    @Test
    public void insertList() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                SysUser user = new SysUser();
                user.setUserName("test" + i);
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.tk");
                userList.add(user);
            }
            //将新建的对象批量插入到数据库中
            //特别注意。这里的返回值是执行sql影响的行数
            int result = userMapper.insertList(userList);
            Assert.assertEquals(2, result);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void updateByMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            //查询条件，同样也是更新字段，必须保证该值存在
            map.put("id",1L);
            //要更新的其他字段
            map.put("user_email","test@mybatis.tk");
            map.put("user_password","12345678");
            //更新数据
            userMapper.updateByMap(map);
            //根据当前id查询修改后的数据
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("test@mybatis.tk",user.getUserEmail());
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void selectUserAndRoleById() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getRole());
        }
    }

    @Test
    public void selectUserAndRoleById2() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById2(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getRole());
        }
    }

    @Test
    public void selectUserAndRoleById3() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleById3(1001L);
            Assert.assertNotNull(user);
            Assert.assertNotNull(user.getRole());
        }
    }

    @Test
    public void selectUserAndRoleByIdSelect() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
            Assert.assertNotNull(user);
            System.out.println("调用user.equals(null)");
            user.equals(null);
            System.out.println("调用user.getRole()");
            Assert.assertNotNull(user.getRole());
        }
    }

    @Test
    public void selectAllUserAndRoles() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAllUserAndRoles();
            System.out.println("用户数："+userList.size());
            for (SysUser user: userList)
            {
                System.out.println("用户名："+user.getUserName());
                for (SysRole role: user.getRoleList())
                {
                    System.out.println("角色名："+role.getRoleName());
                    for (SysPrivilege privilege: role.getPrivilegeList())
                    {
                        System.out.println("权限名："+privilege.getPrivilegeName());
                    }
                }
            }
        }
    }

    @Test
    public void selectAllUserAndRolesSelect() {
        try (SqlSession sqlSession = getSqlSession()){
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
            System.out.println("用户名："+user.getUserName());
            for (SysRole sysRole: user.getRoleList())
            {
                System.out.println("角色名："+sysRole.getRoleName());
                for (SysPrivilege privilege: sysRole.getPrivilegeList())
                {
                    System.out.println("权限名："+privilege.getPrivilegeName());
                }
            }
        }
    }
}
