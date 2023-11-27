package com.bjoggis.debug.command;

import com.bjoggis.debug.table.DesignTable;
import com.bjoggis.linode.adapter.in.CreateInstanceResponse;
import com.bjoggis.linode.adapter.in.GetInstanceResponse;
import com.bjoggis.linode.configuration.properties.LinodeProperties;
import com.bjoggis.linode.domain.LinodeService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.table.Table;
import org.springframework.util.ResourceUtils;

@Command(command = "linode")
public class LinodeCommands {

  private final LinodeService service;
  private final LinodeProperties properties;

  public LinodeCommands(LinodeService service, LinodeProperties properties) {
    this.service = service;
    this.properties = properties;
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

  @Command(command = "delete")
  public String delete(Long id) {
    service.deleteInstance(id);

    return "Instance deleted: " + id;
  }

  @Command(command = "connect")
  public String connect(Long id) throws IOException {
    GetInstanceResponse instance = service.getLinodeInstanceById(id);
    final String username = "root";
    final String password = properties.instance().password();
    final String host = instance.ip();
    final long defaultTimeoutSeconds = 60;
    File file = ResourceUtils.getFile("classpath:commands");
    final String command = Files.readString(file.toPath());


    SshClient client = SshClient.setUpDefaultClient();
    client.start();

    try (ClientSession session = client.connect(username, host, 22)
        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
      session.addPasswordIdentity(password);
      session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);

      try (ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
          ClientChannel channel = session.createChannel(Channel.CHANNEL_SHELL)) {
        channel.setOut(responseStream);
        try {
          channel.open().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);
          try (OutputStream pipedIn = channel.getInvertedIn()) {
            pipedIn.write(command.getBytes());
            pipedIn.flush();
          }

          channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED),
              TimeUnit.SECONDS.toMillis(defaultTimeoutSeconds));
          return responseStream.toString();
        } finally {
          channel.close(false);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } finally {
      client.stop();
    }

  }
}
