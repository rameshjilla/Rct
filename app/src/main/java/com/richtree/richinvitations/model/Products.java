package com.richtree.richinvitations.model;

import java.util.List;

/**
 * Created by TGT on 3/29/2018.
 */

public class Products {
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String id;
  private String name;

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  private String image;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private String description;


}
