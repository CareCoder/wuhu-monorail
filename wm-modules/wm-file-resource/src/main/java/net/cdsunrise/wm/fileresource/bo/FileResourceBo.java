package net.cdsunrise.wm.fileresource.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.cdsunrise.wm.fileresource.entity.FileResource;

/**
 * @author lijun
 * @date 2018-04-20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileResourceBo extends FileResource {
    private String url;
}
