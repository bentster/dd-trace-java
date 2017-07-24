package com.datadoghq.trace.agent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DDJavaAgentInfo {
  public static final String VERSION;

  static {
    String v;
    try {
      final StringBuffer sb = new StringBuffer();

      final BufferedReader br =
          new BufferedReader(
              new InputStreamReader(
                  DDJavaAgentInfo.class.getResourceAsStream("dd-java-agent.version"), "UTF-8"));
      for (int c = br.read(); c != -1; c = br.read()) sb.append((char) c);

      v = sb.toString().trim();
    } catch (final Exception e) {
      v = "unknown";
    }
    VERSION = v;
  }
}
