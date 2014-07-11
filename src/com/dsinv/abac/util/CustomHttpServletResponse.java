package com.dsinv.abac.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CustomHttpServletResponse extends HttpServletResponseWrapper {
	
	private final ServletOutputStream outputStream;
    private final PrintWriter printWriter;

    public CustomHttpServletResponse(HttpServletResponse response) throws UnsupportedEncodingException {
        super(response);

        this.outputStream = new CustomServletOutPutStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));

    }

    @Override
    public ServletOutputStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.printWriter;
    }
}
