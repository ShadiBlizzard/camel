/* Generated by camel-package-maven-plugin - do not edit this file! */
package org.apache.camel.dataformat.deflater;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Source code generated by camel-package-maven-plugin - do not edit this file!
 */
@SuppressWarnings("unchecked")
public class ZipDeflaterDataFormatConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer {

    @Override
    public boolean configure(CamelContext camelContext, Object target, String name, Object value, boolean ignoreCase) {
        ZipDeflaterDataFormat dataformat = (ZipDeflaterDataFormat) target;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "compressionlevel":
        case "compressionLevel": dataformat.setCompressionLevel(property(camelContext, int.class, value)); return true;
        default: return false;
        }
    }

}

