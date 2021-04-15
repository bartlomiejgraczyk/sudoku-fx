package sudoku.model;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sudoku.consts.Consts;
import sudoku.exceptions.InvalidContainerLengthException;
import sudoku.exceptions.NullContainerException;
import sudoku.exceptions.SudokuContainerException;

public abstract class SudokuContainer implements Serializable, Cloneable {

    protected final List<SudokuField> values;
    private final HashSet<SudokuField> set = new HashSet<>();

    public SudokuContainer(List<SudokuField> values) throws SudokuContainerException {
        try {
            this.values = Objects.requireNonNull(values, Consts.NULL_ARRAY);
        } catch (NullPointerException exception) {
            throw new NullContainerException(
                    Consts.NULL_ARRAY,
                    exception
            );
        }
        if (values.size() != Consts.SIZE) {
            throw new InvalidContainerLengthException(
                    Consts.INVALID_LENGTH,
                    new InvalidParameterException()
            );
        }
        for (SudokuField field : values) {
            try {
                Objects.requireNonNull(field, Consts.NULL_ELEMENT);
            } catch (NullPointerException exception) {
                throw new NullContainerException(Consts.NULL_ARRAY, exception);
            }
        }
    }

    public boolean verify() {
        set.clear();
        set.addAll(values);
        return set.size() == Consts.SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuContainer that = (SudokuContainer) o;

        return new EqualsBuilder()
                .append(values, that.values)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(19, 41)
                .append(values)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("values", values)
                .toString();
    }

    @Override
    protected SudokuContainer clone() throws CloneNotSupportedException {
        return (SudokuContainer) super.clone();
    }
}
