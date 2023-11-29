package com.bjoggis.linode.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public record Volume(int id, String status, String label, LocalDateTime created,
                     LocalDateTime updated,
                     @JsonProperty("filesystem_path") String fileSystemPath, int size,
                     @JsonProperty("linode_id") Integer linodeId,
                     @JsonProperty("linode_label") String linodeLabel,
                     String region, List<String> tags,
                     @JsonProperty("hardware_type") String hardwareType) {

}
