package com.firstutility.telco.tp.market.determiner.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class DeterminedAvailabilityResponseMsg {

    private final String fileName;
    private final String customerNumber;
    private final String alk;
    private final String qualifier;
    private final String orderType;
    private final MpfLineCharacteristics mpfLineCharacteristics;
    private final NgaLineCharacteristics ngaLineCharacteristics;
    private final String currentProvider;
    private final boolean ttCustomerOnFibre;
    private final boolean ttCustomerMigratedToBT;

    public DeterminedAvailabilityResponseMsg(final String fileName, final String customerNumber, final String alk,
            final String qualifier, final String orderType, final MpfLineCharacteristics mpfLineCharacteristics,
            final NgaLineCharacteristics ngaLineCharacteristics, final String currentProvider,
            final boolean ttCustomerOnFibre, final boolean ttCustomerMigratedToBT) {
        this.fileName = fileName;

        this.customerNumber = customerNumber;
        this.alk = alk;
        this.qualifier = qualifier;
        this.orderType = orderType;
        this.mpfLineCharacteristics = mpfLineCharacteristics;
        this.ngaLineCharacteristics = ngaLineCharacteristics;
        this.currentProvider = currentProvider;
        this.ttCustomerOnFibre = ttCustomerOnFibre;
        this.ttCustomerMigratedToBT = ttCustomerMigratedToBT;
    }

    public String getFileName() { return fileName; }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getAlk() {
        return alk;
    }

    public String getQualifier() {
        return qualifier;
    }

    public String getOrderType() {
        return orderType;
    }

    public MpfLineCharacteristics getMpfLineCharacteristics() {
        return mpfLineCharacteristics;
    }

    public NgaLineCharacteristics getNgaLineCharacteristics() {
        return ngaLineCharacteristics;
    }

    public String getCurrentProvider() {
        return currentProvider;
    }

    public boolean isTtCustomerOnFibre() {
        return ttCustomerOnFibre;
    }

    public boolean isTtCustomerMigratedToBT() {
        return ttCustomerMigratedToBT;
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

