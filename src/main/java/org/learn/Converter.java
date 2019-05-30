package org.learn;
@FunctionalInterface
public interface  Converter<F, T> {
   T convert(F from);
 default void hah() {

  }
}