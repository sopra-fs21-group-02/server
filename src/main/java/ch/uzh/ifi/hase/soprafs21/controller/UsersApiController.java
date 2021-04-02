package ch.uzh.ifi.hase.soprafs21.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.dogsApp.base-path:/v1}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
