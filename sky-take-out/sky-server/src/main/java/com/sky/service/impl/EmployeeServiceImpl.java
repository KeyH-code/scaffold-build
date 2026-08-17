package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee login(EmployeeLoginDTO employeeLoginDTO){
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        // 1. 根据用户名称查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        // 2. 处理各种异常情况（用户名不存在，密码不对，账号被锁定）
        if(employee == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码比对
        // TODO后期需要进行md5加密，然后再进行比对
        if(!password.equals(employee.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if(employee.getStatus() == StatusConstant.DISABLE){
            throw  new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 3. 返回实体对象
        return employee;
    }
}
