package com.firstutility.telco.tp.market.determiner.domain;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class Address {

    private final String addressLine1;
    private final String addressLine2;
    private final String addressLine3;
    private final String addressLine4;
    private final String addressLine5;
    private final String postCode;

    public Address(final String addressLine1, final String addressLine2, final String addressLine3, final String addressLine4,
            final String addressLine5, final String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.addressLine4 = addressLine4;
        this.addressLine5 = addressLine5;
        this.postCode = postCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public String getPostCode() {
        return postCode;
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

