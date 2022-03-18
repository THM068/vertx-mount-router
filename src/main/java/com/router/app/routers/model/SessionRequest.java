package com.router.app.routers.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionRequest {

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String username;
  private String password;

  public SessionRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }
}