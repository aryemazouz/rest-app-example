package com.rest.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)    //For de-serialization
public class FirewallRule {

    @JsonProperty("fw_id")
    private String id;

    @JsonProperty("source_tag")
    private String sourceTag;

    @JsonProperty("dest_tag")
    private String destTag;
}
