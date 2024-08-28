package market.demo.repository.httpClient;

import market.demo.dto.PostDTO;
import market.demo.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "auth-service", url = "${app.services.auth}")
public interface Market_authClient {

    @PostMapping(value = "/auth/get-user-by-id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Map<String,Object> getUserById(@RequestBody Map<String, Object> objectMap);

    @PostMapping(value = "/auth/get-all-user-by-id",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<UserDTO> getAllUserById(List<Long> userIds);
}
