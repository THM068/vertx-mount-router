package com.router.app.routers.services.endpoints;

public class Country {
  private String country;
  private Long countryId;

  private Country(String country, Long countryId) {
    this.country = country;
    this.countryId = countryId;
  }

  public static Country create(String country, Long country_id) {
    return new Country(country, country_id);
  }


  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public Long getCountryId() {
    return countryId;
  }

  public void setCountryId(Long countryId) {
    this.countryId = countryId;
  }


}
