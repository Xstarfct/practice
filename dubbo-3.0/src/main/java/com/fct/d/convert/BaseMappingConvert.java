package com.fct.d.convert;

import java.util.List;

/**
 * BaseMappingConvert
 *
 * @author fct
 * @version 2021-06-29 16:13
 */
public interface BaseMappingConvert<O, T> {

  O toOrigin(T t);

  List<O> toOriginList(List<T> list);

  T toTarget(O o);

  List<T> toTargetList(List<O> list);
}
