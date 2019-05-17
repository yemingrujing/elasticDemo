import com.test.elasticsearch.EsAppApplication;
import com.test.elasticsearch.controller.AddressRegionController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.MalformedURLException;
import java.net.URL;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EsAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EsAddressRegionTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressRegionController addressRegionController;

    @Before
    public void setUp() throws MalformedURLException {
        String url = String.format("http://localhost:%d/", port);
        System.out.println(String.format("port is: [%d]", port));
        this.base = new URL(url);
    }

    @Test
    public void test1() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(this.base.toString() + "es/query/1", String.class);
        System.out.println(responseEntity.getBody());
    }
}
