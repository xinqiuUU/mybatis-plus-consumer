package com.yc.service.DepositeEmailContentServiceImpl;

import com.yc.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class StrategyContext {

    private final Map<String,EmailContentStrategyService> strategyMap;
    //*****关键点   依赖注入 键是 策略的名称  值是 策略的实现类
    @Autowired
    public StrategyContext(Map<String, EmailContentStrategyService> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public String getEmailContent(String opType, Account account, double money, int toaccountid) {
        // 根据opType获取对应的策略
        EmailContentStrategyService strategy = strategyMap.get(opType);
        if ( strategy == null){
            log.error("没有找到对应的策略:"+opType);
            throw new IllegalArgumentException("没有找到对应的策略:"+opType);
        }
        return strategy.getEmailContent(account, money, toaccountid);
    }


}
