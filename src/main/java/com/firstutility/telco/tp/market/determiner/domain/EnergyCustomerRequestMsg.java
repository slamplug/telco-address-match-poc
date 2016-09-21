package com.firstutility.telco.tp.market.determiner.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class EnergyCustomerRequestMsg {

    private final String fileName;

    private final String customerNumber;

    private final String cli;

    private final Address address;

    public EnergyCustomerRequestMsg(final String fileName, final String customerNumber, final String cli, final Address address) {
        this.fileName = fileName;
        this.customerNumber = customerNumber;
        this.cli = cli;
        this.address = address;
    }

    public String getFileName() { return fileName; }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getCli() {
        return cli;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
