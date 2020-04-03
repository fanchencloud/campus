package cn.fanchencloud.campus.service.impl;

import cn.fanchencloud.campus.mapper.LocalAccountMapper;
import cn.fanchencloud.campus.mapper.PersonInfoMapper;
import cn.fanchencloud.campus.entity.LocalAccount;
import cn.fanchencloud.campus.entity.PersonInfo;
import cn.fanchencloud.campus.model.JsonResponse;
import cn.fanchencloud.campus.service.LocalAccountService;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

/**
 * Created by handsome programmer.
 * User: chen
 * Date: 2019/4/16
 * Time: 7:35
 * Description: 本地账号服务实现类
 *
 * @author chen
 */
@Service
public class LocalAccountServiceImpl implements LocalAccountService {

    private static final Logger logger = LoggerFactory.getLogger(LocalAccountServiceImpl.class);

    /**
     * 注入本地账户数据持久层
     */
    private LocalAccountMapper localAccountMapper;

    /**
     * 注入用户信息数据持久层
     */
    private PersonInfoMapper personInfoMapper;

    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    public int login(LocalAccount localAccount) {
        // 加密数据
        String username = localAccount.getUsername();
        String password = Md5Crypt.apr1Crypt(username, localAccount.getPassword());
        LocalAccount record = localAccountMapper.getRecordByUsernameAndPassword(username, password);
        if (record != null) {
            // 通过userId 查询用户信息
            PersonInfo personInfo = personInfoMapper.queryById(record.getUserId());
            if (personInfo.getEnableStatus() == 0) {
                return 3;
            }
            // 用户名密码正确
            return 1;
        }
        return 2;
    }

    @Transactional
    @Override
    public JsonResponse register(LocalAccount localAccount) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("register");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
        JsonResponse result = null;
        // 查找用户名是否已被占用
        boolean flag = localAccountMapper.isExist(localAccount.getUsername());
        try {
            if (flag) {
                result = JsonResponse.errorMsg("用户名已被使用！");
                logger.error("用户名已被使用！");
                return result;
            }
            // 设置本地账号的默认属性
            localAccount.setCreateTime(new Date());
            localAccount.setLastEditTime(localAccount.getCreateTime());
            PersonInfo personInfo = new PersonInfo();
            personInfo.setCreateTime(new Date());
            personInfo.setLastEditTime(personInfo.getCreateTime());
            // 默认为账号不可用状态，需要管理员审核通过
            personInfo.setEnableStatus(0);
            // 设置用户的注册类别
            personInfo.setUserType(localAccount.getUserType());
            // 将用户信息持久化到数据库中
            int number = personInfoMapper.addPersonInfo(personInfo);
            if (number <= 0) {
                logger.error("添加用户信息失败！");
                throw new RuntimeException("添加用户信息失败！");
            }
            // 将本地账号持久化到数据库
            localAccount.setUserId(personInfo.getId());
            localAccount.setPassword(Md5Crypt.apr1Crypt(localAccount.getUsername(), localAccount.getPassword()));
            number = localAccountMapper.addLocalAccount(localAccount);
            if (number <= 0) {
                logger.error("添加本地账号信息失败！");
                throw new RuntimeException("添加本地账号信息失败！");
            }
            result = JsonResponse.ok("注册成功！");
        } catch (RuntimeException e) {
            dataSourceTransactionManager.rollback(status);
            result = JsonResponse.errorMsg("注册失败");
        }
        return result;
    }

    @Override
    public boolean modifyPassword(String originalPassword, String newPassword, LocalAccount localAccount) {
        String password = Md5Crypt.apr1Crypt(localAccount.getUsername(), originalPassword);
        String temp = Md5Crypt.apr1Crypt("admin", "123456");
        LocalAccount record = localAccountMapper.getRecordByUsernameAndPassword(localAccount.getUsername(), password);
        if (record == null) {
            return false;
        }
        record.setPassword(Md5Crypt.apr1Crypt(localAccount.getUsername(), newPassword));
        record.setLastEditTime(new Date());
        int i = localAccountMapper.updateLocalAccount(record);
        return i != 0;
    }

    @Override
    public LocalAccount getLocalAccount(String username) {
        return localAccountMapper.getRecordByUsername(username);
    }

    @Autowired
    public void setLocalAccountMapper(LocalAccountMapper localAccountMapper) {
        this.localAccountMapper = localAccountMapper;
    }


    @Autowired
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Autowired
    public void setPersonInfoMapper(PersonInfoMapper personInfoMapper) {
        this.personInfoMapper = personInfoMapper;
    }
}