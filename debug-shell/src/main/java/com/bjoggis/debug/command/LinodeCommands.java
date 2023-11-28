package com.bjoggis.debug.command;

import com.bjoggis.debug.table.DesignTable;
import com.bjoggis.linode.adapter.in.CreateInstanceResponse;
import com.bjoggis.linode.adapter.in.GetInstanceResponse;
import com.bjoggis.linode.configuration.properties.LinodeProperties;
import com.bjoggis.linode.domain.LinodeService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.scp.client.CloseableScpClient;
import org.apache.sshd.scp.client.ScpClient;
import org.apache.sshd.scp.client.ScpClientCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.table.Table;
import org.springframework.util.ResourceUtils;

@Command(command = "linode")
public class LinodeCommands {

  private final Logger logger = LoggerFactory.getLogger(LinodeCommands.class);
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
    final long defaultTimeoutSeconds = 300;

    SshClient client = SshClient.setUpDefaultClient();
    client.start();

    uploadFile(client, username, host, defaultTimeoutSeconds, password, "classpath:run");
    uploadFile(client, username, host, defaultTimeoutSeconds, password,
        "classpath:docker-compose.yml");

    try (ClientSession session = client.connect(username, host, 22)
        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
      session.addPasswordIdentity(password);
      session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);

      String chmodOutput = session.executeRemoteCommand("chmod +x run");
      logger.info(chmodOutput);
      String response = session.executeRemoteCommand("./run");
      logger.info(response);

      return "OK";
    } finally {
      client.stop();
    }

  }

  private void uploadFile(SshClient sshClient, String username, String host,
      long defaultTimeoutSeconds, String password, String filepath) throws IOException {
    try (ClientSession session = sshClient.connect(username, host, 22)
        .verify(defaultTimeoutSeconds, TimeUnit.SECONDS).getSession()) {
      session.addPasswordIdentity(password);
      session.auth().verify(defaultTimeoutSeconds, TimeUnit.SECONDS);

      File file = ResourceUtils.getFile(filepath);

      try (CloseableScpClient client = createScpClient(session)) {
        client.upload(file.getAbsolutePath(), file.getName());
      }
    }
  }

  private CloseableScpClient createScpClient(ClientSession session) {
    ScpClientCreator creator = ScpClientCreator.instance();
    ScpClient client = creator.createScpClient(session);
    return CloseableScpClient.singleSessionInstance(client);
  }
}
