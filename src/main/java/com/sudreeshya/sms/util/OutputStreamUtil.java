package com.sudreeshya.sms.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
@Slf4j
public class OutputStreamUtil {

    public static void writeToHttpServletResponse(HttpServletResponse httpServletResponse, ByteArrayOutputStream responseOutputStream) {
        try {
            OutputStream outputStream = httpServletResponse.getOutputStream();
            responseOutputStream.writeTo(outputStream);
            outputStream.close();
            responseOutputStream.close();
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage());
        }
    }
}
