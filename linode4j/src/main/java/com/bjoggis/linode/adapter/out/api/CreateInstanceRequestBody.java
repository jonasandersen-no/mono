package com.bjoggis.linode.adapter.out.api;

public class CreateInstanceRequestBody {

  private String region;
  private String type;

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
