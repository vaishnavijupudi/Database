package logs;

import java.io.IOException;

/**
 * Interface: EventLogger.java
 *
 * @author Yashvi Lad
 * Purpose & Description: Interface implemented by all event log generating java files.
 */
public interface EventLogger {

  void event(String name, long timeELapsed) throws IOException;

}
