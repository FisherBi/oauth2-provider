package com.fisherbi.apis;

import com.fisherbi.client.oauth2.builder.OAuthServiceBuilder;
import com.fisherbi.client.oauth2.model.HttpMethod;
import com.fisherbi.client.oauth2.model.OAuth2AccessToken;
import com.fisherbi.client.oauth2.model.OAuthRequest;
import com.fisherbi.client.oauth2.model.Response;
import com.fisherbi.client.oauth2.service.OAuth20Service;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class GitHubExample {

    private static final String NETWORK_NAME = "GitHub";
    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

    private GitHubExample() {
    }

    public static void main(String... args) throws IOException, InterruptedException, ExecutionException {
        final String clientId = "86d385e56cb6b82b2c03";
        final String clientSecret = "ae36dd915a3b26311e3ec82151af5c9e91c735f8";
        final String secretState = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new OAuthServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .state(secretState)
                .callback("http://www.example.com/oauth_callback/")
                .build(GitHubApi.instance());
        final Scanner in = new Scanner(System.in, "UTF-8");

        System.out.println("=== " + NETWORK_NAME + "'s OAuth流程 ===");
        System.out.println();

        System.out.println("获取Authorization URL...");
        final String authorizationUrl = service.getAuthorizationUrl();
        System.out.println("得到Authorization URL!");
        System.out.println("现在访问去授权:");
        System.out.println(authorizationUrl);
        System.out.println("输入授权码");
        System.out.print(">>");
        final String code = in.nextLine();
        System.out.println();

        System.out.println("输入获取的state. 设置'secretState'='" + secretState + "'。");
        System.out.print(">>");
        final String value = in.nextLine();
        if (secretState.equals(value)) {
            System.out.println("State值匹配!");
        } else {
            System.out.println("State值不匹配!");
            System.out.println();
        }

        System.out.println("请求Access Token...");
        final OAuth2AccessToken accessToken = service.getAccessToken(code);
        System.out.println("得到了Access Token!");
        System.out.println("(Raw response是: " + accessToken.getRawResponse() + "')");
        System.out.println();

        System.out.println("去访问资源...");
        final OAuthRequest request = new OAuthRequest(HttpMethod.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response response = service.execute(request);
        System.out.println("得到相应的用户信息...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());
        System.out.println();
    }
}
