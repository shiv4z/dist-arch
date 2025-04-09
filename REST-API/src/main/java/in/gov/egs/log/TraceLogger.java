package in.gov.egs.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import brave.Span;
import brave.Tracer;

@Component
public class TraceLogger {

    @Autowired
    private Tracer tracer;

    public void logPayload(String payload) {
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            currentSpan.tag("request.body", payload); 
        }
    }
}