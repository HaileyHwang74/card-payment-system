package net.e4net.demo.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Random;


@Slf4j
@RequestMapping("")
@RequiredArgsConstructor
@RestController
public class SmsController {

    final DefaultMessageService messageService;

    public SmsController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSHMM9REP5E8NIR", "DPD36WVVMSNRWAKFJ9GZNVEXJM2G6OUM", "https://api.coolsms.co.kr");
    }

    @PostMapping("/send-one")
    public String sendOne(@RequestBody String mobileNo) {

        Random rand = new Random();
//        String numStr = "";
//        for (int i = 0; i < 4; i++) {
//            String ran = Integer.toString(rand.nextInt(10));
//            numStr += ran;
//        }
        String numStr = "1111";
        Message message = new Message();

        //핸드폰번호 null이 아닐 때 => 적용 안 됨
        if(mobileNo != ""){

            message.setFrom("01089408679");
            message.setTo(mobileNo);
            message.setText("인증번호:\n"+numStr +"를 입력해주세요");
        }
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.


        /**
         * 보유잔액 부족으로 따로 인증메세지는 보내지 않고, 인증번호를 지정해서 return 함.
         */
//        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
//        System.out.println(response);

//        session.setAttribute("mobileNo", mobileNo);
//        session.setAttribute("certifyNo", numStr);

        log.info("numStr: " + numStr);
        return numStr;

    }

    /**
     * 오류 해결: lib에서 jar로 열어주는게 아니라 dependency 따로 찾아서 implement 해줌
     */

}




