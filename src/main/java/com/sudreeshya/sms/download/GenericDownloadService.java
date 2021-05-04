package com.sudreeshya.sms.download;

/**
 * @author Manjit Shakya <manjit.shakya@f1soft.com>
 */
public interface GenericDownloadService<I,O> {

    O convert(I input) throws Exception;
}
