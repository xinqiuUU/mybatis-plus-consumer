package com.yc.model;

import lombok.Data;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

// 账户
@Data
public class Account implements Serializable {
    private int accountid;
    private double balance;
    private String email;
}
