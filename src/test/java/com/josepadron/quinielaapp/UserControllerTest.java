package com.josepadron.quinielaapp;

import com.josepadron.quinielaapp.dao.response.JwtAuthenticationResponse;
import com.josepadron.quinielaapp.models.user.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    private final static String baseAuthUrl = "/api/v1/auth";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User user = new User();
        user.setName("John Doe");
        user.setEmail(Math.floor(Math.random() * (1000 - 5 + 1) + 5) + "@example.com");
        user.setPassword("12345678");

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(baseAuthUrl + "/signup", request, JwtAuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        JwtAuthenticationResponse tokenBody = response.getBody();

        assert tokenBody != null;

        assertThat(tokenBody.getToken()).isNotBlank();
    }

    @Test
    public void testDontLoginWithBadCredentialsUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String email = Math.floor(Math.random() * (1000 - 5 + 1) + 5) + "@example.com";
        String password = String.valueOf(Math.floor(Math.random() * (1000 - 5 + 1) + 5));

        User user = new User();
        user.setName("John Doe");
        user.setEmail(email);
        user.setPassword(password);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(baseAuthUrl + "/signup", request, JwtAuthenticationResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        JwtAuthenticationResponse tokenBody = response.getBody();
        assert tokenBody != null;
    }

    @Test
    public void testDontCreateInvalidUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        User user = new User();
        user.setName("a");
        user.setEmail("b");
        user.setPassword("3");

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity(baseAuthUrl + "/signup", request, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        User createdUser = response.getBody();

        assert createdUser != null;

        assertThat(createdUser.getName()).isNotEqualTo(user.getName());
        assertThat(createdUser.getEmail()).isNotEqualTo(user.getEmail());
    }
}
