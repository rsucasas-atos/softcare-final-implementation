/*
 * Copyright (c) 2003, Rafael Steil
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, 
 * with or without modification, are permitted provided 
 * that the following conditions are met:
 * 
 * 1) Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the 
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the 
 * above copyright notice, this list of conditions and 
 * the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor 
 * the names of its contributors may be used to endorse 
 * or promote products derived from this software without 
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT 
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, 
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 * 
 * Created on 04/12/2004 15:57:26
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * @author Rafael Steil
 * @version $Id: FakeHttpRequest.java,v 1.5 2007/08/01 22:30:04 rafaelsteil Exp $
 */
public class FakeHttpRequest implements HttpServletRequest 
{
	private HttpSession session = new FakeHttpSession();
	private Hashtable params = new Hashtable();
	private Map attributes = new HashMap();
	
	public String getAuthType() {
		
		return null;
	}
	public String getContextPath() {
		return "";
	}
	public Cookie[] getCookies() {
		
		return null;
	}
	public long getDateHeader(String arg0) {
		
		return 0;
	}
	public String getHeader(String arg0) {
		
		return null;
	}
	public Enumeration getHeaderNames() {
		
		return null;
	}
	public Enumeration getHeaders(String arg0) {
		
		return null;
	}
	public int getIntHeader(String arg0) {
		
		return 0;
	}
	public String getMethod() {
		return "dummy-method";
	}
	public String getPathInfo() {
		
		return null;
	}
	public String getPathTranslated() {
		
		return null;
	}
	public String getQueryString() {
		
		return null;
	}
	public String getRemoteUser() {
		
		return null;
	}
	public String getRequestedSessionId() {
		
		return null;
	}
	public String getRequestURI() {
		return "";
	}
	public StringBuffer getRequestURL() {
		
		return null;
	}
	public String getServletPath() {
		
		return null;
	}
	public HttpSession getSession() {
		return this.session;
	}
	public HttpSession getSession(boolean arg0) {
		return this.getSession();
	}
	public Principal getUserPrincipal() {
		
		return null;
	}
	public boolean isRequestedSessionIdFromCookie() {
		
		return false;
	}
	public boolean isRequestedSessionIdFromUrl() {
		
		return false;
	}
	public boolean isRequestedSessionIdFromURL() {
		
		return false;
	}
	public boolean isRequestedSessionIdValid() {
		
		return false;
	}
	public boolean isUserInRole(String arg0) {
		
		return false;
	}
	public Object getAttribute(String arg0) {
		return this.attributes.get(arg0);
	}
	public Enumeration getAttributeNames() {
		
		return null;
	}
	public String getCharacterEncoding() {
		
		return null;
	}
	public int getContentLength() {
		
		return 0;
	}
	public String getContentType() {
		
		return null;
	}
	public ServletInputStream getInputStream() throws IOException {
		
		return null;
	}
	public String getLocalAddr() {
		
		return null;
	}
	public Locale getLocale() {
		
		return null;
	}
	public Enumeration getLocales() {
		
		return null;
	}
	public String getLocalName() {
		
		return null;
	}
	public int getLocalPort() {
		
		return 0;
	}
	public String getParameter(String arg0) {
		
		return null;
	}
	public Map getParameterMap() {
		
		return null;
	}
	public Enumeration getParameterNames() {
		return this.params.elements();
	}
	public String[] getParameterValues(String arg0) {
		
		return null;
	}
	public String getProtocol() {
		
		return null;
	}
	public BufferedReader getReader() throws IOException {
		
		return null;
	}
	public String getRealPath(String arg0) {
		
		return null;
	}
	public String getRemoteAddr() {
		
		return null;
	}
	public String getRemoteHost() {
		
		return null;
	}
	public int getRemotePort() {
		
		return 0;
	}
	public RequestDispatcher getRequestDispatcher(String arg0) {
		
		return null;
	}
	public String getScheme() {
		
		return null;
	}
	public String getServerName() {
		
		return null;
	}
	public int getServerPort() {
		
		return 0;
	}
	public boolean isSecure() {
		
		return false;
	}
	public void removeAttribute(String arg0) {
		this.attributes.remove(arg0);
	}
	public void setAttribute(String arg0, Object arg1) {
		this.attributes.put(arg0, arg1);
	}
	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		

	}
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}
	public AsyncContext startAsync() {
		// TODO Auto-generated method stub
		return null;
	}
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean authenticate(HttpServletResponse arg0) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		return false;
	}
	public Part getPart(String arg0) throws IOException, IllegalStateException,
			ServletException {
		// TODO Auto-generated method stub
		return null;
	}
	public Collection getParts() throws IOException, IllegalStateException,
			ServletException {
		// TODO Auto-generated method stub
		return null;
	}
	public void login(String arg0, String arg1) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	public void logout() throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
