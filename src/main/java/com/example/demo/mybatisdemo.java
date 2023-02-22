package com.example.demo;

import demo.postgres.dao.login.Account;
import demo.postgres.dao.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class mybatisdemo {
    @Autowired
    private AccountMapper accountMapper;
    public List<Account> getObj(){
        return  accountMapper.selectAll();
    }
}
