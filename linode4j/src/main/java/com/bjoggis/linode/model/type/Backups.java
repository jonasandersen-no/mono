package com.bjoggis.linode.model.type;

import java.util.List;

public record Backups(Price price, List<RegionPrice> region_prices) {

}
