package com.resource.method;

import com.resource.processor.Processor;

public interface Method extends Processor {

    public boolean match(String type);

}
