package top.shanbing;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import top.shanbing.domain.model.comment.CommentAddReq;
import top.shanbing.domain.model.comment.CommentListReq;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentControllerTest {
    //@Test
    public static void testCreateUser() throws Exception {
        WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        System.out.println(client);
        CommentAddReq addReq = new CommentAddReq();
        addReq.siteUrl = "https://www.shanbing.top";
        addReq.postUrl = "https://www.shanbing.top/about/";
        addReq.commentName = "test";
        addReq.commentContacts = "shanbing.top@gmail.com";
        addReq.commentContent = "评论内容test";
        byte[] result = client.post().uri("comment/v1/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(addReq), CommentAddReq.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult().getResponseBody();
        System.out.println(new String(result));
    }
    public static void testList() throws Exception {
        WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
        System.out.println(client);
        CommentListReq req = new CommentListReq();
        req.siteUrl = "https://www.shanbing.top";
        req.postUrl = "https://www.shanbing.top/about/";

        byte[] result = client.post().uri("comment/v1/list")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(req), CommentListReq.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult().getResponseBody();
        System.out.println(new String(result));
    }




    public static void main(String[] args)  throws Exception{
        //testCreateUser();
        testList();
//        WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
//        System.out.println(client);
//        String responseBody = new String(client.get().uri("/").exchange().expectBody().returnResult().getResponseBody());
//        System.out.println( "get:" + responseBody);
    }
}
