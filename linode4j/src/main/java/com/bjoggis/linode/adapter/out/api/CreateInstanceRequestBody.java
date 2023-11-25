package com.bjoggis.linode.adapter.out.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CreateInstanceRequestBody {

  private String region;
  private String type;
  private String image;

  @JsonProperty("swap_size")
  private int swapSize;

  @JsonProperty("root_pass")
  private String rootPassword;
  private String label;
  private List<String> tags;

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

  public void setImage(String image) {
    this.image = image;
  }

  public String getImage() {
    return image;
  }

  public void setSwapSize(int swapSize) {
    this.swapSize = swapSize;
  }

  public int getSwapSize() {
    return swapSize;
  }

  public void setRootPassword(String rootPassword) {
    this.rootPassword = rootPassword;
  }

  public String getRootPassword() {
    return rootPassword;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public <E> void setTags(List<String> tags) {
    this.tags = tags;
  }

  public List<String> getTags() {
    return tags;
  }
}
