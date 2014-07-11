package com.dsinv.abac.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class CustomServletOutPutStream extends ServletOutputStream{
    
	private final StringBuffer stringBuffer = new StringBuffer();

    @Override
    public String toString() {
        return stringBuffer.toString();
    }

    @Override
    public void write(int b) {
    	stringBuffer.append(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
    	stringBuffer.append(new String(b, off, len, "UTF-8"));
    }
}
