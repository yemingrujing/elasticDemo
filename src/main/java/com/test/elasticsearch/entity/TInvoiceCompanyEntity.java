package com.test.elasticsearch.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @ProjectName: elasticsearch
 * @Package: com.test.elasticsearch.entity
 * @ClassName: TInvoiceCompanyEntity
 * @Author: guang
 * @Description: 发票公司信息表
 * @Date: 2019/5/20 10:09
 * @Version: 1.0
 */
@Entity
@Table(name = "t_invoice_company", schema = "commerce", catalog = "")
public class TInvoiceCompanyEntity {
    private int id;
    private String code;
    private String kpName;
    private String kpCode;
    private String bankName;
    private String bankAccount;
    private String telephone;
    private String registerAddress;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "kp_name")
    public String getKpName() {
        return kpName;
    }

    public void setKpName(String kpName) {
        this.kpName = kpName;
    }

    @Basic
    @Column(name = "kp_code")
    public String getKpCode() {
        return kpCode;
    }

    public void setKpCode(String kpCode) {
        this.kpCode = kpCode;
    }

    @Basic
    @Column(name = "bank_name")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Basic
    @Column(name = "bank_account")
    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "register_address")
    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TInvoiceCompanyEntity that = (TInvoiceCompanyEntity) o;
        return id == that.id &&
                Objects.equals(code, that.code) &&
                Objects.equals(kpName, that.kpName) &&
                Objects.equals(kpCode, that.kpCode) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankAccount, that.bankAccount) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(registerAddress, that.registerAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, kpName, kpCode, bankName, bankAccount, telephone, registerAddress);
    }
}
