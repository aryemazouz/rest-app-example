package com.rest.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)    //For de-serialization
public class Conf {

    @JsonProperty("vms")
    private Set<VirtualMachine> vms = new HashSet();

    @JsonProperty("fw_rules")
    private Set<FirewallRule> rules;
}
