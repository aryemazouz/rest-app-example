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
public class VirtualMachine {

    @JsonProperty("vm_id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tags")
    private Set<String> tags = new HashSet();

}
