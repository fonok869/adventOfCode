package com.fmolnar.code.basic;

import java.io.Serializable;
import java.util.Arrays;

public abstract class AbstractLspBean implements Serializable {
    public AbstractLspBean() {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass().equals(obj.getClass())) {
            Object[] properties1 = this.getFunctionalPropertiesForObjectEquality();
            Object[] properties2 = ((AbstractLspBean) obj).getFunctionalPropertiesForObjectEquality();
            return Arrays.deepEquals(properties1, properties2);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getFunctionalPropertiesForObjectEquality() == null ? super.hashCode() : Arrays.deepHashCode(this.getFunctionalPropertiesForObjectEquality());
    }

    public abstract Object[] getFunctionalPropertiesForObjectEquality();

    public String toString() {
        return this.getClass().getSimpleName() + " : " + Arrays.deepToString(this.getFunctionalPropertiesForObjectEquality());
    }
}

