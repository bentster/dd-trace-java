package datadog.trace.api.interceptor;

import java.util.Map;

public interface MutableSpan {

  /** @return Start time with nanosecond scale, but millisecond resolution. */
  long getStartTime();

  /** @return Duration with nanosecond scale. */
  long getDurationNano();

  String getOperationName();

  MutableSpan setOperationName(final String serviceName);

  String getServiceName();

  MutableSpan setServiceName(final String serviceName);

  CharSequence getResourceName();

  MutableSpan setResourceName(final CharSequence resourceName);

  Integer getSamplingPriority();

  /**
   * @deprecated Use {@link io.opentracing.Span#setTag(String, boolean)} instead using either tag
   *     names {@link datadog.trace.api.DDTags#MANUAL_KEEP} or {@link
   *     datadog.trace.api.DDTags#MANUAL_DROP}.
   * @param newPriority
   * @return
   */
  @Deprecated
  MutableSpan setSamplingPriority(final int newPriority);

  String getSpanType();

  MutableSpan setSpanType(final String type);

  Map<String, Object> getTags();

  MutableSpan setTag(final String tag, final String value);

  MutableSpan setTag(final String tag, final boolean value);

  MutableSpan setTag(final String tag, final Number value);

  Boolean isError();

  MutableSpan setError(boolean value);

  /** @deprecated Use {@link #getLocalRootSpan()} instead. */
  @Deprecated
  MutableSpan getRootSpan();

  /**
   * Returns the root span for current the trace fragment. In the context of distributed tracing
   * this method returns the root span only for the fragment generated by the currently traced
   * application.
   *
   * @return The root span for the current trace fragment.
   */
  MutableSpan getLocalRootSpan();
}
