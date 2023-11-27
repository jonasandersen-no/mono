package com.bjoggis.debug.command;

import com.bjoggis.debug.table.DesignTable;
import com.bjoggis.linode.adapter.in.CreateInstanceResponse;
import com.bjoggis.linode.adapter.in.GetInstanceResponse;
import com.bjoggis.linode.domain.LinodeService;
import java.util.List;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.table.Table;

@Command(command = "linode")
public class LinodeCommands {

  private final LinodeService service;

  public LinodeCommands(LinodeService service) {
    this.service = service;
  }

  @Command(command = "create")
  public String create() {
    CreateInstanceResponse instance = service.createInstance();

    return "Instance created: " + instance.linodeId() + " with ip: " + instance.ip();
  }

  @Command(command = "find")
  public Table find(Long id) {
    GetInstanceResponse linodeInstanceById = service.getLinodeInstanceById(id);

    return DesignTable.designTableStyle(GetInstanceResponse.class, List.of(linodeInstanceById));
  }

  @Command(command = "list")
  public Table list() {
    List<GetInstanceResponse> instances = service.getAllActiveLinodeInstances();

    return DesignTable.designTableStyle(GetInstanceResponse.class, instances);
  }
}
