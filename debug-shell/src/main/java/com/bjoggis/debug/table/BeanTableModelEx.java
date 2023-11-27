package com.bjoggis.debug.table;


import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.shell.table.TableModel;

public class BeanTableModelEx<T> extends TableModel {

  private List<BeanWrapper> data;

  private List<String> headers;

  public static <T> BeanTableModelEx<T> builder() {
    return new BeanTableModelEx<>();
  }

  public BeanTableModelEx<T> data(Iterable<T> list) {
    this.data = StreamSupport.stream(list.spliterator(), true)
        .map(BeanWrapperImpl::new)
        .collect(Collectors.toList());
    return this;
  }

  public BeanTableModelEx<T> clazz(Class<T> clazz) {
    this.headers = new ArrayList<>();
    for (PropertyDescriptor propertyName : BeanUtils.getPropertyDescriptors(clazz)) {
      if ("class".equals(propertyName.getName())) {
        continue;
      }
      headers.add(propertyName.getName());
    }
    return this;
  }

  /**
   * @return the number of rows that can be queried. Values between 0 and {@code rowCount-1}
   * inclusive are valid values.
   */
  @Override
  public int getRowCount() {
    // header row + 1
    return data.size() + 1;
  }

  /**
   * @return the number of columns that can be queried. Values between 0 and {@code columnCount-1}
   * inclusive are valid values.
   */
  @Override
  public int getColumnCount() {
    return headers.size();
  }

  /**
   * @param row    the row that is being queried
   * @param column the column that is being queried
   * @return the data value to be displayed at a given row and column, which may be null.
   */
  @Override
  public Object getValue(int row, int column) {
    if (row == 0) {
      return headers.get(column);
    }
    return data.get(row - 1).getPropertyValue(headers.get(column));
  }
}