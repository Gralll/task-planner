/*
 * Copyright 2018: Thomson Reuters. All Rights Reserved. Proprietary and
 * Confidential information of Thomson Reuters. Disclosure, Use or Reproduction
 * without the written authorization of Thomson Reuters is prohibited.
 */
package com.gralll.taskplanner.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {

    private String idToken;

    public JwtToken() {
    }

    public JwtToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
