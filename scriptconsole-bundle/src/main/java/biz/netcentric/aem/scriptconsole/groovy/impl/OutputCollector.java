package biz.netcentric.aem.scriptconsole.groovy.impl;

import groovy.lang.Closure;

/**
 * Allows the service to capture the output while the script is running
 * 
 * @author thomas.hartmann@netcentric.biz
 * @since 10/2015
 */
public class OutputCollector extends Closure {

    private final StringBuffer stringBuffer = new StringBuffer();

    public OutputCollector(final Object owner) {
        super(owner);
    }

    public OutputCollector() {
        super(null);
    }

    @Override
    public Object call(final Object params) {
        stringBuffer.append(params);
        return false;
    }

    @Override
    public Object call(final Object... args) {
        stringBuffer.append(args);
        return false;
    }

    public StringBuffer getStringBuffer() {
        return this.stringBuffer;
    }

    public String getOutput() {
        return this.stringBuffer.toString().trim();
    }
}
