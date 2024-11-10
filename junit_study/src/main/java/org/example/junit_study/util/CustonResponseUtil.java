package org.example.junit_study.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.example.junit_study.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 401 : 인증이 되지 않음
// 403 : 권한이 없어 요청 거부됨
public class CustonResponseUtil {
    private static final Logger log = LoggerFactory.getLogger(CustonResponseUtil.class);

    public static void unAuthentication(HttpServletResponse response, String msg) {
        try{
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(-1,msg,null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody);
        }catch (Exception e){
            log.error("서버파싱에러");
        }
    }
}
