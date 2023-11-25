package com.bjoggis.linode.model;

import com.bjoggis.linode.model.type.Addons;
import com.bjoggis.linode.model.type.Price;
import com.bjoggis.linode.model.type.RegionPrice;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record InstanceType(String id, String label, Price price, List<RegionPrice> region_prices,
                           Addons addons, int memory, int disk, int transfer, int vcpus, int gpus,
                           int network_out, @JsonProperty("class") String clazz, String successor) {

}

