package datadog.smoketest

import okhttp3.Request
import spock.lang.Shared

class SpringBootSmokeTest extends AbstractServerSmokeTest {

  @Shared
  static File output = File.createTempFile("trace-structure", "out")

  @Override
  ProcessBuilder createProcessBuilder() {
    String springBootShadowJar = System.getProperty("datadog.smoketest.springboot-grpc.shadowJar.path")

    List<String> command = new ArrayList<>()
    command.add(javaPath())
    command.addAll(defaultJavaProperties)
    command.addAll((String[]) ["-Ddd.writer.type=TraceStructureWriter:${output.getAbsolutePath()}", "-jar", springBootShadowJar, "--server.port=${httpPort}"])
    ProcessBuilder processBuilder = new ProcessBuilder(command)
    processBuilder.directory(new File(buildDirectory))
  }


  def cleanupSpec() {
    // check the structures written out to the log
    BufferedReader reader = new BufferedReader(new FileReader(output))
    try {
      String line = null
      while (null == line || !line.isEmpty()) {
        line = reader.readLine()
        assert "[servlet.request[spring.handler[grpc.client[grpc.message]]]]".equalsIgnoreCase(line)
      }
    } finally {
      reader.close()
    }
  }

  def "greeter #n th time"() {
    setup:
    String url = "http://localhost:${httpPort}/${route}"
    def request = new Request.Builder().url(url).get().build()

    when:
    def response = client.newCall(request).execute()

    then:
    def responseBodyStr = response.body().string()
    responseBodyStr != null
    responseBodyStr.contains("bye")
    response.body().contentType().toString().contains("text/plain")
    response.code() == 200

    where:
    [n, route] << GroovyCollections.combinations((1..200), ["greeting", "async_greeting"])
  }


}
