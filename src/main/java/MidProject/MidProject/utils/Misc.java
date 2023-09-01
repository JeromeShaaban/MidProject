package MidProject.MidProject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

import java.util.HashMap;
import java.util.Map;

public class Misc {

    public static String getParams(JoinPoint jp) {
        Map<String, Object> params = new HashMap<>();
        CodeSignature codeSignature = (CodeSignature) jp.getSignature();
        if (codeSignature.getParameterNames() != null) {
            for (int i = 0; i < codeSignature.getParameterNames().length; i++) {
                if (jp.getArgs()[i] instanceof HttpServletRequest) continue;
                String key = codeSignature.getParameterNames()[i];
                params.put(key, jp.getArgs()[i]);
            }
        }

        try {
            return new ObjectMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}