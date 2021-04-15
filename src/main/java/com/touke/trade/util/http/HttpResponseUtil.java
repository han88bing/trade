package com.touke.trade.util.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

public class HttpResponseUtil {

	
	/**
	 * 返回信息
	 * @param request
	 * @param response
	 * @param object
	 * @return
	 */
	public static boolean buildResposne(HttpServletRequest request,
			HttpServletResponse response,Object object){
		PrintWriter out = null;
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding("UTF-8");
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(object));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out != null) {
				out.close();
			}
		}  
    	return false;
	}
	
	
	/**
	 * 成功返回
	 * @param request
	 * @param response
	 * @param info
	 */
	public static void resposneSuccess(String info){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
    			.getRequestAttributes();
    	HttpServletResponse response = requestAttributes.getResponse();
		PrintWriter out = null;
		response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setCharacterEncoding("UTF-8");
		try {
			out = response.getWriter();
			out.print(info);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out != null) {
				out.close();
			}
		}  
	}
	
	/**
	 * 失败返回
	 * @param request
	 * @param response
	 * @param info
	 */
	public static void resposneError(Object info){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
    			.getRequestAttributes();
    	HttpServletResponse response = requestAttributes.getResponse();
		PrintWriter out = null;
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setCharacterEncoding("UTF-8");
		try {
			out = response.getWriter();
			out.print(JSON.toJSONString(info));
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(out != null) {
				out.close();
			}
		}  
	}
	
	
	
	/**
	 * 获取request 中body数据
	 * @param request
	 * @return
	 */
	  public static String getBodyString(HttpServletRequest request) {
		  InputStream inputStream = null;  
		  BufferedReader reader = null; 
		  StringBuilder sb = new StringBuilder();
		  try {
			  inputStream = request.getInputStream();
			  reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			  String line = "";
              while ((line = reader.readLine()) != null) {
                 sb.append(line);
              }
		  } catch (IOException e) {
			 e.printStackTrace();
		  }finally {
			  if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
		  }
		  return sb.toString();
	 }

	  
	  
	  /**
	     * Description: 复制输入流</br>
	     *
	     * @param inputStream
	     *
	     * @return</br>
	     */
     public static InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
            byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
     }

	private HttpResponseUtil() {
		super();
	}
	
	
	
}
