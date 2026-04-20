package service.academicworkload.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrapped = new ContentCachingRequestWrapper((HttpServletRequest) request);
        chain.doFilter(wrapped, response);

        byte[] buf = wrapped.getContentAsByteArray();

        if (buf.length > 0) {
            String body = new String(buf, StandardCharsets.UTF_8);
            log.info("Request body: {}", body);
        }
    }

}
