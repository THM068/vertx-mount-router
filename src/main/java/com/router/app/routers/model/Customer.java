package com.router.app.routers.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity(name = "customer_list")
public class Customer {

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getZip_code() {
    return zip_code;
  }

  public String getPhone() {
    return phone;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getNotes() {
    return notes;
  }

  public Short getSid() {
    return sid;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Long id;

  @Column
  private String name;

  @Column(length = 50)
  private String address;

  @Column(name = "`zip code`", length = 10)
  private String zip_code;

  @Column(name = "phone", length = 20)
  private String phone;

  @Column(name = "city", length = 50)
  private String city;

  @Column(name = "country", length = 50)
  private String country;

  @Column(name = "notes", length = 6)
  private String notes;

  @Column(name = "SID")
  private Short sid;

}



