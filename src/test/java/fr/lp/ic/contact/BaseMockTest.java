package fr.lp.ic.contact;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.IExpectationSetters;
import org.easymock.internal.matchers.Captures;
import org.junit.Rule;

public class BaseMockTest extends EasyMockSupport {
	
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);
	
	public static <T> IExpectationSetters<T> expect(T value){
		return EasyMock.expect(value);
	}
	
	public static <T> Capture<T> newCapture() {
	   return EasyMock.newCapture();
	}
	
		
    public static <T> T capture(Capture<T> captured) {
        return EasyMock.capture(captured);
    }

}
