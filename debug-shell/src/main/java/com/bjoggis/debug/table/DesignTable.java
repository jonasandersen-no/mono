package com.bjoggis.debug.table;

import org.springframework.shell.table.Aligner;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.CellMatcher;
import org.springframework.shell.table.CellMatchers;
import org.springframework.shell.table.SimpleHorizontalAligner;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;

public class DesignTable {

  public static <T> Table designTableStyle(Class<T> clazz, Iterable<T> data) {
    return designTableStyle(clazz, data, BorderStyle.fancy_light_double_dash,
        BorderStyle.fancy_light, CellMatchers.table(), SimpleHorizontalAligner.center);
  }

  public static <T> Table designTableStyle(Class<T> clazz, Iterable<T> data,
      BorderStyle fullBorderStyle, BorderStyle innerBorderStyle, CellMatcher cellMatcher,
      Aligner aligner) {
    return new TableBuilder(BeanTableModelEx.<T>builder().clazz(clazz).data(data))
        .addFullBorder(fullBorderStyle)
        .addInnerBorder(innerBorderStyle)
        .on(cellMatcher)
        .addAligner(aligner)
        .build();
  }
}
