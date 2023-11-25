package com.bjoggis.linode.adapter.out;

import com.bjoggis.linode.model.LinodeInstance;
import com.bjoggis.linode.model.instance.Page;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

@Validated
public interface LinodeInterface {

  @GetExchange("/v4/linode/instances")
  Page<LinodeInstance> list(@RequestParam Integer page, @RequestParam("page_size") @Min(25) @Max(500) Integer pageSize);

  @GetExchange("/v4/linode/types")
  String types();
}
