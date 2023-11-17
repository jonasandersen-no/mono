package com.bjoggis.kassalapp;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

interface KassalappApi {

  @GetExchange("/products/ean/{ean}")
  String getProductByEan(@PathVariable String ean);
}
