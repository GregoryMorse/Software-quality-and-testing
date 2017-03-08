package ucoach.data.ws;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by szucs on 2/13/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Authorization.class)
public class AuthorizationTest {

    public abstract class MyMessageContext implements MessageContext {
        public Object get(Object key) {
            return null;
        }
    }

    @Test
    public void validateRequest() throws Exception {
        WebServiceContext wsc = Mockito.mock(WebServiceContext.class);
        Authorization auth = new Authorization();
        MyMessageContext mc = Mockito.mock(MyMessageContext.class);
        HashMap<String, List> map = new HashMap<String, List>();
        map.put("Authorization", new ArrayList<String>(Arrays.asList("default_authorization_key")));
        Mockito.when(mc.get(MessageContext.HTTP_REQUEST_HEADERS)).thenReturn(map);
        Mockito.when(wsc.getMessageContext()).thenReturn(mc);
        assertTrue(auth.validateRequest(wsc));

        PowerMockito.mockStatic(System.class);
        Mockito.when(System.getenv("INTERNAL_DATA_AUTH_KEY")).thenReturn("default_key");
        assertEquals("default_key", System.getenv("INTERNAL_DATA_AUTH_KEY"));

        map.clear();
        map.put("Authorization", new ArrayList<String>(Arrays.asList("default_key")));
        assertTrue(auth.validateRequest(wsc));
        map.clear();
        map.put("Authorization", Arrays.asList("wrong_key"));
        assertFalse(auth.validateRequest(wsc));
        map.clear();
        assertFalse(auth.validateRequest(wsc));
    }

}
