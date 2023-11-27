package com.bjoggis.debug;

import com.bjoggis.debug.command.LinodeCommands;
import com.bjoggis.linode.domain.LinodeService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.annotation.EnableCommand;

@AutoConfiguration
@EnableCommand(LinodeCommands.class)
public class DebugConfiguration {

  @Bean
  LinodeCommands linodeCommands(LinodeService service) {
    return new LinodeCommands(service);
  }
}
