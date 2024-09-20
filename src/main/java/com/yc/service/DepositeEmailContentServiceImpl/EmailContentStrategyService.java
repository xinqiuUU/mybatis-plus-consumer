package com.yc.service.DepositeEmailContentServiceImpl;

import com.yc.model.Account;

/*
    策略接口
 */
public interface EmailContentStrategyService {

    default String getEmailContent( Account account, double money, int toaccountid) {
        return "";
    }
}
