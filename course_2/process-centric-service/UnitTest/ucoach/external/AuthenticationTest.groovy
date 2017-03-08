package ucoach.util;

import spock.lang.Specification
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Cookie;
/**
 * Created by morse on 2017. 03. 01..
 */
class AuthorizationTest extends Specification {
    class MockHttpHeaders implements HttpHeaders
    {
        MockHttpHeaders(String authkey)
        {
            _authkey = authkey;
        }
        private String _authkey;
        public List<String> getRequestHeader(String name)
        {
            List<String> ret = null;
            if (_authkey != null && name.compareTo("Authorization") == 0) {
                ret = new ArrayList<String>();
                ret.add(_authkey);
            }
            return ret;
        }
        public String getHeaderString(String name)
        {
            return "";
        }
        public MultivaluedMap<String, String> getRequestHeaders()
        {
            return null;
        }
        public List<MediaType> getAcceptableMediaTypes()
        {
            return null;
        }
        public List<Locale> getAcceptableLanguages() {
            return null;
        }
        public MediaType getMediaType()
        {
            return null;
        }
        public Locale getLanguage()
        {
            return null;
        }
        public Map<String, Cookie> getCookies()
        {
            return null;
        }
        public Date getDate()
        {
            return null;
        }
        public int getLength()
        {
            return 0;
        }
    };
    def "ValidateRequest"() {
        Authorization auth = new Authorization();
        MockHttpHeaders headers = null;
        expect:
        auth.validateRequest(headers) == false;
        auth.validateRequest(headers = new MockHttpHeaders(null)) == false;
        auth.validateRequest(headers = new MockHttpHeaders("GARBAGE")) == false;
        auth.validateRequest(headers = new MockHttpHeaders(auth.getAuthorizationKey())) == true;
    }
}
