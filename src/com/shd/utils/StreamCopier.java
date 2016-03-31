package com.shd.utils;

import java.io.*;

/**
 * YULON Project
 * Title: com.yulon.sdk.objdata.util.StreamCopier
 * Description:
 * Copyright:(c) 2007-2008 International Business Machine.
 *           All Rights Reserved.
 * Created on Aug 20, 2007
 * @author Johnson Chen
 * @version 1.0
 */

public class StreamCopier {
  public static void copy(InputStream in, OutputStream out) 
   throws IOException {

    synchronized (in) {
      synchronized (out) {

        byte[] buffer = new byte[256];
        while (true) {
          int bytesRead = in.read(buffer);
          if (bytesRead == -1) break;
          out.write(buffer, 0, bytesRead);
        }
      }
    }
  }

}
