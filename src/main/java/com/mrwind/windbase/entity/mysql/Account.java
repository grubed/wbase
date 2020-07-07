package com.mrwind.windbase.entity.mysql;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CL-J on 2018/7/18.
 *
 *
 * 资金账户
 */


@Entity
@Table(name = "account")
public class Account {

 @Id
 private String userId;

 private BigDecimal amount = BigDecimal.valueOf(0.00);
 private Integer currency = 0;
 private BigDecimal cash = BigDecimal.valueOf(0.00);
 @UpdateTimestamp
 private Date updateTime;

 public Integer getCurrency() {
  return currency;
 }

 public void setCurrency(Integer currency) {
  this.currency = currency;
 }

 public BigDecimal getCash() {
  return cash;
 }

 public void setCash(BigDecimal cash) {
  this.cash = cash;
 }

 public Account() {
 }
 public Account(String userId) {
  this.userId = userId;
  this.amount = new BigDecimal(0);
 }
 public Account(String userId, BigDecimal amount) {
  this.userId = userId;
  this.amount = amount;
 }

 public String getUserId() {
  return userId;
 }

 public void setUserId(String userId) {
  this.userId = userId;
 }

 public BigDecimal getAmount() {
  return amount;
 }

 public void setAmount(BigDecimal amount) {
  this.amount = amount;
 }

 public Date getUpdateTime() {
  return updateTime;
 }

 public void setUpdateTime(Date updateTime) {
  this.updateTime = updateTime;
 }
}
