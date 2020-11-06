package com.evian.sqct.inteceptors;

import com.alibaba.fastjson.JSONObject;
import com.evian.sqct.annotation.TokenNotVerify;
import com.evian.sqct.bean.jwt.TokenDTO;
import com.evian.sqct.bean.sys.WebConfig;
import com.evian.sqct.response.ResultCode;
import com.evian.sqct.service.ICacheService;
import com.evian.sqct.service.IJsonWebTokenService;
import com.evian.sqct.util.EvianSignature;
import com.evian.sqct.util.RequestUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date   2018年9月29日 下午2:56:56
 * @author XHX
 * @Description 参数校验拦截器
 */
@Component
public class DateCheckInteceptors implements HandlerInterceptor{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IJsonWebTokenService jwtService;

	@Autowired
	private ICacheService cacheService;

	@Autowired
	private WebConfig webConfig;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> params = RequestUtils.genMapByRequestParas(parameterMap);
		if(params.containsKey("sign")&&params.containsKey("timestamp")) {
			if(!validateRequest_getReplenishmentList(params)){
				Map<String, Object> map = new HashMap<String, Object>();
				int code = ResultCode.CODE_ERROR_PARAM.getCode();
				map.put("code", code);
				map.put("message", ResultCode.CODE_ERROR_PARAM.getMessage());
				JSONObject result = new JSONObject(map);
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().print(result.toJSONString());
				return false;
			}
		}else{
			// 有这样注解的api 不需要token验证
			TokenNotVerify tokenNotVerify = ((HandlerMethod) handler).getMethodAnnotation(TokenNotVerify.class);
			if(tokenNotVerify==null||!tokenNotVerify.validate()){
				if(!validateRequest_Token(request)){
					Map<String, Object> map = new HashMap<String, Object>();
					int code = ResultCode.CODE_ERROR_ACCESS_TOKEN.getCode();
					map.put("code", code);
					map.put("message", ResultCode.CODE_ERROR_ACCESS_TOKEN.getMessage());
					JSONObject result = new JSONObject(map);
					response.setContentType("application/json;charset=UTF-8");
					response.getWriter().print(result.toJSONString());
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}



	private boolean validateRequest_Token(HttpServletRequest request){

		// token开关
		if(!webConfig.getTokenSwitch()){
			logger.info("token开关已关");
			return true;
		}

		try {
			String access_token = jwtService.getHeaderToken(request);
			if(access_token==null){
				return false;
			}

			Integer accountId = jwtService.getTokenAccountId(access_token);
			List<TokenDTO> tokens = cacheService.getLoginAccountTokens(accountId);
			System.out.println("pppppppppppppppp = "+tokens);
			if(tokens!=null){
				for (TokenDTO token:tokens){
					if(access_token.equals(token.getAccess_token())){
						return true;
					}
				}
			}
		} catch (ExpiredJwtException e) {
			logger.error("[access_token校验失败:{}]",e.getMessage());
		}
		return false;
	}
	
	/**
	 * 参数验证
	 */
	private boolean validateRequest_getReplenishmentList(
			Map<String, String> params) throws IOException {
		// 签名开关 
		if(!webConfig.getSignSwitch()){
			logger.info("签名开关已关");
			return true;
		}

		if (!params.containsKey("sign")
				|| StringUtils.isEmpty(params.get("sign"))) {
			return false;
		}
		
		// 验证签名
		String sign = EvianSignature.getSignature(params, new String(
				EvianSignature.signKey));
		if (!params.get("sign").equals(sign)) {
			logger.info("[用户签名:{}] [正确签名:{}]",new Object[] {params.get("sign"),sign});
			
			return false;
		}
		return true;
	}

}
