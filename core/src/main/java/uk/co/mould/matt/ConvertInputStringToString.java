package uk.co.mould.matt;

import java.io.IOException;
import java.io.InputStream;

public class ConvertInputStringToString {
	private InputStream in_s;

	public ConvertInputStringToString(InputStream in_s) {
		this.in_s = in_s;
	}

	public String invoke() throws IOException {
		byte[] b = new byte[in_s.available()];
		in_s.read(b);
		return new String(b);
	}
}
