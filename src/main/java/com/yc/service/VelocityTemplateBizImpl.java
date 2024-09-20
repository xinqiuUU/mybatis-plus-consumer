package com.yc.service;

import com.yc.model.Account;
import com.yc.service.DepositeEmailContentServiceImpl.StrategyContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

@Component
public class VelocityTemplateBizImpl {

    @Autowired
    private StrategyContext strategyContext;

    public String genEmailContent(String opType, Account account, double money, int toaccountid) {
        String info = "";
        info = strategyContext.getEmailContent(opType, account, money, toaccountid);
        return info;
    }
}
