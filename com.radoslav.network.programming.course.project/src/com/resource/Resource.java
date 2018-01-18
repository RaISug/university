package com.resource;

import com.request.Request;
import com.resource.processor.Processor;

public interface Resource extends Processor {

    boolean match(Request request);

}
