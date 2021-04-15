package sudoku.model;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sudoku.consts.Consts;
import sudoku.exceptions.InvalidFieldValueException;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) throws InvalidFieldValueException {
        if (value < 0 || value > Consts.MAX_VALUE) {
            throw new InvalidFieldValueException(
                    Consts.INVALID_VALUE,
                    new IllegalArgumentException()
            );
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(13, 31)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }

    @Override
    protected SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.getFieldValue(), o.getFieldValue());
    }
}