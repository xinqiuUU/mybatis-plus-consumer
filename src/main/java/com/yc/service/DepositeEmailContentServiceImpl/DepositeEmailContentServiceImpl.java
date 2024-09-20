package com.yc.service.DepositeEmailContentServiceImpl;

import com.yc.model.Account;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

@Service("deposit")
public class DepositeEmailContentServiceImpl implements EmailContentStrategyService{
    @Autowired
    private VelocityContext context;// 模板上下文
    @Autowired
    private VelocityEngine velocityEngine; // 模板引擎
    @Autowired
    @Qualifier("depositeTemplate") // 模板名称depositeTemplate注入
    private Template depositeTemplate;

    @Autowired
    @Qualifier("fullDf") // 模板名称withdrawTemplate注入
    private DateFormat fullDf;
    @Autowired
    @Qualifier("partDf") // 模板名称withdrawTemplate注入
    private DateFormat partDf;

    @Override
    public String getEmailContent(Account account, double money, int toaccountid) {
        Date d = new Date();
        //托管了
//        VelocityContext context = new VelocityContext();
        context.put("accountid", account.getAccountid());
        context.put("email", account.getEmail());
        context.put("subject", "存款操作");
        context.put("optime", fullDf.format(d));
        context.put("money",money );
        context.put("balance", account.getBalance());
        context.put("currentDate", partDf.format(d));

        try(StringWriter writer = new StringWriter()){
            depositeTemplate.merge(context,writer);  //合并内容。替换占位符
            return writer.toString();       //从流获取最终的字符后
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }
}
