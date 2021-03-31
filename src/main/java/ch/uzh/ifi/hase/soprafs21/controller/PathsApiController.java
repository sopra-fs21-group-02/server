package ch.uzh.ifi.hase.soprafs21.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-03-31T23:13:43.859438600+02:00[Europe/Berlin]")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class PathsApiController implements PathsApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PathsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
