package com.tomasky.doms.support.filter;

import com.tomasky.doms.support.util.CommonUtil;
import com.tomasky.doms.support.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * DESC : 直连请求过滤
 *
 * @author : 番茄木-ZLin
 * @data : 2015/7/27
 * @version: v1.0.0
 */
public class DirectFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(DirectFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String xml = CommonUtil.convertStreamToString(request.getInputStream());
        //String xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?><PushRoomType><list><RoomType><AccountId>1000968</AccountId><RoomTypeId>1019276</RoomTypeId><RoomTypeName>大床房</RoomTypeName><RoomDetails><RoomDetail><RoomDate>2015-09-07</RoomDate><RoomPrice>218.0</RoomPrice><PriRoomPrice>9999999.0</PriRoomPrice><RoomNum>3</RoomNum></RoomDetail></RoomDetails></RoomType></list></PushRoomType>";
        if (!StringUtils.isEmpty(xml)) {
            String rootElementName = "";
            try {

            } catch (Exception e) {
                log.error("RouterRestFilter 解析xml失败", e);
            }
            StringBuilder uri = new StringBuilder("/fanqieService/").append(rootElementName);
            uri.append("?pushXml=").append(xml);
            request.getRequestDispatcher(uri.toString()).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
