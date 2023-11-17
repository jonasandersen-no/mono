package com.bjoggis.kassalapp;

class KassalappServiceImpl implements KassalappService {

  private final KassalappApi kassalappApi;

  KassalappServiceImpl(KassalappApi kassalappApi) {
    this.kassalappApi = kassalappApi;
  }

  @Override
  public String getProductByEan(String ean) {
    return kassalappApi.getProductByEan(ean);
  }
}
